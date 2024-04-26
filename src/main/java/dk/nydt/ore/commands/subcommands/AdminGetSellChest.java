package dk.nydt.ore.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import dk.nydt.ore.commands.ICommand;
import dk.nydt.ore.handlers.database.StoreHandler;
import org.bukkit.entity.Player;

@CommandAlias("admin")
@Subcommand("getSellChest")
@CommandPermission("ore.admin")
public class AdminGetSellChest extends BaseCommand implements ICommand {

    @Override
    public String defaultMessage() {
        return "gets the sell chest.";
    }

    @Default
    public void onDefault(Player player) {
        player.getInventory().addItem(StoreHandler.getSellChestStore().getSellChestItem());
    }
}
