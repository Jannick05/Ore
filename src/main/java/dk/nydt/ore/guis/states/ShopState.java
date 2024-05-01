package dk.nydt.ore.guis.states;

import eu.okaeri.placeholders.context.PlaceholderContext;
import lombok.Getter;
import org.bukkit.entity.Player;

public class ShopState extends GUIState {

    @Getter
    public String name;

    @Getter
    public int price;

    @Getter
    public int amount;

    public ShopState(Player player) {
        super(player);
    }

    public ShopState(Player player, String name, int price, int amount) {
        super(player);
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public void applyPlaceholder(PlaceholderContext placeholder) {
        super.applyPlaceholder(placeholder);
        placeholder.with("item_name", this.name);
        placeholder.with("item_price", this.price);
        placeholder.with("item_amount", this.amount);
    }
}
