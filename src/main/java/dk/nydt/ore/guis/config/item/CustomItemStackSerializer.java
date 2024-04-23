package dk.nydt.ore.guis.config.item;

import com.google.common.collect.Multimap;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class CustomItemStackSerializer implements ObjectSerializer<CustomItemStackWrapper> {

    @Override
    public boolean supports(@NonNull Class<? super CustomItemStackWrapper> type) {
        return type.equals(CustomItemStackWrapper.class);
    }

    @Override
    public void serialize(@NonNull CustomItemStackWrapper object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        ItemStack item = object.getItem();
        if(item.getItemMeta() != null) {
            addSharedMeta(item.getItemMeta(), data, generics);
        }
        Optional<String> texture = getSkullTexture(item);
        if(texture.isPresent()) {
            data.add("texture", texture.get());
        } else {
            addItemData(item, data);
        }
    }

    private void addItemData(ItemStack item, SerializationData data) {
        data.add("material", "DynamicMaterial");
        if(item.getDurability() != 0) {
            data.add("durability", item.getDurability());
        }
        if(item.getAmount() != 1) {
            data.add("amount", item.getAmount());
        }
    }

    private void addSharedMeta(ItemMeta meta, SerializationData data, GenericsDeclaration generics) {
        if(meta.hasDisplayName()) {
            data.add("displayName", meta.getDisplayName());
        }
        if(meta.hasLore()) {
            data.add("lore", meta.getLore());
        }
        if(meta.hasEnchants()) {
            data.add("enchants", meta.getEnchants());
        }
        if(!meta.getItemFlags().isEmpty()) {
            data.add("itemFlags", new ArrayList<>(meta.getItemFlags()));
        }
    }

    @SneakyThrows
    private Optional<String> getSkullTexture(ItemStack item) {
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
    private Object getProfile(SkullMeta meta) {
        Field profileField = meta.getClass().getDeclaredField("profile");
        profileField.setAccessible(true);
        return profileField.get(meta);
    }

    @SneakyThrows
    private String getTexture(Object profile) {
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

    @Override
    public CustomItemStackWrapper deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new CustomItemStackWrapper(data.getValue(ItemStack.class));
    }
}
