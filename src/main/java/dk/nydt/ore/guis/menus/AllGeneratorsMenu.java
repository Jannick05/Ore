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
import dk.nydt.ore.utils.GuiUpdater;
import dk.nydt.ore.utils.ItemStackUtils;
import dk.nydt.ore.utils.VaultUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Set;
import java.util.function.Consumer;

public class AllGeneratorsMenu extends MutualGUI<AllGenerators, AllGeneratorsState, PaginatedGui> {
    private final ItemStack generatorItemConfig;
    public AllGeneratorsMenu(Player player) {
        AllGenerators allGenerators = (AllGenerators) Ore.getConfigHandler().getConfig("allGenerators");
        AllGeneratorsState state = new AllGeneratorsState(player);
        PaginatedGui gui = create(allGenerators, 6, state, Collections.emptySet());

        this.generatorItemConfig = allGenerators.getGeneratorItem().getItem();

        disableAllInteractions();
        buildBottomDecoration();
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
        addItem(45, getConfig().getPreviousPage(), (Consumer<InventoryClickEvent>) event -> {
            getGui().previous();
        });
        addItem(53, getConfig().getNextPage(), (Consumer<InventoryClickEvent>) event -> {
            getGui().next();
        });
        Generators generators = (Generators) Ore.getConfigHandler().getConfig("generators");
        generators.getGenerators().forEach((tier, generator) -> {

            ItemStack genItem = generator.getItemStack();
            String name = generator.getName();
            ItemStack item = ItemStackUtils.uniteItemStacks(generatorItemConfig.clone(), genItem);
            AllGeneratorsState state = new AllGeneratorsState(getState().getPlayer(), name, generator.getTier(), generator.getBuyValue(), generator.getDropValue(), generator.getDropXP());

            addPaginatedItem(item, state, event -> {
                Player player = (Player) event.getWhoClicked();
                if(player.isOp()) {
                    player.getInventory().addItem(generator.getItemStack());
                    return;
                }
                if(VaultUtils.hasEnough((Player) event.getWhoClicked(), generator.getBuyValue())) {
                    VaultUtils.subtractBalance((Player) event.getWhoClicked(), generator.getBuyValue());
                    player.getInventory().addItem(generator.getItemStack());
                }
            });
        });
    }
}
