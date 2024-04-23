package dk.nydt.ore;

import co.aikar.commands.PaperCommandManager;
import dk.nydt.ore.handlers.ConfigHandler;
import dk.nydt.ore.handlers.database.StoreHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ore extends JavaPlugin {
    @Getter
    private static PaperCommandManager commandManager;
    @Getter
    private static ConfigHandler configHandler;
    @Getter
    public static StoreHandler storeHandler;
    @Getter
    private static Ore instance;

    @Override
    @SuppressWarnings("unchecked")
    public void onEnable() {
        this.getLogger().info("--------------------");
        instance = this;
        configHandler = new ConfigHandler<>();
        commandManager = new PaperCommandManager(this);

        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        boolean database = initDatabase();
        if (!database) {
            this.getLogger().info("Failed to initialize database");
            this.getLogger().info("Disabling plugin");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.getLogger().info("--------------------");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private boolean initDatabase() {
        try {
            storeHandler = new StoreHandler();
            storeHandler.init(this.getLogger());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            this.getLogger().info("Failed to initialize database");
            return false;
        }
    }

    public static void log(String message) {
        Ore.getInstance().getLogger().info(message);
    }
}
