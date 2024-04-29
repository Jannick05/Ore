package dk.nydt.ore.database.stores;

import com.j256.ormlite.dao.Dao;
import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.database.BaseStore;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.objects.SellChest;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

public class UserStore extends BaseStore<Integer, User> {
    private StoreManager storeHandler;

    public UserStore(Dao<User, Integer> dao, StoreManager stores, Logger logger) {
        super(dao, stores, logger);
    }

    public User getUser(Player player) {
        User user = new User(player.getName(), 0, 0, 50, 1, 50, 1, player.getUniqueId(), 25);
        User created = getOrPersist("uuid", user.getUuid(), user);
        created.setName(player.getName());
        persist(created);
        return created;
    }

    public void addGenerator(User user, int tier, Location location) {
        UserGenerator userGenerator = new UserGenerator(user, tier, location);

        StoreManager.getUserGeneratorStore().persist(userGenerator);
        persist(user);
    }

    public void setSellChest(User user, Location location) {
        SellChest sellChest = new SellChest(user, location);
        user.setSellChest(sellChest);

        StoreManager.getSellChestStore().persist(sellChest);
        persist(user);
    }

    public void addStat(User user, String stat, double amount) {
        switch (stat) {
            case "xp":
                user.setXp(user.getXp() + amount);
                break;
            case "multiplier":
                user.setMultiplier(user.getMultiplier() + amount);
                break;
            case "level":
                user.setLevel((int) (user.getLevel() + amount));
                break;
            case "rebirth":
                user.setRebirth((int) (user.getRebirth() + amount));
                break;
            case "maxGenerators":
                user.setMaxGenerators((int) (user.getMaxGenerators() + amount));
                break;
        }
        persist(user);
    }

    public void addStat(Player player, Player targetPlayer, String stat, double amount) {
        addStat(StoreManager.getUserStore().getUser(targetPlayer), stat, amount);
        Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
        lang.getStatAdded().send(player, "{player}", targetPlayer.getName(), "{amount}", String.valueOf(amount), "{stat}", stat);
        lang.getStatReceived().send(targetPlayer, "{amount}", String.valueOf(amount), "{stat}", stat);
    }

    public void rebirth(User user) {
        user.setRebirth(user.getRebirth() + 1);
        user.setXp(0);
        user.setXpNeeded(50);
        user.setLevel(1);
        user.setLevelNeeded(user.getLevelNeeded()*2);
        persist(user);
    }
}
