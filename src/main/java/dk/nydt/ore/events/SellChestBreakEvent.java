package dk.nydt.ore.events;

import dk.nydt.ore.Ore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.handlers.database.stores.SellChestStore;
import dk.nydt.ore.objects.SellChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SellChestBreakEvent implements Listener {
    public SellChestBreakEvent(Ore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSellChestBreak(BlockBreakEvent event) {
        SellChestStore sellChestStore = StoreHandler.getSellChestStore();
        SellChest sellChest = sellChestStore.getSellChestAtLocation(event.getBlock().getLocation()).orElse(null);
        if(sellChest == null) return; //Not a sellchest, return
        event.setCancelled(true);
    }
}
