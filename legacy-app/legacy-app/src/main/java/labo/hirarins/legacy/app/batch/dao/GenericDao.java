package labo.hirarins.legacy.app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import labo.hirarins.legacy.app.ConnectionManager;

public class GenericDao {

    private static final Logger log = Logger.getLogger(GenericDao.class.getName());

    private ConnectionManager connManager;

    public GenericDao() {
        this.connManager = new ConnectionManager();
    }

    protected Connection getConnection() throws SQLException {
        return this.connManager.getConnection();
    }
    
    protected ResultSet executeQuery(String query, Object... params) throws SQLException {
        Connection conn = this.connManager.getConnection();
        PreparedStatement prst = conn.prepareStatement(query);
        try {
            int index = 1;
            for (Object param : params) {
                prst.setObject(index++, param);
            }
            return prst.executeQuery();
        } finally {
            try {
                prst.close();
            } catch(SQLException e) {
                // notify close preparedstatement error only.
                log.info("close preparedstatement error occured.", e);
            }
            try {
                conn.close();
            } catch(SQLException e) {
                // notify close connection error only.
                log.info("close connection error occured.", e);
            }
        }
    }
}