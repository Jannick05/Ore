package dk.nydt.ore.events;

import dk.nydt.ore.Ore;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.database.stores.UserGeneratorStore;
import dk.nydt.ore.objects.UserGenerator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class GeneratorBreakEvent implements Listener {

    public GeneratorBreakEvent(Ore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGeneratorBreak(BlockBreakEvent event) {
        UserGeneratorStore userGeneratorStore = StoreManager.getUserGeneratorStore();
        UserGenerator userGenerator = userGeneratorStore.getUserGeneratorAtLocation(event.getBlock().getLocation()).orElse(null);
        if(userGenerator == null) return; //Not a generator, return
        event.setCancelled(true);
    }
}
