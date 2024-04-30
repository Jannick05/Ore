package dk.nydt.ore.guis.states;

import eu.okaeri.placeholders.context.PlaceholderContext;
import lombok.Getter;
import org.bukkit.entity.Player;

public class ShopState extends GUIState {

    @Getter
    public int price;

    @Getter
    public int amount;

    public ShopState(Player player) {
        super(player);
    }

    public ShopState(Player player, int price, int amount) {
        super(player);
        this.price = price;
        this.amount = amount;
    }

    public void applyPlaceholder(PlaceholderContext placeholder) {
        super.applyPlaceholder(placeholder);
        placeholder.with("item_price", this.price);
        placeholder.with("item_amount", this.amount);
    }
}
