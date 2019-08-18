package labo.hirarins.legacy.app.web;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import labo.hirarins.legacy.app.ApplicationRuntimeException;
import labo.hirarins.legacy.app.ConnectionManager;
import labo.hirarins.legacy.app.UserDaoRmi.User;

public class SessionListener implements HttpSessionAttributeListener {

    private static final String UPDATE_LAST_LOGIN_DATE_QUERY = "update users set login_faild_count=0, last_login_date = ? where user_id = ?";

    private static final Logger log = Logger.getLogger(SessionListener.class.getName());

    private ConnectionManager connManager;

    public SessionListener() {
        this.connManager = new ConnectionManager();
    }

    public void attributeAdded(HttpSessionBindingEvent se) {
        Object obj = se.getValue();
        if (obj instanceof User) {
            User user = (User) obj;
            if (user.isUnlockTargetFlag()) {
                log.info("accepted unlock request. userId=" + user.getUserId());
                return;
            }
            log.info("user logged in.");
            Connection conn = null;
            PreparedStatement prst = null;
            try {
                try {
                    conn = connManager.getConnection();
                    log.info("get connection.");
                    prst = conn.prepareStatement(UPDATE_LAST_LOGIN_DATE_QUERY);
                    prst.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
                    prst.setString(2, user.getUserId());
                    int cnt = prst.executeUpdate();
                    conn.commit();
                    log.info("updated last_login_date. user_id=" + user.getUserId() + ", update_count=" + cnt);
                } finally {
                    if (prst != null) {
                        try {
                            prst.close();
                        } catch(SQLException e) {
                            log.info("close PreparedStatement error occured.", e);
                        }
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch(SQLException e) {
                            log.info("close connection error occured.", e);
                        }
                    }
                }
            } catch (SQLException e) {
                if (conn != null) {
                    try {
                        conn.rollback();
                    } catch (SQLException e1) {
                        log.error("rollback error occured.", e);
                        throw new ApplicationRuntimeException("rollback error occured.", e);
                    }
                }
                log.error("update last_login_date error occured.", e);
                throw new ApplicationRuntimeException("session created error occured.", e);
            }
        }
    }

    public void attributeRemoved(HttpSessionBindingEvent se) {
        // nothing to do

    }

    public void attributeReplaced(HttpSessionBindingEvent se) {
        // nothing to do

	}

    
}