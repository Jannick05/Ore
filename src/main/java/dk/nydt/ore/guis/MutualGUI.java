package dk.nydt.ore.guis;

import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.BaseGui;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import dk.nydt.ore.guis.config.ConfigCompliance;
import dk.nydt.ore.guis.states.GUIState;
import dk.nydt.ore.utils.ColorUtils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class MutualGUI <ConfigType extends ConfigCompliance, S extends GUIState, U extends BaseGui> {
    private U gui;
    private int rows;
    private S state;
    private ConfigType config;

    public <T extends ConfigCompliance> U create(T config, int rows, S state, Set<InteractionModifier> modifiers) {
        this.rows = rows;
        this.config = (ConfigType) config;
        this.state = state;

        this.gui = createGui(modifiers);

        initActions();
        return gui;
    }

    public U createGui(Set<InteractionModifier> modifiers) {
        return (U) new Gui(rows, ColorUtils.getColored(config.getTitle(), getState()), modifiers);
    }

    public void buildDecorations() {
        List<Integer> topSlots = IntStream.range(0, 9).boxed().collect(Collectors.toList());
        List<Integer> bottomSlots = IntStream.range((rows - 1) * 9, rows * 9).boxed().collect(Collectors.toList());

        topSlots.forEach(i -> gui.setItem(i, new GuiItem(config.getTopDecoration())));
        bottomSlots.forEach(i -> gui.setItem(i, new GuiItem(config.getBottomDecoration())));
    }

    public <L extends GUIState> void addItem(int slot, ItemStack item, L state) {
        GuiItem guiItem = new GuiItem(ColorUtils.getColored(item.clone(), state), event -> {
            onClick(slot, event);
        });

        gui.setItem(slot, guiItem);
    }

    public <L extends GUIState> void addItem(int slot, ItemStack item, L state, Consumer<InventoryClickEvent> consumer) {
        GuiItem guiItem = new GuiItem(ColorUtils.getColored(item.clone(), state), consumer::accept);

        gui.setItem(slot, guiItem);
    }


    public void addItem(int slot, ItemStack item) {
        GuiItem guiItem = new GuiItem(ColorUtils.getColored(item.clone()), event -> {
            onClick(slot, event);
        });

        gui.setItem(slot, guiItem);
    }

    public void addItem(int slot, ItemStack item, Consumer<InventoryClickEvent> consumer) {
        GuiItem guiItem = new GuiItem(ColorUtils.getColored(item.clone()), consumer::accept);
        gui.setItem(slot, guiItem);
    }

    public <L extends GUIState> void addPaginatedItem(ItemStack item, L state, Consumer<InventoryClickEvent> consumer) {
        PaginatedGui paginatedGui = (PaginatedGui) gui;
        GuiItem guiItem = new GuiItem(ColorUtils.getColored(item.clone(), state), consumer::accept);
        paginatedGui.addItem(guiItem);
    }

    public <L extends GUIState> void updateItem(int slot, ItemStack item, L state) {
        GuiItem guiItem = new GuiItem(ColorUtils.getColored(item.clone(), state));
        gui.updateItem(slot, guiItem);
    }

    public void clearItems() {
        if (gui != null && gui instanceof PaginatedGui) {
            PaginatedGui paginatedGui = (PaginatedGui) gui;
            paginatedGui.clearPageItems(false);
        } else if (gui != null) {
            for (int i = 0; i < rows * 9; i++) {
                gui.setItem(i, new GuiItem(new ItemStack(Material.AIR)));
            }
        }
    }

    public void open() {
        gui.open(state.getPlayer());
    }

    public void initActions() {
        gui.setCloseGuiAction(this::onClose);
        gui.setDefaultClickAction(this::onAnyClick);
        gui.setDragAction(this::onDrag);
        gui.setPlayerInventoryAction(this::onPlayerAction);
    }

    // Events
    public void setItems(boolean init) {}
    public void onClick(int slot, InventoryClickEvent event) {}
    public void onAnyClick(InventoryClickEvent event) {}
    public void onDrag(InventoryDragEvent event) {}
    public void onPlayerAction(InventoryClickEvent event) {}
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {}


    // Utils
    public void playSound(Sound sound) {
        state.getPlayer().playSound(state.getPlayer().getLocation(), sound, 1, 1);
    }

    public void closeInventory() {
        state.getPlayer().closeInventory();
    }

    public void disableAllInteractions() {
        gui.disableAllInteractions();
    }

    public int getSlot(int row, int column) {
        return (row - 1) * 9 + column;
    }

    public void returnItem(Player player, ItemStack item) {
        if (!getState().getPlayer().isOnline()) return;
        if (player.getInventory().firstEmpty() != -1 && (!getState().getPlayer().isDead())) {
            player.getInventory().addItem(item.clone());
        } else {
            player.getWorld().dropItem(player.getLocation(), item.clone());
        }
    }
}