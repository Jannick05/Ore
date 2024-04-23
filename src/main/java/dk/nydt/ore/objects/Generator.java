package dk.nydt.ore.objects;

import org.bukkit.Material;

public class Generator {
    private String name;
    private int lore;
    private int tier;
    private Material block;
    private double buyValue;
    private double dropValue;
    private double DropXP;

    public Generator() {
    }

    public Generator(String name, int lore, int tier, Material block, double buyValue, double dropValue, double DropXP) {
        this.name = name;
        this.lore = lore;
        this.tier = tier;
        this.block = block;
        this.buyValue = buyValue;
        this.dropValue = dropValue;
        this.DropXP = DropXP;
    }

    @Override
    public Generator clone() {
        return new Generator(name, lore, tier, block, buyValue, dropValue, DropXP);
    }
}
