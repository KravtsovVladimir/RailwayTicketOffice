package epam.connection;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Мир on 29.03.2017.
 */
public class ConnectionProxy implements AutoCloseable {

    private final Connection connection;
    private boolean isTransactionActive;
    private static final Logger logger = Logger.getLogger(ConnectionProxy.class);

    public ConnectionProxy(Connection connection) {
        this.connection = connection;
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    public void setAutoCommit(boolean autoCommit) {
        try {
            connection.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
    }

    @Override
    public void close() {
        if (!isTransactionActive) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error("Sorry, something wrong!", e);
            }
        }
    }

    public boolean isTransactionActive() {
        return isTransactionActive;
    }

    public void setTransactionActive(boolean transactionActive) {
        isTransactionActive = transactionActive;
    }
}
