package dk.nydt.ore.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Generators;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.handlers.database.stores.UserGeneratorStore;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@DatabaseTable(tableName = "sellables")
public class Sellable extends BaseDaoEnabled<Sellable, Integer> {

    @Getter @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @Getter @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "sell_chests")
    private SellChest sellChest;

    @Getter @DatabaseField(columnName = "tier")
    private int tier;

    public Sellable() {

    }

    public Sellable(SellChest sellChest, int tier) {
        this.sellChest = sellChest;
        this.tier = tier;
    }

    private final UserGeneratorStore userGeneratorStore = StoreHandler.getUserGeneratorStore();

    public ItemStack getItemStack() {
        GlobalGenerator generator = userGeneratorStore.getGlobalGeneratorByTier(this.tier);
        return ItemBuilder.from(generator.getDropMaterial()).build();
    }

    public double getPrice() {
        GlobalGenerator generator = userGeneratorStore.getGlobalGeneratorByTier(this.tier);
        return generator.getDropValue();
    }

    public double getXP() {
        GlobalGenerator generator = userGeneratorStore.getGlobalGeneratorByTier(this.tier);
        return generator.getDropXP();
    }
}
