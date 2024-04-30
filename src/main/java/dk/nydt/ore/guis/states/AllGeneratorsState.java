package dk.nydt.ore.guis.states;

import eu.okaeri.placeholders.context.PlaceholderContext;
import lombok.Getter;
import org.bukkit.entity.Player;

public class AllGeneratorsState extends GUIState {

    @Getter
    public String name;

    @Getter
    public int tier;

    @Getter
    public double price;

    @Getter
    public double value;

    @Getter
    public double xp;

    public AllGeneratorsState(Player player) {
        super(player);
    }

    public AllGeneratorsState(Player player, String name, int tier, double price, double value, double xp) {
        super(player);
        this.name = name;
        this.tier = tier;
        this.price = price;
        this.value = value;
        this.xp = xp;
    }

    public void applyPlaceholder(PlaceholderContext placeholder) {
        super.applyPlaceholder(placeholder);
        placeholder.with("generator_name", this.name);
        placeholder.with("generator_tier", this.tier);
        placeholder.with("generator_price", this.price);
        placeholder.with("generator_value", this.value);
        placeholder.with("generator_xp", this.xp);
    }
}
