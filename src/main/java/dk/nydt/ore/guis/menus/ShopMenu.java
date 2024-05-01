package dk.nydt.ore.guis.menus;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.guis.MutualGUI;
import dk.nydt.ore.guis.config.configs.Shop;
import dk.nydt.ore.guis.states.ShopState;
import dk.nydt.ore.utils.ColorUtils;
import dk.nydt.ore.utils.ItemStackUtils;
import dk.nydt.ore.utils.VaultUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ShopMenu extends MutualGUI<Shop, ShopState, PaginatedGui> {
    private final Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
    private final ItemStack shopItem;
    public ShopMenu(Player player) {
        Shop shop = (Shop) Ore.getConfigHandler().getConfig("shop");
        ShopState state = new ShopState(player);
        PaginatedGui gui = create(shop, 1, state, Collections.emptySet());

        this.shopItem = shop.getShopItem().getItem();

        disableAllInteractions();
        setItems(true);
    }

    @Override
    public PaginatedGui createGui(Set<InteractionModifier> modifiers) {
        return Gui.paginated()
                .title(Component.text(ColorUtils.getColored(getConfig().getTitle())))
                .rows(getRows())
                .disableAllInteractions()
                .create();
    }

    @Override
    public void setItems(boolean init) {
        List<String> isAdded = new ArrayList<>();
        getConfig().getShopItems().forEach((category, items) -> {
            items.forEach((material, data) -> {
                if(isAdded.contains(category)) return;
                isAdded.add(category);
                addPaginatedItem(ItemBuilder.from(material).name(Component.text(ColorUtils.getColored("&7&o"+category))).flags(ItemFlag.HIDE_ATTRIBUTES).build(), getState(), event -> {
                    getGui().clearPageItems(false);
                    getConfig().getShopItems().get(category).forEach((item, info) -> {
                        ItemStack itemStack = ItemStackUtils.uniteItemStacks(shopItem.clone(), new ItemStack(item, info.get("Amount")));
                        ShopState state = new ShopState(getState().getPlayer(), item.name(), info.get("Price"), info.get("Amount"));
                        addPaginatedItem(itemStack, state, event1 -> {
                            if(VaultUtils.hasEnough((Player) event1.getWhoClicked(), info.get("Price"))) {
                                VaultUtils.subtractBalance((Player) event1.getWhoClicked(), info.get("Price"));
                                event1.getWhoClicked().getInventory().addItem(new ItemStack(item, info.get("Amount")));
                                lang.getBuySuccess().send((Player) event1.getWhoClicked(), "{price}", String.valueOf(info.get("Price")), "{item}", item.name(), "{amount}", String.valueOf(info.get("Amount")));
                            } else {
                                lang.getBuyFail().send((Player) event1.getWhoClicked(), "{price}", String.valueOf(info.get("Price")), "{item}", item.name(), "{amount}", String.valueOf(info.get("Amount")));
                            }
                        });
                    });
                    addItem(7, getConfig().getPreviousPage(), (Consumer<InventoryClickEvent>) event1 -> {
                        getGui().previous();
                    });
                    addItem(8, getConfig().getNextPage(), (Consumer<InventoryClickEvent>) event1 -> {
                        getGui().next();
                    });
                    getGui().open(getState().getPlayer());
                });
                addItem(7, getConfig().getPreviousPage(), (Consumer<InventoryClickEvent>) event -> {
                    getGui().previous();
                });
                addItem(8, getConfig().getNextPage(), (Consumer<InventoryClickEvent>) event -> {
                    getGui().next();
                });
            });
        });
    }
}
