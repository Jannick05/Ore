package dk.nydt.ore.handlers.database.stores;

import com.j256.ormlite.dao.Dao;
import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Generators;
import dk.nydt.ore.handlers.database.BaseStore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.objects.GlobalGenerator;
import dk.nydt.ore.objects.UserGenerator;
import dk.nydt.ore.utils.LocationUtils;
import org.bukkit.Location;

import java.util.Optional;
import java.util.logging.Logger;

public class UserGeneratorStore extends BaseStore<Integer, UserGenerator> {
    private StoreHandler storeHandler;

    public UserGeneratorStore(Dao<UserGenerator, Integer> dao, StoreHandler stores, Logger logger) {
        super(dao, stores, logger);
    }

    public Optional<UserGenerator> getUserGeneratorAtLocation(Location location) {
        return get("location", LocationUtils.serialize(location));
    }

    public GlobalGenerator getGlobalGeneratorByTier(int tier) {
        Generators generators = (Generators) Ore.getConfigHandler().getConfig("generators");
        return generators.getGenerators().get(tier);
    }

    public void upgrade(UserGenerator userGenerator) {
        GlobalGenerator globalGenerator = this.getGlobalGeneratorByTier(userGenerator.getTier() + 1);
        Location loc = LocationUtils.deserialize(userGenerator.getLocation());
        loc.getBlock().setType(globalGenerator.getMaterial());
        userGenerator.setTier(userGenerator.getTier() + 1);
        persist(userGenerator);
    }

    public void deleteGenerator(UserGenerator generator) {
        delete(generator.getId());
    }
}
