package dk.nydt.ore.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import dk.nydt.ore.commands.ICommand;
import dk.nydt.ore.guis.menus.AllGeneratorsMenu;
import org.bukkit.entity.Player;

@CommandAlias("admin")
@Subcommand("allgenerators|allgens|gens")
@CommandPermission("ore.admin")
public class AdminAllGenerators extends BaseCommand implements ICommand {
    @Override
    public String defaultMessage() {
        return "Opens the All Generators GUI for the player.";
    }
    @Default
    public void onDefault(Player player) {
        new AllGeneratorsMenu(player).open();
    }
}
