package dk.nydt.ore.events;

import dk.nydt.ore.Ore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.handlers.database.stores.UserGeneratorStore;
import dk.nydt.ore.objects.GlobalGenerator;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import org.bukkit.Bukkit;
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
        UserGeneratorStore userGeneratorStore = StoreHandler.getUserGeneratorStore();
        UserGenerator userGenerator = userGeneratorStore.getUserGeneratorAtLocation(event.getBlock().getLocation()).orElse(null);
        if(userGenerator == null) return; //Not a generator, return
        event.setCancelled(true);
    }
}
