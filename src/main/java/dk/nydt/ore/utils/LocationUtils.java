package dk.nydt.ore.utils;

import org.bukkit.Location;

public class LocationUtils {
    public static String serialize(Location location) {
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ();
    }

    public static Location deserialize(String location) {
        String[] split = location.split(";");
        return new org.bukkit.Location(org.bukkit.Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]));
    }
}
