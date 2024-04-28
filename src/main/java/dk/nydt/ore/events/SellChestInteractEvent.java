package dk.nydt.ore.events;

import dk.nydt.ore.Ore;
import dk.nydt.ore.guis.menus.SellChestMenu;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.handlers.database.stores.SellChestStore;
import dk.nydt.ore.objects.GlobalGenerator;
import dk.nydt.ore.objects.SellChest;
import dk.nydt.ore.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SellChestInteractEvent implements Listener {

    private static final SellChestStore sellChestStore = StoreHandler.getSellChestStore();

    public SellChestInteractEvent(Ore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSellChestInteract(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) return;
        Player player = event.getPlayer();
        User user = StoreHandler.getUserStore().getUser(player);
        SellChest sellChest = sellChestStore.getSellChestAtLocation(event.getClickedBlock().getLocation()).orElse(null);

        //TODO:
        // - Tjek om de har adgang til plottet. Hvis ja, så har de agang til sellchest.
        // - Der skal bruges plotsquares lorte api

        if(sellChest == null) return; //Not a generator, return

        event.setCancelled(true);

        if(!sellChest.getUser().getUuid().equals(user.getUuid())) {
            player.sendMessage("Dette er ikke din sellchest.");
            return;
        }

        new SellChestMenu(player, sellChest).open();
    }

    @EventHandler
    public void onSellChestRemove(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.LEFT_CLICK_BLOCK)) return;
        if (!event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) return;
        Player player = event.getPlayer();
        User user = StoreHandler.getUserStore().getUser(player);

        SellChest sellChest = sellChestStore.getSellChestAtLocation(event.getClickedBlock().getLocation()).orElse(null);
        if(sellChest == null) return; //Not a sellchest, return


        if(sellChest.getUser().getUuid().equals(user.getUuid())) {
            player.sendMessage("You broke your sellchest!");
            sellChestStore.deleteSellChest(user, sellChest);
            event.getClickedBlock().setType(Material.AIR);
            player.getInventory().addItem(sellChestStore.getSellChestItem());
        } else {
            player.sendMessage("You can't interact with " + sellChest.getUser().getName() + "'s sell chest!");
        }

    }
}
