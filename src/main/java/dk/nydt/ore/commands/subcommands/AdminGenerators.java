package dk.nydt.ore.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import dk.nydt.ore.commands.ICommand;
import dk.nydt.ore.guis.menus.AllGeneratorsMenu;
import dk.nydt.ore.guis.menus.PlayerGeneratorsMenu;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@CommandAlias("admin")
@Subcommand("generators|allgenerators|allgens|gens")
@CommandPermission("ore.admin")
public class AdminGenerators extends BaseCommand implements ICommand {
    @Override
    public String defaultMessage() {
        return "Opens the All Generators GUI for the player.";
    }
    @Default
    @CommandCompletion("@players")
    public void onDefault(Player player, @Optional OnlinePlayer target) {
        if (target != null) {
            new PlayerGeneratorsMenu(player, target.getPlayer()).open();
            return;
        }
        new AllGeneratorsMenu(player).open();
    }
}
