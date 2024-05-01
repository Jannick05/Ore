package dk.nydt.ore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dk.nydt.ore.Ore;
import dk.nydt.ore.commands.interfaces.ICommand;
import dk.nydt.ore.guis.menus.ShopMenu;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("shop")
public class ShopCommand extends BaseCommand {
    private List<ICommand> commands = new ArrayList<>();

    public void init() {
        Ore.log("Registering " + this.getClass().getSimpleName());
        registerCommands();
    }

    public void registerCommands() {
        Ore.getCommandManager().registerCommand(this);
        commands.forEach(command -> {
            Ore.getCommandManager().registerCommand((BaseCommand) command);
            Ore.log(" - Registered " + command.getClass().getSimpleName() + " command!");
        });
    }

    @Default
    public void onDefault(Player player) {
        new ShopMenu(player).open();
    }
}
