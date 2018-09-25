package com.lognad.services.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class PooledConnection {

    /**
     * ComboPooledDataSource dataSource = DatabaseUtility.getDataSource();
     * connection = dataSource.getConnection();
     *
     * @return
     * @throws PropertyVetoException
     */
    public static ComboPooledDataSource getDataSource() throws PropertyVetoException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setJdbcUrl("jdbc:mysql://localhost/test");
        cpds.setUser("root");
        cpds.setPassword("password");

        // Optional Settings //
        cpds.setInitialPoolSize(5);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        cpds.setMaxStatements(100);

        return cpds;
    }
}
