package epam.connection;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {
    private static DataSource datasource = new DataSource();
    private BoneCP connectionPool;
    private Properties properties = new Properties();
    private InputStream is = null;
    private static final Logger logger = Logger.getLogger(epam.connection.DataSource.class);

    private DataSource() {
        try {
            is = getClass().getResourceAsStream("/db.properties");
            properties.load(is);

            BoneCPConfig config = new BoneCPConfig();
            Class.forName(properties.getProperty("DB_DRIVER_CLASS"));
            config.setJdbcUrl(properties.getProperty("DB_URL"));
            config.setUsername(properties.getProperty("DB_USERNAME"));
            //config.setPassword(properties.getProperty("DB_PASSWORD"));

            config.setMinConnectionsPerPartition(5);
            config.setMaxConnectionsPerPartition(10);
            config.setPartitionCount(1);
            connectionPool = new BoneCP(config);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            logger.error("Sorry, something wrong!", e);
        }
    }

    public static DataSource getInstance() {
        return datasource;
    }

    public ConnectionProxy getConnection() {
        try {
            return new ConnectionProxy(this.connectionPool.getConnection());
        } catch (SQLException e) {
            logger.error("Sorry, something wrong!", e);
        }
        return null;
    }
}
