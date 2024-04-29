package dk.nydt.ore;

import co.aikar.commands.PaperCommandManager;
import dk.nydt.ore.commands.AdminCommand;
import dk.nydt.ore.commands.RebirthCommand;
import dk.nydt.ore.commands.UpgradeCommand;
import dk.nydt.ore.config.serializers.GeneratorSerdesPack;
import dk.nydt.ore.config.serializers.MessageSerdesPack;
import dk.nydt.ore.config.configs.Config;
import dk.nydt.ore.config.configs.Generators;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.events.*;
import dk.nydt.ore.guis.config.GUISerdesPack;
import dk.nydt.ore.guis.config.configs.AllGenerators;
import dk.nydt.ore.guis.config.configs.PlayerGenerators;
import dk.nydt.ore.guis.config.configs.SellChests;
import dk.nydt.ore.handlers.ConfigHandler;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.tasks.TaskGenerateDrop;
import dk.nydt.ore.utils.PlaceholderUtils;
import dk.nydt.ore.utils.VaultUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class Ore extends JavaPlugin {
    @Getter
    private static PaperCommandManager commandManager;
    @Getter
    private static ConfigHandler configHandler;
    @Getter
    public static StoreManager storeManager;
    @Getter
    private static Ore instance;

    @Override
    @SuppressWarnings("unchecked")
    public void onEnable() {
        this.getLogger().info("--------------------");
        instance = this;

        //Create default directories
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        //Database
        boolean database = initDatabase();
        if (!database) {
            this.getLogger().info("Failed to initialize database");
            this.getLogger().info("Disabling plugin");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //Configs
        configHandler = new ConfigHandler<>();
        configHandler.load("Config", Config.class, "", new MessageSerdesPack());
        configHandler.load("Lang", Lang.class, "/lang", new MessageSerdesPack());
        configHandler.load("Generators", Generators.class, "/generators", new GeneratorSerdesPack());
        configHandler.load("AllGenerators", AllGenerators.class, "/guis", new GUISerdesPack());
        configHandler.load("PlayerGenerators", PlayerGenerators.class, "/guis", new GUISerdesPack());
        configHandler.load("SellChests", SellChests.class, "/guis", new GUISerdesPack());

        //Commands
        commandManager = new PaperCommandManager(this);
        new AdminCommand().init();
        new RebirthCommand().init();
        new UpgradeCommand().init();

        //Events
        new GeneratorPlaceEvent(this);
        new GeneratorBreakEvent(this);
        new GeneratorInteractEvent(this);
        new SellChestPlaceEvent(this);
        new SellChestBreakEvent(this);
        new SellChestInteractEvent(this);

        //PlaceholderAPI
        new PlaceholderUtils(this).register();
        new VaultUtils(this).register();

        //Start generate task
        new TaskGenerateDrop().runTaskTimerAsynchronously(this, 0, 100);

        this.getLogger().info("--------------------");
        this.getLogger().info("ADDING ALL GENERATORS NOW NISSEMAND 123");
    }

    @Override
    public void onDisable() {
        storeManager.closeConnection();
    }

    private boolean initDatabase() {
        try {
            storeManager = new StoreManager();
            storeManager.init(this.getLogger());
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
