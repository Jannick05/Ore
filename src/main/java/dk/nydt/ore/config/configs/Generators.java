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
                    "&6Wood",
                    Material.WOOD_BUTTON,
                    0.1
            ));
            put(2, new GlobalGenerator(
                    "Stone Generator",
                    Arrays.asList("Generates Stone", "Generates Stone", "Generates Stone"),
                    2,
                    Material.STONE,
                    200,
                    100,
                    "&7Stone",
                    Material.STONE_BUTTON,
                    0.2
            ));
            put(3, new GlobalGenerator(
                    "Coal Generator",
                    Arrays.asList("Generates Coal", "Generates Coal", "Generates Coal"),
                    3,
                    Material.COAL_BLOCK,
                    300,
                    150,
                    "&8Coal",
                    Material.COAL,
                    0.3
            ));
            put(4, new GlobalGenerator(
                    "Iron Generator",
                    Arrays.asList("Generates Iron", "Generates Iron", "Generates Iron"),
                    4,
                    Material.IRON_BLOCK,
                    400,
                    200,
                    "&fIron",
                    Material.IRON_INGOT,
                    0.4
            ));
            put(5, new GlobalGenerator(
                    "Gold Generator",
                    Arrays.asList("Generates Gold", "Generates Gold", "Generates Gold"),
                    5,
                    Material.GOLD_BLOCK,
                    500,
                    250,
                    "&6Gold",
                    Material.GOLD_INGOT,
                    0.5
            ));
            put(6, new GlobalGenerator(
                    "Diamond Generator",
                    Arrays.asList("Generates Diamond", "Generates Diamond", "Generates Diamond"),
                    6,
                    Material.DIAMOND_BLOCK,
                    600,
                    300,
                    "&bDiamond",
                    Material.DIAMOND,
                    0.6
            ));
            put(7, new GlobalGenerator(
                    "Emerald Generator",
                    Arrays.asList("Generates Emerald", "Generates Emerald", "Generates Emerald"),
                    7,
                    Material.EMERALD_BLOCK,
                    700,
                    350,
                    "&aEmerald",
                    Material.EMERALD,
                    0.7
            ));
            put(8, new GlobalGenerator(
                    "Redstone Generator",
                    Arrays.asList("Generates Redstone", "Generates Redstone", "Generates Redstone"),
                    8,
                    Material.REDSTONE_BLOCK,
                    800,
                    400,
                    "&4Redstone",
                    Material.REDSTONE,
                    0.8
            ));
            put(9, new GlobalGenerator(
                    "Quartz Generator",
                    Arrays.asList("Generates Quartz", "Generates Quartz", "Generates Quartz"),
                    9,
                    Material.QUARTZ_BLOCK,
                    900,
                    450,
                    "&fQuartz",
                    Material.QUARTZ,
                    1.0
            ));
        }
    };
}
