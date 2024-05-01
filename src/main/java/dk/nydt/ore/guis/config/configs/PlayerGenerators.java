package dk.nydt.ore.guis.config.configs;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dk.nydt.ore.guis.config.ConfigCompliance;
import dk.nydt.ore.guis.config.item.CustomItemStackWrapper;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class PlayerGenerators extends OkaeriConfig implements ConfigCompliance {
    public String title = "&8Generators";
    public ItemStack topDecoration = null;
    public ItemStack bottomDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
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
    public CustomItemStackWrapper generatorItem = new CustomItemStackWrapper(ItemBuilder.from(Material.DIRT)
            .name(Component.text("{playergenerator_name}"))
            .lore(
                    Component.text("&7"),
                    Component.text("&8▍ &7Tier: &f{playergenerator_tier}"),
                    Component.text("&8▍ &7Location: &f{playergenerator_location}"),
                    Component.text("&7"),
                    Component.text("&8&l【 &7Klik for at teleportere. &8&l】")

            ).build());

    public List<ItemStack> getItems() {
        return null;
    }
}
