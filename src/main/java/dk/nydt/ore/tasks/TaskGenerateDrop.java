package dk.nydt.ore.tasks;

import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.handlers.database.stores.UserStore;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskGenerateDrop extends BukkitRunnable {

    @Override
    public void run() {
        UserStore userStore = StoreHandler.getUserStore();
        for(Player player : Bukkit.getOnlinePlayers()) {
            User user = userStore.getUser(player);
            for(UserGenerator generator : user.getGenerators()) {
                generator.generate();
            }
        }
    }
}
