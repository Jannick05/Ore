package dk.nydt.ore.guis.config.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CustomItemStackWrapper {

    public ItemStack item;
    public CustomItemStackWrapper(ItemStack item) {
        item.setType(Material.SKULL);
        this.item = item;
    }

    public ItemStack getItem() {
        return this.item.clone();
    }
}
