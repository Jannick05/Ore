package dk.nydt.ore.events;

import dev.triumphteam.gui.components.util.ItemNbt;
import dk.nydt.ore.Ore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.objects.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class GeneratorPlaceEvent implements Listener {

    public GeneratorPlaceEvent(Ore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGeneratorPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        Player player = event.getPlayer();
        User user = StoreHandler.getUserStore().getUser(player);
        String tierString = ItemNbt.getString(item, "generator");
        if(tierString.equalsIgnoreCase("")) return; //Not a generator, return
        int tier = Integer.parseInt(tierString);
        if(!event.canBuild()) return; //Can't build, return

        if(user.getGenerators().size() >= user.getMaxGenerators()) {
            player.sendMessage("You have reached the maximum amount of generators!");
            event.setCancelled(true);
            return;
        }

        StoreHandler.getUserStore().addGenerator(user, tier, event.getBlock().getLocation());
        player.sendMessage("You placed a tier " + tier + " generator!");
    }
}
