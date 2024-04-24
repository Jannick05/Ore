package dk.nydt.ore.config.serializers;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import lombok.NonNull;

public class GeneratorSerdesPack implements OkaeriSerdesPack {

    @Override
    public void register(@NonNull SerdesRegistry registry) {
        registry.register(new GeneratorSerializer());
    }
}
