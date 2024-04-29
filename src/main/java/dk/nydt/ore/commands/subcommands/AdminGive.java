package dk.nydt.ore.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import dk.nydt.ore.Ore;
import dk.nydt.ore.commands.interfaces.ICommand;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.database.StoreManager;
import org.bukkit.entity.Player;

@CommandAlias("admin")
@Subcommand("give")
@CommandPermission("ore.admin")
public class AdminGive extends BaseCommand implements ICommand {

    @Override
    public String defaultMessage() {
        return "Gets the sell chest.";
    }

    @Default
    public void onDefault(Player player) {
        Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
        lang.getAvailableCommands().send(player);
        lang.getAvailableCommand().send(player, "{command}", "sellchest");
    }

    @Subcommand("sellchest")
    @CommandCompletion("@players")
    public void onSellChest(Player player, OnlinePlayer target) {
        Player targetPlayer = target.getPlayer();
        Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
        lang.getSellChestGiven().send(player, "{player}", targetPlayer.getName());
        lang.getSellChestReceived().send(targetPlayer);
        targetPlayer.getInventory().addItem(StoreManager.getSellChestStore().getSellChestItem());
    }
}
