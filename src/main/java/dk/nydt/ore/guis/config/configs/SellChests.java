package dk.nydt.ore.guis.config.configs;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dk.nydt.ore.guis.config.ConfigCompliance;
import dk.nydt.ore.guis.config.item.CustomItemStackWrapper;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
public class SellChests extends OkaeriConfig implements ConfigCompliance {
    public String title = "&aSell Chest";
    public ItemStack topDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 6);
    public ItemStack bottomDecoration = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 6);

    @Comment("")
    @Comment("Matrialet af dette item er dynamic, og vil ændre sig til sellable item'et.")
    public CustomItemStackWrapper sellableItem = new CustomItemStackWrapper(ItemBuilder.from(Material.DIRT)
            .name(Component.text("{sellable_name}"))
            .lore(
                    Component.text("&7"),
                    Component.text("&8▍ &7Tier: &f{sellable_tier}"),
                    Component.text("&8▍ &7Amount: &f{sellable_amount}"),
                    Component.text("&8▍ &7Price: &2$&a{sellable_price}"),
                    Component.text("&8▍ &7XP: &f{sellable_XP}"),
                    Component.text("&7"),
                    Component.text("&8&l【 &7Klik for at sælge. &8&l】")

            ).build());

    public List<ItemStack> getItems() {
        return null;
    }
}
