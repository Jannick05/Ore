package dk.nydt.ore.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import dk.nydt.ore.Ore;
import dk.nydt.ore.commands.interfaces.ICommand;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.objects.User;
import org.bukkit.entity.Player;

@CommandAlias("admin")
@Subcommand("add")
@CommandPermission("ore.admin")
public class AdminAdd extends BaseCommand implements ICommand {

    @Override
    public String defaultMessage() {
        return "Adds stuff to a user";
    }

    @Default
    public void onDefault(Player player) {
        Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
        lang.getAvailableCommands().send(player);
        lang.getAvailableCommand().send(player, "{command}", "rebirth");
        lang.getAvailableCommand().send(player, "{command}", "level");
        lang.getAvailableCommand().send(player, "{command}", "xp");
        lang.getAvailableCommand().send(player, "{command}", "maxGenerators");
    }

    @Subcommand("rebirth")
    @CommandCompletion("@players 1|2|3|4|5")
    public void onAddRebirth(Player player, OnlinePlayer target, int amount) {
        Player targetPlayer = target.getPlayer();
        StoreManager.getUserStore().addStat(player, targetPlayer, "rebirth", amount);
    }

    @Subcommand("level")
    @CommandCompletion("@players 1|2|3|4|5")
    public void onAddLevel(Player player, OnlinePlayer target, int amount) {
        Player targetPlayer = target.getPlayer();
        StoreManager.getUserStore().addStat(player, targetPlayer, "level", amount);
    }

    @Subcommand("multiplier")
    @CommandCompletion("@players 0.5|1|5")
    public void onAddMulti(Player player, OnlinePlayer target, double amount) {
        Player targetPlayer = target.getPlayer();
        StoreManager.getUserStore().addStat(player, targetPlayer, "multiplier", amount);
    }

    @Subcommand("xp")
    @CommandCompletion("@players 10|20|30|44|50")
    public void onAddXP(Player player, OnlinePlayer target, double amount) {
        Player targetPlayer = target.getPlayer();
        StoreManager.getUserStore().addStat(player, targetPlayer, "xp", amount);
    }

    @Subcommand("maxGenerators")
    @CommandCompletion("@players 1|2|3|4|5")
    public void onAddGenerators(Player player, OnlinePlayer target, int amount) {
        Player targetPlayer = target.getPlayer();
        StoreManager.getUserStore().addStat(player, targetPlayer, "maxGenerators", amount);
    }
}
