package labo.hirarins.legacy.app.batch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import labo.hirarins.legacy.app.ConnectionManager;

public class GenericDao {

    private ConnectionManager connManager;

    public GenericDao() {
        this.connManager = new ConnectionManager();
    }

    protected Connection getConnection() throws SQLException {
        return this.connManager.getConnection();
    }
    
}