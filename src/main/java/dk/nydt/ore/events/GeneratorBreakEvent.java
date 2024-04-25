package dk.nydt.ore.events;

import dk.nydt.ore.Ore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.objects.User;
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
    }
}
