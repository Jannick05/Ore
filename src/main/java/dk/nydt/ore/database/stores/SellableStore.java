package dk.nydt.ore.database.stores;

import com.j256.ormlite.dao.Dao;
import dk.nydt.ore.database.BaseStore;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.objects.Sellable;

import java.util.logging.Logger;

public class SellableStore extends BaseStore<Integer, Sellable> {
    private StoreManager storeHandler;

    public SellableStore(Dao<Sellable, Integer> dao, StoreManager stores, Logger logger) {
        super(dao, stores, logger);
    }
}
