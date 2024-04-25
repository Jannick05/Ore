package dk.nydt.ore.objects;

import dev.triumphteam.gui.components.util.ItemNbt;
import lombok.Getter;
import lombok.var;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GlobalGenerator {
    @Getter
    private String name;
    @Getter
    private List<String> lore;
    @Getter
    private int tier;
    @Getter
    private Material material;
    @Getter
    private double buyValue;
    @Getter
    private double dropValue;
    @Getter
    private Material dropMaterial;
    @Getter
    private double dropXP;

    public GlobalGenerator(String name, List<String> lore, int tier, Material material, double buyValue, double dropValue, Material dropMaterial, double dropXP) {
        this.name = name;
        this.lore = lore;
        this.tier = tier;
        this.material = material;
        this.buyValue = buyValue;
        this.dropValue = dropValue;
        this.dropMaterial = dropMaterial;
        this.dropXP = dropXP;
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(this.material, 1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.name);
        itemMeta.setLore(this.lore);
        itemStack.setItemMeta(itemMeta);
        return ItemNbt.setString(itemStack, "generator", String.valueOf(this.tier));
    }

}
