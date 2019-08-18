package labo.hirarins.legacy.app.batch.dao;

import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import labo.hirarins.legacy.app.UserDaoRmi;
import labo.hirarins.legacy.app.web.Utils;

public class UserDao extends GenericDao implements UserDaoRmi {

    private static final Logger log = Logger.getLogger(UserDao.class.getName());

    private static final String FIND_BY_USERID_QUERY =
        "select "
        + "\n user_id, user_name, mail_address, passcode, last_login_date, last_change_passcode_date, login_faild_count, locked_flag, unlock_target_flag"
        + "\n from users"
        + "\n where user_id = ?"
        ;

    private static final String INSERT_QUERY = 
        "insert into users values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String LOGIN_FAILD_QUERY = 
        "update users set "
        + "\n login_faild_count = login_faild_count + 1, "
        + "\n locked_flag = case when login_faild_count + 1 >= 5 then '1' else '0' end"
        + "\n where user_id = ?";

    private static final String REQUEST_UNLOCK_QUERY =
        "update users set "
        + "\n unlock_target_flag = '1' "
        + "\n where user_id = ?";

    private static final String FIND_UNLOCK_TARGET_USERS_QUERY = 
        "select "
        + "\n user_id, user_name, mail_address, passcode, last_login_date, last_change_passcode_date, login_faild_count, locked_flag, unlock_target_flag"
        + "\n from users"
        + "\n where locked_flag = '1' and unlock_target_flag = '1'";


    private static final String UNLOCK_USER_QUERY = 
        "update users set "
        + "\n unlock_target_flag = '0', locked_flag = '0', passcode = ? "
        + "\n where user_id = ?";

    
    public User findByUserId(String userId) throws RemoteException {
        log.info("findByUserId. userId=" + userId);

        Connection conn = null;
        PreparedStatement prst = null;
        ResultSet rs = null;
        User user = null;
        try {
            conn = super.getConnection();
            prst = conn.prepareStatement(FIND_BY_USERID_QUERY);
            prst.setString(1, userId);
            rs = prst.executeQuery();
            
            if (rs.next()) {

                user = new User(
                    rs.getString("user_id"), 
                    rs.getString("user_name"), 
                    rs.getString("mail_address"), 
                    rs.getString("passcode"), 
                    rs.getDate("last_login_date"),
                    rs.getDate("last_change_passcode_date"),
                    rs.getInt("login_faild_count"), 
                    "1".equals(rs.getString("locked_flag")) ? true : false, 
                    "1".equals(rs.getString("unlock_target_flag")) ? true : false);
            }

            return user;
        } catch(SQLException e) {
            log.error("findByUserId error occured.", e);
            rollback(conn);
            throw new RemoteException("findByUserId error occured.", e);

        } finally {
            closeResultSet(rs);
            closeStatement(prst);
            closeConnection(conn);
        }

	}

    public void addUser(User user) throws RemoteException {
        log.info("addUser. user=" + user);
        Connection conn = null;
        PreparedStatement prst = null;
        try {
            conn = super.getConnection();
            prst = conn.prepareStatement(INSERT_QUERY);

            prst.setString(1, user.getUserId());
            prst.setString(2, user.getUserName());
            prst.setString(3, user.getMailAddress());
            prst.setString(4, user.getPasscode());
            if (user.getLastLoginDate() == null) {
                prst.setNull(5, Types.TIMESTAMP);
            } else {
                prst.setTimestamp(5, new Timestamp(user.getLastLoginDate().getTime()));
            }
            if (user.getLastChangePasswordDate() == null) {
                prst.setNull(6, Types.TIMESTAMP);
            } else {
                prst.setTimestamp(6, new Timestamp(user.getLastChangePasswordDate().getTime()));
            }
            prst.setInt(7, user.getLoginFaildCount());
            prst.setString(8, user.isLockedFlag() ? "1" : "0");
            prst.setString(9, user.isUnlockTargetFlag() ? "1" : "0");
            prst.executeUpdate();
            conn.commit();
            log.info("added user. user=" + user);
        } catch (SQLException e) {
            log.error("adduser error occured.", e);
            rollback(conn);
            throw new RemoteException("adduser error occured.", e);
        } finally {
            closeStatement(prst);
            closeConnection(conn);
        }
        
    }

    public User loginFaild(User user) throws RemoteException {
        updateUser(LOGIN_FAILD_QUERY, user.getUserId());
        return findByUserId(user.getUserId());
    }

    public void requestUnlock(User user) throws RemoteException {
        updateUser(REQUEST_UNLOCK_QUERY, user.getUserId());
    }

    public List<User> findUnlockTargetUsers() throws RemoteException {
        log.info("findUnlockTargetUsres");

        List<User> unlockTargetUsers = new ArrayList<UserDaoRmi.User>();
        Connection conn = null;
        PreparedStatement prst = null;
        ResultSet rs = null;
        try {
            conn = super.getConnection();
            prst = conn.prepareStatement(FIND_UNLOCK_TARGET_USERS_QUERY);
            rs = prst.executeQuery();
            
            while (rs.next()) {

                unlockTargetUsers.add(
                    new User(
                        rs.getString("user_id"), 
                        rs.getString("user_name"), 
                        rs.getString("mail_address"), 
                        rs.getString("passcode"), 
                        rs.getDate("last_login_date"),
                        rs.getDate("last_change_passcode_date"),
                        rs.getInt("login_faild_count"), 
                        "1".equals(rs.getString("locked_flag")) ? true : false, 
                        "1".equals(rs.getString("unlock_target_flag")) ? true : false
                    )
                );
            }

            return unlockTargetUsers;
        } catch(SQLException e) {
            log.error("findUnlockTargetUsers error occured.", e);
            rollback(conn);
            throw new RemoteException("findUnlockTargetUsers error occured.", e);

        } finally {
            closeResultSet(rs);
            closeStatement(prst);
            closeConnection(conn);
        }
	}

    public User unlockUser(User user) throws RemoteException {
        Connection conn = null;
        PreparedStatement prst = null;

        try {
            conn = super.getConnection();
            prst = conn.prepareStatement(UNLOCK_USER_QUERY);
            prst.setString(1, Utils.convertToMD5String(user.getUserId()));
            prst.setString(2, user.getUserId());
            prst.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            log.error("unlockUser error occured.", e);
            rollback(conn);
            throw new RemoteException("unlockUser error occured.", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("unlockUser error occured.", e);
            rollback(conn);
            throw new RemoteException("unlockUser error occured.", e);
		} finally {
            closeStatement(prst);
            closeConnection(conn);
        }

        return findByUserId(user.getUserId());
    }

    private void updateUser(String query, String userId) throws RemoteException {
        Connection conn = null;
        PreparedStatement prst = null;

        try {
            conn = super.getConnection();
            prst = conn.prepareStatement(query);
            prst.setString(1, userId);
            prst.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            log.error("loginFaild error occured.", e);
            rollback(conn);
            throw new RemoteException("loginFaild error occured.", e);
        } finally {
            closeStatement(prst);
            closeConnection(conn);
        }

    }

    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // notify closing resultset error only.
                log.info("resultset close error occured.", e);
            }
        }

    }

    private void rollback(Connection conn) throws RemoteException {
        try {
            conn.rollback();
            log.info("rollback success.");
        } catch (SQLException e2) {
            throw new RemoteException("transaction rollback error occured.", e2);
        }
    }

    private void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                // notify closing preparedstatment error only.
                log.info("preparedstatment close error occured.", e);
            }

        }

    }

    private void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // notify closing connection error only.
                log.info("connection close error occured.", e);
            }
        }

    }

    
}