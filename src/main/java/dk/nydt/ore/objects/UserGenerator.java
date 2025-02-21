package dk.nydt.ore.objects;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.misc.BaseDaoEnabled;
import com.j256.ormlite.table.DatabaseTable;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.utils.LocationUtils;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

@DatabaseTable(tableName = "user_generators")
public class UserGenerator extends BaseDaoEnabled<UserGenerator, Integer> {

    @Getter @DatabaseField(generatedId = true, columnName = "id")
    private int id;

    @Getter @DatabaseField(foreign = true, foreignAutoRefresh = true, columnName = "user")
    private User user;

    @Setter @Getter @DatabaseField(columnName = "tier")
    private int tier;

    @Getter @DatabaseField(columnName = "location")
    private String location;

    public UserGenerator() {
    }

    public UserGenerator(User user, int tier, Location location) {
        this.user = user;
        this.tier = tier;
        this.location = LocationUtils.serialize(location);
    }

    public void upgrade() {
        StoreManager.getUserGeneratorStore().upgrade(this);
    }
}
