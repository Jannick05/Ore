package dk.nydt.ore.events;

import dk.nydt.ore.Ore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.handlers.database.stores.UserGeneratorStore;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GeneratorBreakEvent implements Listener {

    public GeneratorBreakEvent(Ore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGeneratorBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = StoreHandler.getUserStore().getUser(player);

        UserGeneratorStore userGeneratorStore = StoreHandler.getUserGeneratorStore();
        UserGenerator userGenerator = userGeneratorStore.getUserGeneratorAtLocation(event.getBlock().getLocation()).orElse(null);
        if(userGenerator == null) return; //Not a generator, return

        if(userGenerator.getUser().getUuid().equals(user.getUuid())) {
            userGeneratorStore.deleteGenerator(userGenerator);
            player.sendMessage("You broke your generator!");
        } else {
            player.sendMessage("You can't break someone else's generator!");
            event.setCancelled(true);
        }


    }
}
