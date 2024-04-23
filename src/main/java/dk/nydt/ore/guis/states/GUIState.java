package dk.nydt.ore.guis.states;

import eu.okaeri.placeholders.context.PlaceholderContext;
import lombok.Getter;
import org.bukkit.entity.Player;

public class GUIState {

    @Getter
    private final Player player;

    public GUIState(Player player) {
        this.player = player;
    }

    public void applyPlaceholder(PlaceholderContext placeholder) {
        placeholder.with("player", (this.player != null) ? this.player.getDisplayName(): "Ingen...");
    }

    public void applyPlaceholder(PlaceholderContext placeholder, int slot) {}
}