package dk.nydt.ore.events;

import dev.triumphteam.gui.components.util.ItemNbt;
import dk.nydt.ore.Ore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.objects.SellChest;
import dk.nydt.ore.objects.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SellChestPlaceEvent implements Listener {
    public SellChestPlaceEvent(Ore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSellChestPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        User user = StoreHandler.getUserStore().getUser(player);

        if(!ItemNbt.getString(event.getItemInHand(), "sellchest").equalsIgnoreCase("true")) return; //Not a sell chest, return
        if(!event.canBuild()) return; //Can't build, return

        if(user.getSellChest() != null) {
            player.sendMessage("You already have a sell chest!");
            event.setCancelled(true);
            return;
        }
        StoreHandler.getUserStore().setSellChest(user, event.getBlock().getLocation());
        player.sendMessage("You placed a sell chest!");
    }
}
