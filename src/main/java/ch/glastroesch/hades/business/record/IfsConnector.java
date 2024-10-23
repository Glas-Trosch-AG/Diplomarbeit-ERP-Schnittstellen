package ch.glastroesch.hades.business.record;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IfsConnector {

    private static final Logger LOGGER = Logger.getLogger(IfsConnector.class.getName());

    private String host;
    private String port;
    private String service;
    private String user;
    private String password;
    private Connection connection = null;
    private int connectionCnt;

    public IfsConnector(String host, String port, String service, String user, String password) {

        this.host = host;
        this.port = port;
        this.service = service;
        this.user = user;
        this.password = password;

        resetConnectionCounter();

    }

    public void connect() {

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            LOGGER.info("oracle driver not found");
            return;
        }

        connection = null;

        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + "/" + service, user, password);
        } catch (SQLException e) {
            LOGGER.info("connecting to database failed");
            return;
        }

    }

    public void close() {

        resetConnectionCounter();

        if (connection != null) {
            try {
                LOGGER.log(Level.INFO, "connection was closed");
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "error closing ifs connection", e);
            }
        }

    }

    public void increaseConnectionCounter() {

        connectionCnt++;

    }

    public void resetConnectionCounter() {

        connectionCnt = 0;

    }

    public Connection getConnection() {
        return connection;
    }

    public int getConnectionCnt() {
        return connectionCnt;
    }

}
