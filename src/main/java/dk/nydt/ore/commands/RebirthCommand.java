package dk.nydt.ore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import dk.nydt.ore.Ore;
import dk.nydt.ore.commands.interfaces.ICommand;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.objects.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

@CommandAlias("rebirth")
public class RebirthCommand extends BaseCommand  {

    private final Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
    private List<ICommand> commands = new ArrayList<>();

    private Map<UUID, Long> ableToRebirth = new HashMap<>();

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
        User user = StoreManager.getUserStore().getUser(player);
        if(user.getLevel() < user.getLevelNeeded()) {
            lang.getNotEnoughLevel().send(player, "{level}", String.valueOf(user.getLevelNeeded()));
            return;
        }
        if(!ableToRebirth.containsKey(player.getUniqueId())) {
            lang.getAcceptRebirth().send(player);
            ableToRebirth(player.getUniqueId());
            return;
        }
        if(ableToRebirth(player.getUniqueId())) {
            lang.getRebirthed().send(player);
            StoreManager.getUserStore().rebirth(user);
        } else {
            lang.getAcceptRebirth().send(player);
        }
    }

    private boolean ableToRebirth(UUID uuid) {
        if(ableToRebirth.containsKey(uuid)) {
            if(ableToRebirth.get(uuid) > System.currentTimeMillis()) {
                ableToRebirth.remove(uuid);
                return true;
            } else {
                ableToRebirth.put(uuid, System.currentTimeMillis() + 10000);
                return false;
            }
        } else {
            ableToRebirth.put(uuid, System.currentTimeMillis() + 10000);
            return false;
        }
    }
}
