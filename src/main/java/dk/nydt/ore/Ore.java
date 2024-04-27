package dk.nydt.ore;

import co.aikar.commands.PaperCommandManager;
import dk.nydt.ore.commands.AdminCommand;
import dk.nydt.ore.config.serializers.GeneratorSerdesPack;
import dk.nydt.ore.config.serializers.MessageSerdesPack;
import dk.nydt.ore.config.configs.Config;
import dk.nydt.ore.config.configs.Generators;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.events.*;
import dk.nydt.ore.guis.config.GUISerdesPack;
import dk.nydt.ore.guis.config.configs.AllGenerators;
import dk.nydt.ore.guis.config.configs.SellChests;
import dk.nydt.ore.handlers.ConfigHandler;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.objects.GlobalGenerator;
import dk.nydt.ore.tasks.TaskGenerateDrop;
import dk.nydt.ore.utils.PlaceholderUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

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

        //Create default directories
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        //Commands
        commandManager = new PaperCommandManager(this);
        new AdminCommand().init();

        //Database
        boolean database = initDatabase();
        if (!database) {
            this.getLogger().info("Failed to initialize database");
            this.getLogger().info("Disabling plugin");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //Events
        new GeneratorPlaceEvent(this);
        new GeneratorBreakEvent(this);
        new GeneratorInteractEvent(this);
        new SellChestPlaceEvent(this);
        new SellChestBreakEvent(this);
        new SellChestInteractEvent(this);

        //Configs
        configHandler = new ConfigHandler<>();
        configHandler.load("Config", Config.class, "", new MessageSerdesPack());
        configHandler.load("Lang", Lang.class, "/lang", new MessageSerdesPack());
        configHandler.load("Generators", Generators.class, "/generators", new GeneratorSerdesPack());
        configHandler.load("AllGenerators", AllGenerators.class, "/guis", new GUISerdesPack());
        configHandler.load("SellChests", SellChests.class, "/guis", new GUISerdesPack());

        //PlaceholderAPI
        new PlaceholderUtils(this).register();

        //Start generate task
        BukkitTask generateTask = new TaskGenerateDrop().runTaskTimerAsynchronously(this, 0, 100);

        this.getLogger().info("--------------------");
        this.getLogger().info("ADDING ALL GENERATORS NOW NISSEMAND 123");
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
