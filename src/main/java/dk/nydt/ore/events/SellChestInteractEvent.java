package dk.nydt.ore.events;

import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.guis.menus.SellChestMenu;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.database.stores.SellChestStore;
import dk.nydt.ore.objects.SellChest;
import dk.nydt.ore.objects.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SellChestInteractEvent implements Listener {

    private static final SellChestStore sellChestStore = StoreManager.getSellChestStore();
    private static final Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");

    public SellChestInteractEvent(Ore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onSellChestInteract(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (!event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) return;
        Player player = event.getPlayer();
        User user = StoreManager.getUserStore().getUser(player);
        SellChest sellChest = sellChestStore.getSellChestAtLocation(event.getClickedBlock().getLocation()).orElse(null);

        //TODO:
        // - Tjek om de har adgang til plottet. Hvis ja, så har de agang til sellchest.
        // - Der skal bruges plotsquares lorte api

        if(sellChest == null) return; //Not a generator, return

        event.setCancelled(true);

        if(!sellChest.getUser().getUuid().equals(user.getUuid())) {
            lang.getCantOpenOthersSellChest().send(player, "{player}", sellChest.getUser().getName());
            return;
        }

        new SellChestMenu(player, sellChest).open();
    }

    @EventHandler
    public void onSellChestRemove(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.LEFT_CLICK_BLOCK)) return;
        if (!event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) return;
        Player player = event.getPlayer();
        User user = StoreManager.getUserStore().getUser(player);

        SellChest sellChest = sellChestStore.getSellChestAtLocation(event.getClickedBlock().getLocation()).orElse(null);
        if(sellChest == null) return; //Not a sellchest, return


        if(sellChest.getUser().getUuid().equals(user.getUuid())) {
            lang.getSellChestRemoved().send(player);
            sellChestStore.deleteSellChest(user, sellChest);
            event.getClickedBlock().setType(Material.AIR);
            player.getInventory().addItem(sellChestStore.getSellChestItem());
        } else {
            lang.getCantRemoveOthersSellChest().send(player, "{player}", sellChest.getUser().getName());
        }

    }
}
