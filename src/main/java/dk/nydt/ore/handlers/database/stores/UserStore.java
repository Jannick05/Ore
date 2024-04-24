package dk.nydt.ore.handlers.database.stores;

import com.j256.ormlite.dao.Dao;
import dk.nydt.ore.handlers.database.BaseStore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.objects.User;

import java.util.logging.Logger;

public class UserStore extends BaseStore<Integer, User> {
    private StoreHandler storeHandler;

    public UserStore(Dao<User, Integer> dao, StoreHandler stores, Logger logger) {
        super(dao, stores, logger);
    }
}
