package dk.nydt.ore.objects;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import dk.nydt.ore.database.StoreManager;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@DatabaseTable(tableName = "users")
public class User extends BaseDaoEnabled<User, Integer> {

    @Setter @Getter @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @Setter @Getter @DatabaseField(columnName = "name")
    private String name;

    @Setter @Getter @DatabaseField(columnName = "rebirth")
    private int rebirth;

    @Setter @Getter @DatabaseField(columnName = "multiplier")
    private double multiplier;

    @Setter @Getter @DatabaseField(columnName = "xp")
    private double xp;

    @Setter @Getter @DatabaseField(columnName = "xp_needed")
    private double xpNeeded;

    @Setter @Getter @DatabaseField(columnName = "level")
    private int level;

    @Setter @Getter @DatabaseField(columnName = "level_needed")
    private int levelNeeded;

    @Setter @Getter @DatabaseField(columnName = "uuid")
    private UUID uuid;

    @Setter @Getter @DatabaseField(columnName = "max_generators")
    private int maxGenerators;

    @Setter @Getter @ForeignCollectionField(eager = true, columnName = "generators")
    private ForeignCollection<UserGenerator> generators;

    @Setter @Getter @DatabaseField(foreign = true, foreignAutoCreate = true, foreignAutoRefresh = true, columnName = "sell_chests")
    private SellChest sellChest;

    public User() {
    }

    public User(String name, int rebirth, double xp, double xpNeeded, int level, int levelNeeded, double multiplier, UUID uuid, int maxGenerators) {
        this.name = name;
        this.rebirth = rebirth;
        this.multiplier = multiplier;
        this.xp = xp;
        this.xpNeeded = xpNeeded;
        this.level = level;
        this.levelNeeded = levelNeeded;
        this.uuid = uuid;
        this.maxGenerators = maxGenerators;
    }

    public void addXP(double xp) {
        this.xp += xp;
        if(xp >= xpNeeded) {
            this.level++;
            this.maxGenerators++;
            this.xp = 0;
            this.xpNeeded = this.xpNeeded * 1.5;
        }
        StoreManager.getUserStore().persist(this);
    }

}
