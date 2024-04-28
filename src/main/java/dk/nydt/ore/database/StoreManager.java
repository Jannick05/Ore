package dk.nydt.ore.database;

import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import dk.nydt.ore.Ore;
import dk.nydt.ore.database.stores.SellChestStore;
import dk.nydt.ore.database.stores.SellableStore;
import dk.nydt.ore.database.stores.UserGeneratorStore;
import dk.nydt.ore.database.stores.UserStore;
import dk.nydt.ore.objects.SellChest;
import dk.nydt.ore.objects.Sellable;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import lombok.Getter;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;

public class StoreManager {
    private Logger logger;
    private static @Getter UserStore userStore;
    private static @Getter UserGeneratorStore userGeneratorStore;
    private static @Getter SellChestStore sellChestStore;
    private static @Getter SellableStore sellableStore;

    private ConnectionSource connectionSource;

    public StoreManager() {
    }

    public void init(Logger logger) throws SQLException {
        logger.info("Initializing database connection");
        try {
            this.connectionSource = new JdbcConnectionSource(getConnectionUrl());

            TableUtils.createTableIfNotExists(connectionSource, SellChest.class);
            TableUtils.createTableIfNotExists(connectionSource, UserGenerator.class);
            TableUtils.createTableIfNotExists(connectionSource, User.class);
            TableUtils.createTableIfNotExists(connectionSource, Sellable.class);

            logger.info(" - Connected to database");
        } catch (Exception exception) {
            logger.severe(" - Failed to connect to database: ");
            exception.printStackTrace();
            return;
        }

        userStore = new UserStore(DaoManager.createDao(connectionSource, User.class), this, logger);
        userGeneratorStore = new UserGeneratorStore(DaoManager.createDao(connectionSource, UserGenerator.class), this, logger);
        sellChestStore = new SellChestStore(DaoManager.createDao(connectionSource, SellChest.class), this, logger);
        sellableStore = new SellableStore(DaoManager.createDao(connectionSource, Sellable.class), this, logger);
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
