package com.tekhne.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.PooledConnection;

import com.tekhne.exception.OperationException;

public class ConnectionManager {

    private PooledConnection pooledConn;
    
    public ConnectionManager(String connString) {
        pooledConn = PooledConnectionFactory.getPooledConnection(connString);
    }
    
    public Connection getConnection() {
        try {
            return pooledConn.getConnection();
        } catch (SQLException ex) {
            throw new OperationException("At get Connection from pool", ex);
        }
    }
}
