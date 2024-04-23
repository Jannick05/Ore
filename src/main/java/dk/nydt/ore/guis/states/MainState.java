package dk.nydt.ore.guis.states;

import eu.okaeri.placeholders.context.PlaceholderContext;
import lombok.Getter;
import org.bukkit.entity.Player;

public class MainState extends GUIState {

    @Getter
    public int randomNumber;

    public MainState(Player player, int randomNumber) {
        super(player);
        this.randomNumber = randomNumber;
    }

    public void applyPlaceholder(PlaceholderContext placeholder) {
        super.applyPlaceholder(placeholder);
        placeholder.with("randomNumber", this.randomNumber);
    }
}
