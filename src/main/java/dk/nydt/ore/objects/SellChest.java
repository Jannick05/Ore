package dk.nydt.ore.objects;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import dk.nydt.ore.utils.LocationUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@DatabaseTable(tableName = "sell_chests")
public class SellChest extends BaseDaoEnabled<SellChest, Integer> {

    @Getter @Setter
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @Getter @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user")
    private User user;

    @Getter @DatabaseField(columnName = "location")
    private String location;

    @Getter @ForeignCollectionField(eager = true, columnName = "items")
    private ForeignCollection<Sellable> items;

    public SellChest() {
    }

    public SellChest(User user, Location location) {
        this.user = user;
        this.location = LocationUtils.serialize(location);
    }

    public void addSellableItem(Sellable sellable) {
        items.add(sellable);
    }

    public void removeSellableItem(Sellable sellable) {
        items.remove(sellable);
    }

    public boolean hasSellableItem(int tier) {
        return items.stream().anyMatch(sellable -> sellable.getTier() == tier);
    }

    public Sellable getSellableItem(int tier) {
        return items.stream().filter(sellable -> sellable.getTier() == tier).findFirst().orElse(null);
    }
}
