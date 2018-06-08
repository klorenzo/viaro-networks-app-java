package com.kevinlorenzo.jobs.viaronetworksapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Logger;

/**
 *
 * @author Kevin Lorenzo
 */
public class DBConfig {

    private static final Logger LOGGER = Logger.getLogger(DBConfig.class.getName());

    private static DBConfig dbConfig;

    private static final String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    private Connection connection;

    private DBConfig() {
        try {

            Class.forName(MYSQL_JDBC_DRIVER).newInstance();

            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/viaronetworksapp?user=root&password=toor");

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            LOGGER.info(e.getMessage());
        }
    }

    public static synchronized DBConfig getInstance() {
        if (dbConfig == null) {
            dbConfig = new DBConfig();
        }
        return dbConfig;
    }

    public Connection getConnection() {
        return this.connection;
    }

}