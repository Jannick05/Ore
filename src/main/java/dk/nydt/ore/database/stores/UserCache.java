package dk.nydt.ore.database.stores;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class UserCache {
    private final Map<UUID, OfflinePlayer> userCache = new ConcurrentHashMap<>();

    public void persistUser(UUID uuid, OfflinePlayer player) {
        userCache.put(uuid, player);
    }

    public OfflinePlayer persistAndGetUser(UUID uuid, OfflinePlayer player) {
        userCache.put(uuid, player);
        return userCache.get(uuid);
    }

    public OfflinePlayer persistOrGetAsync(UUID uuid, Player player) {
        if (userCache.containsKey(uuid)) {
            return userCache.get(uuid);
        }

        userCache.put(uuid, player);
        return player;
    }

    public CompletableFuture<OfflinePlayer> persistOrGetAsync(UUID uuid) {
        if (uuid == null) return CompletableFuture.completedFuture(null);

        if (userCache.containsKey(uuid)) {
            return CompletableFuture.completedFuture(userCache.get(uuid));
        }

        return CompletableFuture.supplyAsync(() -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            userCache.put(uuid, offlinePlayer);
            return offlinePlayer;
        });
    }

    public OfflinePlayer getUser(UUID uuid) {
        return userCache.get(uuid);
    }

    public void removeUser(UUID uuid) {
        userCache.remove(uuid);
    }

    public void clear() {
        userCache.clear();
    }
}
