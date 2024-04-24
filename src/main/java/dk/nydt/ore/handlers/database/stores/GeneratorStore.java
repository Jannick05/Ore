package dk.nydt.ore.handlers.database.stores;

import com.j256.ormlite.dao.Dao;
import dk.nydt.ore.handlers.database.BaseStore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.objects.UserGenerator;

import java.util.logging.Logger;

public class GeneratorStore extends BaseStore<Integer, UserGenerator> {
    private StoreHandler storeHandler;

    public GeneratorStore(Dao<UserGenerator, Integer> dao, StoreHandler stores, Logger logger) {
        super(dao, stores, logger);
    }
}
