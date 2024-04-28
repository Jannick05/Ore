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
import org.bukkit.inventory.ItemStack;

public class GeneratorPlaceEvent implements Listener {

    private static final Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");

    public GeneratorPlaceEvent(Ore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGeneratorPlace(BlockPlaceEvent event) {
        ItemStack item = event.getItemInHand();
        Player player = event.getPlayer();
        User user = StoreManager.getUserStore().getUser(player);
        String tierString = ItemNbt.getString(item, "generator");
        if(tierString.equalsIgnoreCase("")) return; //Not a generator, return
        int tier = Integer.parseInt(tierString);
        if(!event.canBuild()) return; //Can't build, return

        if(user.getGenerators().size() >= user.getMaxGenerators()) {
            lang.getGeneratorMaxPlacedReached().send(player);
            event.setCancelled(true);
            return;
        }

        StoreManager.getUserStore().addGenerator(user, tier, event.getBlock().getLocation());
        lang.getGeneratorPlaced().send(player, "{tier}", String.valueOf(tier));
    }
}
