package dk.nydt.ore.objects;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import dk.nydt.ore.handlers.database.StoreHandler;
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

    @Setter @Getter @DatabaseField(columnName = "xp")
    private double xp;

    @Setter @Getter @DatabaseField(columnName = "xp_needed")
    private double xpNeeded;

    @Setter @Getter @DatabaseField(columnName = "level")
    private int level;

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

    public User(String name, int prestige, double xp, double xpNeeded, int level, double multiplier, UUID uuid, int maxGenerators) {
        this.name = name;
        this.prestige = prestige;
        this.multiplier = multiplier;
        this.xp = xp;
        this.xpNeeded = xpNeeded;
        this.level = level;
        this.uuid = uuid;
        this.maxGenerators = maxGenerators;
    }

    public void addXP(double xp) {
        this.xp += xp;
        if(xp >= xpNeeded) {
            this.level++;
            this.xp = 0;
            this.xpNeeded = this.xpNeeded * 1.5;
        }
        StoreHandler.getUserStore().persist(this);
    }

    public void addGenerator(UserGenerator userGenerator) {
        this.generators.add(userGenerator);
    }

}
