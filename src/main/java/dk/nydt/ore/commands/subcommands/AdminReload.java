package dk.nydt.ore.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import dk.nydt.ore.Ore;
import dk.nydt.ore.commands.ICommand;
import org.bukkit.entity.Player;

@CommandAlias("admin")
@Subcommand("reload")
@CommandPermission("ore.admin")
public class AdminReload extends BaseCommand implements ICommand {

    @Override
    public String defaultMessage() {
        return "Reloads all configs.";
    }

    @Default
    public void onDefault(Player player) {
        try {
            Ore.getConfigHandler().reloadConfigs();
            player.sendMessage("§aGenindlæste alle conifgs");
        } catch (Exception e) {
            player.sendMessage("§cKunne ikke genindlæse configs");
            e.printStackTrace();
        }
    }
}
