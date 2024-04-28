package dk.nydt.ore.database.stores;

import com.j256.ormlite.dao.Dao;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dk.nydt.ore.database.BaseStore;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.objects.SellChest;
import dk.nydt.ore.objects.Sellable;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.utils.LocationUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class SellChestStore extends BaseStore<Integer, SellChest> {
    private StoreManager storeHandler;

    public SellChestStore(Dao<SellChest, Integer> dao, StoreManager stores, Logger logger) {
        super(dao, stores, logger);
    }

    public Optional<SellChest> getSellChestAtLocation(Location location) {
        return get("location", LocationUtils.serialize(location));
    }

    public List<Sellable> getContent(SellChest sellChest) {
        return StoreManager.getSellableStore().getAll("sell_chests", sellChest.getId());
    }

    public void stockSellableItem(User user, int tier) {
        SellChest sellChest = user.getSellChest();
        if (sellChest == null) return;

        Sellable sellable;
        if(sellChest.hasSellableItem(tier)) {
            sellable = sellChest.getSellableItem(tier);
            sellable.setAmount(sellable.getAmount() + 1);
        } else {
            sellable = new Sellable(sellChest, tier, 1);
        }
        StoreManager.getSellableStore().persist(sellable);
        persist(sellChest);
    }

    public void unstockSellableItem(SellChest sellChest, Sellable sellable) {
        if (sellChest == null) return;
        StoreManager.getSellableStore().delete(sellable.getId());
        persist(sellChest);
    }

    public void deleteSellChest(User user, SellChest sellChest) {
        sellChest.getItems().clear();
        delete(sellChest.getId());
        StoreManager.getUserStore().persist(user);
    }

    public ItemStack getSellChestItem() {
        return ItemBuilder.from(Material.ENDER_CHEST)
                .name(Component.text("Sell Chest"))
                .lore(Component.text("Right click to place a sell chest"))
                .amount(1)
                .setNbt("sellchest", "true")
                .build();
    }
}
