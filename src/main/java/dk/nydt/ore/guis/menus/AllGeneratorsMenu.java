package dk.nydt.ore.guis.menus;

import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Generators;
import dk.nydt.ore.guis.MutualGUI;
import dk.nydt.ore.guis.config.configs.AllGenerators;
import dk.nydt.ore.guis.states.AllGeneratorsState;
import dk.nydt.ore.guis.states.PlayerGeneratorsState;
import dk.nydt.ore.utils.ColorUtils;
import dk.nydt.ore.utils.GuiUpdater;
import dk.nydt.ore.utils.ItemStackUtils;
import dk.nydt.ore.utils.LocationUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Set;

public class AllGeneratorsMenu extends MutualGUI<AllGenerators, AllGeneratorsState, PaginatedGui> {
    private final ItemStack generatorItemConfig;
    public AllGeneratorsMenu(Player player) {
        AllGenerators allGenerators = (AllGenerators) Ore.getConfigHandler().getConfig("allGenerators");
        AllGeneratorsState state = new AllGeneratorsState(player);
        PaginatedGui gui = create(allGenerators, 5, state, Collections.emptySet());

        this.generatorItemConfig = allGenerators.getGeneratorItem().getItem();

        disableAllInteractions();
        buildDecorations();
        setItems(true);

        new GuiUpdater<>(player, this).start();
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

            ItemStack genItem = generator.getItemStack();
            String name = generator.getName();
            ItemStack item = ItemStackUtils.uniteItemStacks(generatorItemConfig.clone(), genItem);
            AllGeneratorsState state = new AllGeneratorsState(getState().getPlayer(), name, generator.getTier(), generator.getBuyValue(), generator.getDropValue(), generator.getDropXP());

            addPaginatedItem(item, state, event -> {
                event.getWhoClicked().getInventory().addItem(genItem);
            });
        });
    }
}
