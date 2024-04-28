package dk.nydt.ore.guis.states;

import dk.nydt.ore.objects.Sellable;
import eu.okaeri.placeholders.context.PlaceholderContext;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class SellChestState extends GUIState {

    @Getter @Setter
    private Sellable sellable;

    @Getter @Setter
    private String name;

    @Getter @Setter
    private int tier;

    @Getter @Setter
    private int amount;

    @Getter @Setter
    private double price;

    @Getter @Setter
    private double xp;

    public SellChestState(Player player, Sellable sellable) {
        super(player);
        this.sellable = sellable;
        this.name = sellable.getName();
        this.tier = sellable.getTier();
        this.amount = sellable.getAmount();
        this.price = sellable.getPrice();
        this.xp = sellable.getXP();
    }

    public void applyPlaceholder(PlaceholderContext placeholder) {
        super.applyPlaceholder(placeholder);

        placeholder.with("sellable_name", this.name);
        placeholder.with("sellable_tier", this.tier);
        placeholder.with("sellable_amount", this.amount);
        placeholder.with("sellable_price", this.price);
        placeholder.with("sellable_XP", this.xp);
    }
}
