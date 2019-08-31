package com.tekhne.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.ConnectionPoolDataSource;
import javax.sql.PooledConnection;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import com.tekhne.exception.OperationException;

public class ConnectionManager {

    private PooledConnection pooledConn;
    
    public ConnectionManager(String connString) {
        
        
    }
    
    public Connection getConnection() {
        try {
            return pooledConn.getConnection();
        } catch (SQLException ex) {
            throw new OperationException("At get Connection from pool", ex);
        }
    }
}
