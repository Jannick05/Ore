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
public class AllGenerators extends OkaeriConfig implements ConfigCompliance {
    public String title = "&dGenerators";
    public ItemStack topDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
    public ItemStack bottomDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);

    public CustomItemStackWrapper generatorItem = new CustomItemStackWrapper(ItemBuilder.from(Material.DIRT)
            .name(Component.text("{generator_name}"))
            .lore(
                    Component.text("&7"),
                    Component.text("&8▍ &7Tier: &f{generator_tier}"),
                    Component.text("&8▍ &7Pris: &f{generator_price}"),
                    Component.text("&7"),
                    Component.text("&8▍ &7Værdi: &f{generator_value}"),
                    Component.text("&8▍ &7XP: &f{generator_xp}"),
                    Component.text("&7"),
                    Component.text("&8&l【 &7Klik for at købe. &8&l】")

            ).build());


    public List<ItemStack> getItems() {
        return null;
    }
}
