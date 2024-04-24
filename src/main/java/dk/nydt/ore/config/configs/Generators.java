package dk.nydt.ore.config.configs;

import dk.nydt.ore.objects.GlobalGenerator;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class Generators extends OkaeriConfig {

    public Map<Integer, GlobalGenerator> generators = new HashMap<Integer, GlobalGenerator>() {{
            put(1, new GlobalGenerator(
                    "Wood Generator",
                    Arrays.asList("Generates wood", "Generates wood", "Generates wood"),
                    1,
                    Material.WOOD,
                    100,
                    50,
                    Material.WOOD_BUTTON,
                    0.1
            ));
            put(2, new GlobalGenerator(
                    "Stone Generator",
                    Arrays.asList("Generates Stone", "Generates Stone", "Generates Stone"),
                    2,
                    Material.WOOD,
                    200,
                    100,
                    Material.STONE_BUTTON,
                    0.2
            ));
        }
    };
}
