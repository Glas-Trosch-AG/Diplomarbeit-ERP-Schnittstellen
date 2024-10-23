package ch.glastroesch.hades.business.record;

import static ch.glastroesch.hades.business.config.SettingConstants.IFS_HOST;
import static ch.glastroesch.hades.business.config.SettingConstants.IFS_PASSWORD;
import static ch.glastroesch.hades.business.config.SettingConstants.IFS_POOL_SIZE;
import static ch.glastroesch.hades.business.config.SettingConstants.IFS_PORT;
import static ch.glastroesch.hades.business.config.SettingConstants.IFS_SERVICE;
import static ch.glastroesch.hades.business.config.SettingConstants.IFS_USER;
import ch.glastroesch.hades.business.config.SettingService;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IfsConnectionService {

    private static final Logger LOGGER = Logger.getLogger(IfsConnectionService.class.getName());
    private static final int MAX_TIME = 5;
    private static final int MAX_CONNECTION_CALLS = 20;

    @Autowired
    SettingService settingService;

    private List<IfsConnector> connectors;
    private int currentConnection = 0;

    public Connection getConnection() {

        if (connectors == null) {
            createConnections();
        }

        IfsConnector connector = getConnector();
        connector.increaseConnectionCounter();

        return connector.getConnection();

    }

    private void createConnections() {

        int poolSize = settingService.asInteger(IFS_POOL_SIZE, 10);

        connectors = new ArrayList<>();
        for (int i = 0; i < poolSize; i++) {
            connectors.add(createConnectionFor(settingService.get(IFS_USER), settingService.get(IFS_PASSWORD)));
        }

    }

    public IfsConnector createConnectionFor(String user, String password) {

        IfsConnector connector = new IfsConnector(settingService.get(IFS_HOST), settingService.get(IFS_PORT), settingService.get(IFS_SERVICE),
                user, password);
        connector.connect();

        return connector;

    }

    private IfsConnector getConnector() {

        IfsConnector connector = getCurrentConnector();

        try {
            if (connector.getConnectionCnt() > MAX_CONNECTION_CALLS || !connector.getConnection().isValid(MAX_TIME)) {
                connector.close();
                connector.connect();
                connector.resetConnectionCounter();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "error checking validity of connection", e);
        }

        return connector;

    }

    private IfsConnector getCurrentConnector() {

        IfsConnector connector = connectors.get(currentConnection);

        currentConnection++;
        if (currentConnection > connectors.size() - 1) {
            currentConnection = 0;
        }

        return connector;

    }

}
