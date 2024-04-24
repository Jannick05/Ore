package dk.nydt.ore.guis.menus;

import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Generators;
import dk.nydt.ore.guis.MutualGUI;
import dk.nydt.ore.guis.config.configs.AllGenerators;
import dk.nydt.ore.guis.states.AllGeneratorsState;
import dk.nydt.ore.utils.ColorUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Set;

public class AllGeneratorsMenu extends MutualGUI<AllGenerators, AllGeneratorsState, PaginatedGui> {
    public AllGeneratorsMenu(Player player) {
        AllGenerators allGenerators = (AllGenerators) Ore.getConfigHandler().getConfig("allGenerators");
        AllGeneratorsState state = new AllGeneratorsState(player, 5);
        PaginatedGui gui = create(allGenerators, 5, state, Collections.emptySet());

        disableAllInteractions();
        buildDecorations();
        setItems(true);
    }

    @Override
    public PaginatedGui createGui(Set<InteractionModifier> modifiers) {
        return Gui.paginated()
                .title(Component.text(ColorUtils.getColored(getConfig().getTitle())))
                .rows(getRows())
                .pageSize(36)
                .disableAllInteractions()
                .create();
    }

    @Override
    public void setItems(boolean init) {
        Generators generators = (Generators) Ore.getConfigHandler().getConfig("generators");
        generators.getGenerators().forEach((tier, generator) -> {
            addPaginatedItem(generator.getItemStack(), getState(), event -> {
                event.getWhoClicked().sendMessage("You clicked on a tier " + tier + " generator!");
            });
        });
    }
}
