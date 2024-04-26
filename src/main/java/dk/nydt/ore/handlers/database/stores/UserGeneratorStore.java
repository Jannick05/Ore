package dk.nydt.ore.handlers.database.stores;

import com.j256.ormlite.dao.Dao;
import dk.nydt.ore.handlers.database.BaseStore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import org.bukkit.Location;

import java.util.logging.Logger;

public class UserGeneratorStore extends BaseStore<Integer, UserGenerator> {
    private StoreHandler storeHandler;

    public UserGeneratorStore(Dao<UserGenerator, Integer> dao, StoreHandler stores, Logger logger) {
        super(dao, stores, logger);
    }
}
