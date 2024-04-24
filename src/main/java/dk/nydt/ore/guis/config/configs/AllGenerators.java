package dk.nydt.ore.guis.config.configs;

import dk.nydt.ore.guis.config.ConfigCompliance;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class AllGenerators extends OkaeriConfig implements ConfigCompliance {
    public String title = "&dGenerators";
    public ItemStack topDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
    public ItemStack bottomDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);


    public List<ItemStack> getItems() {
        return null;
    }
}
