package dk.nydt.ore.guis.config;

import dk.nydt.ore.guis.config.item.CustomItemStackSerializer;
import dk.nydt.ore.guis.config.item.CustomItemStackWrapper;
import dk.nydt.ore.guis.config.item.ItemStackSerializer;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class GUISerdesPack implements OkaeriSerdesPack {
    @Override
    public void register(@NonNull SerdesRegistry registry) {
        registry.registerExclusive(CustomItemStackWrapper.class, new CustomItemStackSerializer());
        registry.registerExclusive(ItemStack.class, new ItemStackSerializer());
    }
}
