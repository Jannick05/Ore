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
    public String title = "&dGenerators";
    public ItemStack topDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
    public ItemStack bottomDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
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
