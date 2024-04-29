package dk.nydt.ore.utils;

import dk.nydt.ore.Ore;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.objects.User;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlaceholderUtils extends PlaceholderExpansion {

    private final Ore plugin;

    public PlaceholderUtils(Ore plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "Nydt";
    }

    @Override
    public String getIdentifier() {
        return "ore";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        Player player = (Player) offlinePlayer;
        User user = StoreManager.getUserStore().getUser(player);

        if(params.equalsIgnoreCase("balance")){
            return VaultUtils.getBalance(player)+"";
        }
        if(params.equalsIgnoreCase("level")){
            return user.getLevel()+"";
        }
        if(params.equalsIgnoreCase("xp")){
            return user.getXp()+"";
        }
        if(params.equalsIgnoreCase("xpNeeded")){
            return user.getXpNeeded()+"";
        }
        if(params.equalsIgnoreCase("rebirth")){
            return user.getRebirth()+"";
        }
        if(params.equalsIgnoreCase("multiplier")){
            return user.getMultiplier()+"";
        }
        if(params.equalsIgnoreCase("maxGenerators")){
            return user.getMaxGenerators()+"";
        }
        if(params.equalsIgnoreCase("generators")){
            return user.getGenerators().size()+"";
        }

        return null;
    }
}
