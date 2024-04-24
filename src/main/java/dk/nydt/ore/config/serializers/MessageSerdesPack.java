package dk.nydt.ore.config.serializers;

import dk.nydt.ore.guis.config.item.ItemStackSerializer;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class MessageSerdesPack implements OkaeriSerdesPack {

    @Override
    public void register(@NonNull SerdesRegistry registry) {
        registry.register(new MessageSerializer());
        registry.registerExclusive(ItemStack.class, new ItemStackSerializer());
    }
}