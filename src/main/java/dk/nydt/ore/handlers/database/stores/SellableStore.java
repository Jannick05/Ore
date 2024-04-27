package dk.nydt.ore.handlers.database.stores;

import com.j256.ormlite.dao.Dao;
import dk.nydt.ore.handlers.database.BaseStore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.objects.Sellable;

import java.util.logging.Logger;

public class SellableStore extends BaseStore<Integer, Sellable> {
    private StoreHandler storeHandler;

    public SellableStore(Dao<Sellable, Integer> dao, StoreHandler stores, Logger logger) {
        super(dao, stores, logger);
    }
}
