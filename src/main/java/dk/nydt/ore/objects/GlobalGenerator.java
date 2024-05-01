package dk.nydt.ore.objects;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.util.ItemNbt;
import lombok.Getter;
import lombok.Setter;
import lombok.var;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GlobalGenerator {
    @Setter @Getter
    private String name;
    @Setter @Getter
    private List<String> lore;
    @Setter @Getter
    private int tier;
    @Setter @Getter
    private Material material;
    @Setter @Getter
    private int buyValue;
    @Setter @Getter
    private double dropValue;
    @Setter @Getter
    private String dropMaterialName;
    @Setter @Getter
    private Material dropMaterial;
    @Setter @Getter
    private double dropXP;

    public GlobalGenerator(String name, List<String> lore, int tier, Material material, int buyValue, double dropValue, String dropMaterialName, Material dropMaterial, double dropXP) {
        this.name = name;
        this.lore = lore;
        this.tier = tier;
        this.material = material;
        this.buyValue = buyValue;
        this.dropValue = dropValue;
        this.dropMaterialName = dropMaterialName;
        this.dropMaterial = dropMaterial;
        this.dropXP = dropXP;
    }

    public ItemStack getItemStack() {
        List<Component> lore = new ArrayList<>();
        for(String line : this.lore) {
            lore.add(Component.text(line));
        }
        return ItemBuilder.from(this.material)
                .name(Component.text(this.name))
                .lore(lore)
                .amount(1)
                .setNbt("generator", String.valueOf(this.tier))
                .build();
    }

}
