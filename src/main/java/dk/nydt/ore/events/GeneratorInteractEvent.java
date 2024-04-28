package dk.nydt.ore.events;

import dk.nydt.ore.Ore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.handlers.database.stores.UserGeneratorStore;
import dk.nydt.ore.objects.GlobalGenerator;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import dk.nydt.ore.utils.VaultUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GeneratorInteractEvent implements Listener {

    private static final UserGeneratorStore userGeneratorStore = StoreHandler.getUserGeneratorStore();

    public GeneratorInteractEvent(Ore plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onGeneratorUpgrade(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if(!event.getPlayer().isSneaking()) return;
        if(!checkCooldown(event.getPlayer().getUniqueId())) return;
        if(event.getClickedBlock() == null) return;

        Player player = event.getPlayer();
        User user = StoreHandler.getUserStore().getUser(player);

        UserGenerator userGenerator = userGeneratorStore.getUserGeneratorAtLocation(event.getClickedBlock().getLocation()).orElse(null);
        if(userGenerator == null) return; //Not a generator, return

        if(!userGenerator.getUser().getUuid().equals(user.getUuid())) {
            player.sendMessage("You can't upgrade " + userGenerator.getUser().getName() + "'s generator!");
            return;
        }

        GlobalGenerator globalGenerator = userGeneratorStore.getGlobalGeneratorByTier(userGenerator.getTier() + 1);
        GlobalGenerator currentGlobalGenerator = userGeneratorStore.getGlobalGeneratorByTier(userGenerator.getTier());
        if(globalGenerator == null) {
            player.sendMessage("You can't upgrade this generator anymore!");
            return;
        }

        if(!VaultUtils.hasEnough(player, (globalGenerator.getBuyValue()-currentGlobalGenerator.getBuyValue()))) {
            player.sendMessage("You don't have enough money to upgrade your generator! You need " + (globalGenerator.getBuyValue()-currentGlobalGenerator.getBuyValue()) + " more!");
            return;
        }

        player.sendMessage("You upgraded your generator to tier " + globalGenerator.getTier() + "!");
        VaultUtils.subtractBalance(player, (globalGenerator.getBuyValue()-currentGlobalGenerator.getBuyValue()));
        userGenerator.upgrade();
    }

    @EventHandler
    public void onGeneratorRemove(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.LEFT_CLICK_BLOCK)) return;
        if (event.getClickedBlock() == null) return;
        Player player = event.getPlayer();
        User user = StoreHandler.getUserStore().getUser(player);

        UserGenerator userGenerator = userGeneratorStore.getUserGeneratorAtLocation(event.getClickedBlock().getLocation()).orElse(null);
        if(userGenerator == null) return; //Not a generator, return

        if(userGenerator.getUser().getUuid().equals(user.getUuid())) {
            player.sendMessage("You broke your generator!");
            userGeneratorStore.deleteGenerator(userGenerator);
            GlobalGenerator globalGenerator = userGeneratorStore.getGlobalGeneratorByTier(userGenerator.getTier());
            if(globalGenerator == null) return; //Tier doesn't exist, return
            event.getClickedBlock().setType(Material.AIR);
            player.getInventory().addItem(globalGenerator.getItemStack());
        } else {
            player.sendMessage("You can't break " + userGenerator.getUser().getName() + "'s generator!");
            event.setCancelled(true);
        }

    }

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public boolean checkCooldown(UUID uuid) {
        if(!cooldowns.containsKey(uuid)) {
            cooldowns.put(uuid, System.currentTimeMillis());
            return true;
        }
        long lastUse = cooldowns.get(uuid);
        if(System.currentTimeMillis() - lastUse > 250) {
            cooldowns.put(uuid, System.currentTimeMillis());
            return true;
        }
        return false;
    }
}
