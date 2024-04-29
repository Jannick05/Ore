package dk.nydt.ore.utils;

import com.google.common.base.Strings;
import dk.nydt.ore.Ore;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.objects.User;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        if(params.equalsIgnoreCase("coins")){
            return 0+"";
        }
        if(params.equalsIgnoreCase("gems")){
            return 0+"";
        }
        if(params.equalsIgnoreCase("bar")){
            return getProgressBar((int) Math.round(user.getXp()), (int) Math.round(user.getXpNeeded()), 10, '•', ChatColor.GREEN, ChatColor.RED);
        }
        if(params.equalsIgnoreCase("date")){
            String pattern = "MM/dd/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(new Date());
        }

        return null;
    }

    private String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor, ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }
}
