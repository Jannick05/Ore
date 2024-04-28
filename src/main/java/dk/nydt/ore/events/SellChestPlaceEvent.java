package dk.nydt.ore.events;

import dev.triumphteam.gui.components.util.ItemNbt;
import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.objects.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SellChestPlaceEvent implements Listener {
    private static final Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
    public SellChestPlaceEvent(Ore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSellChestPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        User user = StoreManager.getUserStore().getUser(player);

        if(!ItemNbt.getString(event.getItemInHand(), "sellchest").equalsIgnoreCase("true")) return; //Not a sell chest, return
        if(!event.canBuild()) return; //Can't build, return

        if(user.getSellChest() != null) {
            lang.getSellChestAlreadyPlaced().send(player);
            event.setCancelled(true);
            return;
        }
        StoreManager.getUserStore().setSellChest(user, event.getBlock().getLocation());
        lang.getSellChestPlaced().send(player);
    }
}
