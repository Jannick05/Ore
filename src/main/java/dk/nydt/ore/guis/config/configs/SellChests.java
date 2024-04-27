package dk.nydt.ore.guis.config.configs;

import dk.nydt.ore.guis.config.ConfigCompliance;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class SellChests extends OkaeriConfig implements ConfigCompliance {
    public String title = "&aSell Chest";
    public ItemStack topDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 6);
    public ItemStack bottomDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 6);


    public List<ItemStack> getItems() {
        return null;
    }
}
