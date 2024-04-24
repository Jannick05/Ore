package dk.nydt.ore.handlers;

import dk.nydt.ore.Ore;
import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.snakeyaml.YamlSnakeYamlConfigurer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ConfigHandler<T extends OkaeriConfig> {

    private Map<String, T> configMap = new HashMap<>();

    public void load(String config, Class<T> clazz, String folder, OkaeriSerdesPack serdesPack) {
        T configInstance = ConfigManager.create(clazz, it -> {
            it.withConfigurer(new YamlSnakeYamlConfigurer());
            it.withSerdesPack(serdesPack);
            it.withBindFile(new File(Ore.getInstance().getDataFolder() + folder, config.toLowerCase() + ".yml"));
            it.withRemoveOrphans(false);
            it.saveDefaults();
            it.load();
        });

        configMap.put(config.toLowerCase(), configInstance);
        Ore.log("!!!!!JUST FINISHED LOADING " + clazz);
    }

    public void load(String config, Class<T> clazz, String folder) {
        T configInstance = ConfigManager.create(clazz, it -> {
            it.withConfigurer(new YamlSnakeYamlConfigurer());
            it.withBindFile(new File(Ore.getInstance().getDataFolder() + folder, config.toLowerCase() + ".yml"));
            it.withRemoveOrphans(false);
            it.saveDefaults();
            it.load();
        });

        configMap.put(config.toLowerCase(), configInstance);
        Ore.log("!!!!!JUST FINISHED LOADING " + clazz);
    }

    public T getConfig(String config) {
        return configMap.get(config.toLowerCase());
    }

    public void reloadConfigs() {
        configMap.forEach((key, value) -> value.load());
    }
}