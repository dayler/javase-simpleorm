
package com.tekhne.persistence;

import java.sql.SQLException;

import javax.sql.PooledConnection;

import org.sqlite.javax.SQLiteConnectionPoolDataSource;

import com.tekhne.exception.OperationException;
import com.tekhne.exception.UnsupportedDriverException;

public final class PooledConnectionFactory {
    
    private PooledConnectionFactory() {}
    
    public static PooledConnection getPooledConnection(String connStr) {
        try {
            if (connStr.startsWith("jdbc:sqlite:")) {
                SQLiteConnectionPoolDataSource ds = new SQLiteConnectionPoolDataSource();
                ds.setUrl(connStr);
                return ds.getPooledConnection();
            }
            throw new UnsupportedDriverException("Not supported driver for " + connStr);
        } catch (SQLException ex) {
            throw new OperationException("At get PooledConnection for " + connStr, ex);
        }
    }
    
}
