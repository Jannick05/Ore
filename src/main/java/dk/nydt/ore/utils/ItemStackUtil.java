package dk.nydt.ore.utils;

import com.google.common.collect.Multimap;
import lombok.SneakyThrows;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Optional;

public class ItemStackUtil {

    public static ItemStack uniteItemStacks(ItemStack item1, ItemStack item2) {
        ItemStack result = item2.clone();

        ItemMeta actualMeta = result.getItemMeta();
        ItemMeta configMeta = item1.getItemMeta();

        if (actualMeta != null && configMeta != null) {
            if (configMeta.hasDisplayName()) {
                actualMeta.setDisplayName(configMeta.getDisplayName());
            }
            if (configMeta.hasLore()) {
                actualMeta.setLore(configMeta.getLore());
            }

            //check if it's a skull
            if (actualMeta instanceof SkullMeta && configMeta instanceof SkullMeta) {
                SkullMeta actualSkullMeta = (SkullMeta) actualMeta;
                SkullMeta configSkullMeta = (SkullMeta) configMeta;
                if (configSkullMeta.hasOwner()) {
                    actualSkullMeta.setOwner(configSkullMeta.getOwner());
                } else {
                    Optional<String> texture = getSkullTexture(item1);
                    texture.ifPresent(actualSkullMeta::setOwner);
                }
            }
        }
        result.setItemMeta(actualMeta);
        return result;
    }

    @SneakyThrows
    private static Optional<String> getSkullTexture(ItemStack item) {
        if(item.getItemMeta() instanceof SkullMeta) {
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            Object profile = getProfile(meta);
            try {
                return Optional.of(getTexture(profile));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return Optional.empty();
    }

    @SneakyThrows
    private static Object getProfile(SkullMeta meta) {
        Field profileField = meta.getClass().getDeclaredField("profile");
        profileField.setAccessible(true);
        return profileField.get(meta);
    }

    @SneakyThrows
    private static String getTexture(Object profile) {
        Field propertiesField = profile.getClass().getDeclaredField("properties");
        propertiesField.setAccessible(true);
        Multimap<String, Object> properties = (Multimap<String, Object>) propertiesField.get(profile);

        Collection<Object> textures = properties.get("textures");
        if (textures == null || textures.isEmpty()) {
            return null;
        }

        Object textureProperty = textures.iterator().next();
        Field valueField = textureProperty.getClass().getDeclaredField("value");
        valueField.setAccessible(true);
        return (String) valueField.get(textureProperty);
    }
}
