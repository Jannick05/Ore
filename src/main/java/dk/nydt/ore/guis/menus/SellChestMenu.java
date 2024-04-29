package dk.nydt.ore.guis.menus;

import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import dk.nydt.ore.Ore;
import dk.nydt.ore.guis.MutualGUI;
import dk.nydt.ore.guis.config.configs.SellChests;
import dk.nydt.ore.guis.states.SellChestState;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.objects.SellChest;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.utils.ColorUtils;
import dk.nydt.ore.utils.GuiUpdater;
import dk.nydt.ore.utils.ItemStackUtils;
import dk.nydt.ore.utils.VaultUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Set;

public class SellChestMenu extends MutualGUI<SellChests, SellChestState, PaginatedGui> {

    private final SellChest sellChest;

    private final User user;
    private final ItemStack sellableItemConfig;

    public SellChestMenu(Player player, SellChest sellChest) {
        SellChests sellChests = (SellChests) Ore.getConfigHandler().getConfig("sellchests");
        SellChestState state = new SellChestState(player);
        PaginatedGui gui = create(sellChests, 5, state, Collections.emptySet());

        this.sellChest = sellChest;
        this.user = StoreManager.getUserStore().getUser(player);
        this.sellableItemConfig = sellChests.getSellableItem().getItem();

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
        if(!StoreManager.getSellChestStore().getContent(sellChest).isEmpty()) {
            StoreManager.getSellChestStore().getContent(sellChest).forEach(sellable -> {

                ItemStack itemStack = ItemStackUtils.uniteItemStacks(sellableItemConfig.clone(), sellable.getItemStack());
                SellChestState state = new SellChestState(getState().getPlayer(), sellable);

                addPaginatedItem(itemStack, state, event -> {

                    VaultUtils.addBalance((Player) event.getWhoClicked(), sellable.getPrice()*user.getMultiplier());
                    user.addXP(sellable.getXP());

                    StoreManager.getSellChestStore().unstockSellableItem(sellChest, sellable);
                    playSound(Sound.CLICK);
                    update();
                });
            });
        }
    }
}
