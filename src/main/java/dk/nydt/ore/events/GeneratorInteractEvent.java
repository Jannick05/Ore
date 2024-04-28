package dk.nydt.ore.events;

import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.database.stores.UserGeneratorStore;
import dk.nydt.ore.objects.GlobalGenerator;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import dk.nydt.ore.utils.VaultUtils;
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

    private static final UserGeneratorStore userGeneratorStore = StoreManager.getUserGeneratorStore();
    private static final Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");

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
        User user = StoreManager.getUserStore().getUser(player);

        UserGenerator userGenerator = userGeneratorStore.getUserGeneratorAtLocation(event.getClickedBlock().getLocation()).orElse(null);
        if(userGenerator == null) return; //Not a generator, return

        if(!userGenerator.getUser().getUuid().equals(user.getUuid())) {
            lang.getCantUpgradeOthersGenerator().send(player, "{player}", userGenerator.getUser().getName());
            return;
        }

        GlobalGenerator globalGenerator = userGeneratorStore.getGlobalGeneratorByTier(userGenerator.getTier() + 1);
        GlobalGenerator currentGlobalGenerator = userGeneratorStore.getGlobalGeneratorByTier(userGenerator.getTier());
        if(globalGenerator == null) {
            lang.getGeneratorMaxTierReached().send(player);
            return;
        }

        if(!VaultUtils.hasEnough(player, (globalGenerator.getBuyValue()-currentGlobalGenerator.getBuyValue()))) {
            lang.getNotEnoughMoney().send(player, "{money}", String.valueOf((globalGenerator.getBuyValue()-currentGlobalGenerator.getBuyValue())));
            return;
        }

        lang.getGeneratorUpgraded().send(player, "{tier}", String.valueOf(userGenerator.getTier() + 1));
        VaultUtils.subtractBalance(player, (globalGenerator.getBuyValue()-currentGlobalGenerator.getBuyValue()));
        userGenerator.upgrade();
    }

    @EventHandler
    public void onGeneratorRemove(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.LEFT_CLICK_BLOCK)) return;
        if (event.getClickedBlock() == null) return;
        Player player = event.getPlayer();
        User user = StoreManager.getUserStore().getUser(player);

        UserGenerator userGenerator = userGeneratorStore.getUserGeneratorAtLocation(event.getClickedBlock().getLocation()).orElse(null);
        if(userGenerator == null) return; //Not a generator, return

        if(userGenerator.getUser().getUuid().equals(user.getUuid())) {
            lang.getGeneratorRemoved().send(player, "{tier}", String.valueOf(userGenerator.getTier()));
            userGeneratorStore.deleteGenerator(userGenerator);
            GlobalGenerator globalGenerator = userGeneratorStore.getGlobalGeneratorByTier(userGenerator.getTier());
            if(globalGenerator == null) return; //Tier doesn't exist, return
            event.getClickedBlock().setType(Material.AIR);
            player.getInventory().addItem(globalGenerator.getItemStack());
        } else {
            lang.getCantBreakOthersGenerator().send(player, "{player}", userGenerator.getUser().getName());
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
