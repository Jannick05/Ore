package dk.nydt.ore.guis.config.configs;

import dk.nydt.ore.guis.config.ConfigCompliance;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Shop extends OkaeriConfig implements ConfigCompliance  {
    public String title = "&dShop";
    public ItemStack topDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 6);
    public ItemStack bottomDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 6);

    public Map<String, Map<Material, Map<String, Integer>>> shopItems = new HashMap<String, Map<Material, Map<String, Integer>>>() {{
        put("Blocks", new HashMap<Material, Map<String, Integer>>() {{
            put(Material.DIRT, new HashMap<String, Integer>() {{
                put("Price", 32);
                put("Amount", 64);
            }});
        }});
        put("Tools", new HashMap<Material, Map<String, Integer>>() {{
            put(Material.DIAMOND_PICKAXE, new HashMap<String, Integer>() {{
                put("Price", 100);
                put("Amount", 1);
            }});
        }});
    }};

    public List<ItemStack> getItems() {
        return null;
    }

}
