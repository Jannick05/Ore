package dk.nydt.ore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dk.nydt.ore.Ore;
import dk.nydt.ore.commands.interfaces.ICommand;
import dk.nydt.ore.commands.subcommands.AdminAdd;
import dk.nydt.ore.commands.subcommands.AdminGenerators;
import dk.nydt.ore.commands.subcommands.AdminGive;
import dk.nydt.ore.commands.subcommands.AdminReload;
import dk.nydt.ore.config.configs.Lang;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("admin")
@CommandPermission("ore.admin")
public class AdminCommand extends BaseCommand {
    private List<ICommand> commands = new ArrayList<>();
    public void init() {
        Ore.log("Registering " + this.getClass().getSimpleName());
        commands.add(new AdminGenerators());
        commands.add(new AdminReload());
        commands.add(new AdminGive());
        commands.add(new AdminAdd());
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
        Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
        lang.getAvailableCommands().send(player);
        lang.getAvailableCommand().send(player, "{command}", "give");
        lang.getAvailableCommand().send(player, "{command}", "add");
        lang.getAvailableCommand().send(player, "{command}", "reload");
        lang.getAvailableCommand().send(player, "{command}", "generators");
    }
}
