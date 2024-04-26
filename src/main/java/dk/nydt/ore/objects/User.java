package dk.nydt.ore.objects;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import dk.nydt.ore.handlers.database.StoreHandler;
import dk.nydt.ore.handlers.database.stores.UserGeneratorStore;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

import java.util.UUID;

@DatabaseTable(tableName = "users")
public class User extends BaseDaoEnabled<User, Integer> {
    @Setter @Getter @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @Setter @Getter @DatabaseField(columnName = "name")
    private String name;

    @Setter @Getter @DatabaseField(columnName = "prestige")
    private int prestige;
    @Setter @Getter @DatabaseField(columnName = "multiplier")
    private double multiplier;
    @Setter @Getter @DatabaseField(columnName = "uuid")
    private UUID uuid;
    @Setter @Getter @DatabaseField(columnName = "max_generators")
    private int maxGenerators;
    @Setter @Getter @ForeignCollectionField(eager = true, columnName = "generators")
    private ForeignCollection<UserGenerator> generators;

    @Setter @Getter @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "sell_chests")
    private SellChest sellChest;

    public User() {
    }

    public User(String name, int prestige, double multiplier, UUID uuid, int maxGenerators) {
        this.name = name;
        this.prestige = prestige;
        this.multiplier = multiplier;
        this.uuid = uuid;
        this.maxGenerators = maxGenerators;
    }

    public void addGenerator(int tier, Location location) {
        UserGenerator userGenerator = new UserGenerator(this, tier, location);
        this.generators.add(userGenerator);
    }
}
