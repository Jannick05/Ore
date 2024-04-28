package dk.nydt.ore.utils;

import dk.nydt.ore.Ore;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultUtils {
    private final Ore plugin;
    @Getter
    private static Economy econ = null;
    public VaultUtils(Ore plugin) {
        this.plugin = plugin;
        register();
    }

    public void register() {
        boolean initEconAndWatchStatus = setupEconomy();
        if (!initEconAndWatchStatus) {
            Bukkit.getServer().getPluginManager().disablePlugin(Ore.getInstance());
        }
    }

    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static double getBalance(OfflinePlayer player) {
        return econ.getBalance(player);
    }

    public static void addBalance(OfflinePlayer player, double amount) {
        econ.depositPlayer(player, amount);
    }

    public static void subtractBalance(OfflinePlayer player, double amount) {
        econ.withdrawPlayer(player, amount);
    }

    public static boolean hasEnough(OfflinePlayer player, double amount) {
        return econ.getBalance(player) >= amount;
    }
}
