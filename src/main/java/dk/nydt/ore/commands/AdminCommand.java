package dk.nydt.ore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import dk.nydt.ore.Ore;
import dk.nydt.ore.commands.subcommands.AdminAllGenerators;
import dk.nydt.ore.commands.subcommands.AdminReload;
import dk.nydt.ore.config.configs.Generators;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

@CommandAlias("admin")
@CommandPermission("ore.admin")
public class AdminCommand extends BaseCommand {
    private List<ICommand> commands = new ArrayList<>();
    public void init() {
        Ore.log("Registering " + this.getClass().getSimpleName());
        commands.add(new AdminAllGenerators());
        commands.add(new AdminReload());
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
    public void onDefault() {
        Generators config = (Generators) Ore.getConfigHandler().getConfig("Generators");

        Bukkit.broadcastMessage(config.getGenerators().toString());
    }
}
