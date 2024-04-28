package dk.nydt.ore.tasks;

import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.database.stores.UserStore;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class TaskGenerateDrop extends BukkitRunnable {

    @Override
    public void run() {
        UserStore userStore = StoreManager.getUserStore();
        for(Player player : Bukkit.getOnlinePlayers()) {
            User user = userStore.getUser(player);
            Map<Integer, Integer> tierToCount = new HashMap<>();
            for(UserGenerator userGenerator : user.getGenerators()) {
                int tier = userGenerator.getTier();
                tierToCount.put(tier, tierToCount.getOrDefault(tier, 0) + 1);
            }
            for(Map.Entry<Integer, Integer> entry : tierToCount.entrySet()) {
                int tier = entry.getKey();
                int count = entry.getValue();
                StoreManager.getSellChestStore().stockSellableItem(user, tier, count);
            }
            StoreManager.getSellChestStore().persist(user.getSellChest());
        }
    }
}
