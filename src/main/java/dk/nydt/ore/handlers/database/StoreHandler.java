package dk.nydt.ore.handlers.database;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dk.nydt.ore.Ore;
import dk.nydt.ore.handlers.database.stores.GeneratorStore;
import dk.nydt.ore.handlers.database.stores.UserStore;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import lombok.Getter;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

public class StoreHandler {
    private Logger logger;
    private static @Getter UserStore userStore;
    private static @Getter GeneratorStore generatorStore;

    private ConnectionSource connectionSource;

    public StoreHandler() {
    }

    public void init(Logger logger) throws SQLException {
        logger.info("Initializing database connection");
        try {
            this.connectionSource = new JdbcConnectionSource(getConnectionUrl());

            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, UserGenerator.class);

            logger.info(" - Connected to database");
        } catch (Exception exception) {
            logger.severe(" - Failed to connect to database: ");
            exception.printStackTrace();
            return;
        }

        userStore = new UserStore(DaoManager.createDao(connectionSource, User.class), this, logger);
        generatorStore = new GeneratorStore(DaoManager.createDao(connectionSource, UserGenerator.class), this, logger);
    }

    public String getConnectionUrl() {
        String databaseType = "sqlite";
        String databasePath = Ore.getInstance().getDataFolder() + File.separator + Ore.getInstance().getDescription().getName();
        return "jdbc:" + databaseType + ":" + databasePath + ".db";
    }

    public void closeConnection() {
        try {
            if (this.connectionSource != null) {
                this.connectionSource.close();
            }
        } catch (Exception exception) {
            this.logger.severe("Failed to close database connection: " + exception.getMessage());
        }
    }
}
