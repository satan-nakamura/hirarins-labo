package labo.hirarins.legacy.app;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {

    private static DataSource ds;

    public Connection getConnection() throws SQLException {
        if (ds == null) {
            setDatasource();
        }
        Connection conn = ds.getConnection();
        conn.setAutoCommit(false);
        return conn;
    }

    private synchronized void setDatasource() {
        if (ds == null) {
            try {
                InitialContext ctx = new InitialContext();
                ds = (DataSource) ctx.lookup("java:comp/env/jdbc/labo-db");
            } catch (NamingException e) {
                e.printStackTrace();
                throw new ApplicationRuntimeException("create initailContext error occured.", e);
            }
        }
    }
    
}