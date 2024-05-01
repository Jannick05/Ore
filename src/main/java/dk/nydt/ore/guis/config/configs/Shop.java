package dk.nydt.ore.guis.config.configs;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dk.nydt.ore.guis.config.ConfigCompliance;
import dk.nydt.ore.guis.config.item.CustomItemStackWrapper;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Shop extends OkaeriConfig implements ConfigCompliance  {
    public String title = "&dShop";
    public ItemStack topDecoration = null;
    public ItemStack bottomDecoration = null;

    public Map<String, Map<Material, Map<String, Integer>>> shopItems = new HashMap<String, Map<Material, Map<String, Integer>>>() {{
        put("Blocks", new HashMap<Material, Map<String, Integer>>() {{
            put(Material.DIRT, new HashMap<String, Integer>() {{
                put("Price", 32);
                put("Amount", 64);
            }});
        }});
        put("Tools", new HashMap<Material, Map<String, Integer>>() {{
            put(Material.DIAMOND_PICKAXE, new HashMap<String, Integer>() {{
                put("Price", 100);
                put("Amount", 1);
            }});
        }});
    }};

    public ItemStack nextPage = ItemBuilder.skull()
            .texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGJmOGI2Mjc3Y2QzNjI2NjI4M2NiNWE5ZTY5NDM5NTNjNzgzZTZmZjdkNmEyZDU5ZDE1YWQwNjk3ZTkxZDQzYyJ9fX0=")
            .name(Component.text("&fNæste side"))
            .lore(Component.text("&7"), Component.text("&8▍ &7Klik her for at se næste side"), Component.text("&7"))
            .build();

    public ItemStack previousPage = ItemBuilder.skull()
            .texture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjc2MjMwYTBhYzUyYWYxMWU0YmM4NDAwOWM2ODkwYTQwMjk0NzJmMzk0N2I0ZjQ2NWI1YjU3MjI4ODFhYWNjNyJ9fX0=")
            .name(Component.text("&fForrige side"))
            .lore(Component.text("&7"), Component.text("&8▍ &7Klik her for at se forrige side"), Component.text("&7"))
            .build();

    public CustomItemStackWrapper shopItem = new CustomItemStackWrapper(ItemBuilder.from(Material.DIRT)
            .name(Component.text("&7&o{item_name}"))
            .lore(
                    Component.text("&7"),
                    Component.text("&8▍ &7Antal: &f{item_price}"),
                    Component.text("&8▍ &7Pris: &f{item_amount}"),
                    Component.text("&7"),
                    Component.text("&8&l【 &7Klik for at købe. &8&l】"),
                    Component.text("&7")

            ).build());

    public List<ItemStack> getItems() {
        return null;
    }

}
