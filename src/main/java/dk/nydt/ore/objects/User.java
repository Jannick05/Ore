package dk.nydt.ore.objects;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;

import javax.xml.stream.Location;
import java.util.UUID;

@DatabaseTable(tableName = "users")
public class User extends BaseDaoEnabled<User, Integer> {
    @Getter @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @Getter @DatabaseField(columnName = "name")
    private String name;
    @Getter @DatabaseField(columnName = "balance")
    private double balance;
    @Getter @DatabaseField(columnName = "level")
    private int level;
    @Getter @DatabaseField(columnName = "xp")
    private int xp;
    @Getter @DatabaseField(columnName = "prestige")
    private int prestige;
    @Getter @DatabaseField(columnName = "multiplier")
    private double multiplier;
    @Getter @DatabaseField(columnName = "uuid")
    private UUID uuid;
    @Getter @DatabaseField(columnName = "max_generators")
    private int maxGenerators;
    @Getter @ForeignCollectionField(eager = true, columnName = "generators")
    private ForeignCollection<UserGenerator> generators;

    public User() {
    }

    public User(String name, double balance, int level, int xp, int prestige, double multiplier, UUID uuid, int maxGenerators) {
        this.name = name;
        this.balance = balance;
        this.level = level;
        this.xp = xp;
        this.prestige = prestige;
        this.multiplier = multiplier;
        this.uuid = uuid;
        this.maxGenerators = maxGenerators;
    }

    public void addGenerator(int tier, Location location) {
        UserGenerator userGenerator = new UserGenerator(this, tier, location.toString());
        this.generators.add(userGenerator);
    }

    public void removeGenerator(UserGenerator generator) {
        this.generators.remove(generator);
    }
}
