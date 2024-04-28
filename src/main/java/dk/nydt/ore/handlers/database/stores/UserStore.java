package dk.nydt.ore.handlers.database.stores;

import com.j256.ormlite.dao.Dao;
import dk.nydt.ore.handlers.database.BaseStore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.objects.SellChest;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class UserStore extends BaseStore<Integer, User> {
    private StoreHandler storeHandler;

    public UserStore(Dao<User, Integer> dao, StoreHandler stores, Logger logger) {
        super(dao, stores, logger);
    }

    public User getUser(Player player) {
        User user = new User(player.getName(), 0, 0, 50, 1, 1, player.getUniqueId(), 25);
        User created = getOrPersist("uuid", user.getUuid(), user);
        created.setName(player.getName());
        persist(created);
        return created;
    }

    public void addGenerator(User user, int tier, Location location) {
        UserGenerator userGenerator = new UserGenerator(user, tier, location);
        user.addGenerator(userGenerator);
        StoreHandler.getUserGeneratorStore().persist(userGenerator);
        persist(user);
    }

    public void setSellChest(User user, Location location) {
        SellChest sellChest = new SellChest(user, location);
        user.setSellChest(sellChest);
        StoreHandler.getSellChestStore().persist(sellChest);
        persist(user);
    }
}
