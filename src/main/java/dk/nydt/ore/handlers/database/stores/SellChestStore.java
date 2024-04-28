package dk.nydt.ore.handlers.database.stores;

import com.j256.ormlite.dao.Dao;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dk.nydt.ore.guis.config.configs.SellChests;
import dk.nydt.ore.handlers.database.BaseStore;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.objects.SellChest;
import dk.nydt.ore.objects.Sellable;
import dk.nydt.ore.objects.User;
import dk.nydt.ore.objects.UserGenerator;
import dk.nydt.ore.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class SellChestStore extends BaseStore<Integer, SellChest> {
    private StoreHandler storeHandler;

    public SellChestStore(Dao<SellChest, Integer> dao, StoreHandler stores, Logger logger) {
        super(dao, stores, logger);
    }

    public Optional<SellChest> getSellChestAtLocation(Location location) {
        return get("location", LocationUtils.serialize(location));
    }

    public List<Sellable> getContent(SellChest sellChest) {
        return StoreHandler.getSellableStore().getAll("sell_chests", sellChest.getId());
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
            sellChest.addSellableItem(sellable);
        }
        StoreHandler.getSellableStore().persist(sellable);
        persist(sellChest);
    }

    public void unstockSellableItem(SellChest sellChest, Sellable sellable) {
        if (sellChest == null) return;
        StoreHandler.getSellableStore().delete(sellable.getId());
        persist(sellChest);
    }

    public void deleteSellChest(User user, SellChest sellChest) {
        sellChest.getItems().clear();
        delete(sellChest.getId());
        StoreHandler.getUserStore().persist(user);
    }

    public ItemStack getSellChestItem() {
        return ItemBuilder.from(Material.ENDER_CHEST)
                .setName("Sell Chest")
                .setLore("Place this chest to sell items.")
                .setAmount(1)
                .setNbt("sellchest", "true")
                .build();
    }
}
