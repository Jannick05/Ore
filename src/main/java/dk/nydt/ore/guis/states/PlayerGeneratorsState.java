package dk.nydt.ore.guis.states;

import eu.okaeri.placeholders.context.PlaceholderContext;
import lombok.Getter;
import org.bukkit.entity.Player;

public class PlayerGeneratorsState extends GUIState {

    @Getter
    public String name;

    @Getter
    public int tier;

    @Getter
    public String location;

    public PlayerGeneratorsState(Player player) {
        super(player);
    }

    public PlayerGeneratorsState(Player player, String name, int tier, String location) {
        super(player);
        this.name = name;
        this.tier = tier;
        this.location = location;
    }

    public void applyPlaceholder(PlaceholderContext placeholder) {
        super.applyPlaceholder(placeholder);
        placeholder.with("playergenerator_name", this.name);
        placeholder.with("playergenerator_tier", this.tier);
        placeholder.with("playergenerator_location", this.location);
    }
}
