package dk.nydt.ore.guis.config;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface ConfigCompliance {
    String getTitle();
    ItemStack getTopDecoration();
    ItemStack getBottomDecoration();
    List<ItemStack> getItems();
}