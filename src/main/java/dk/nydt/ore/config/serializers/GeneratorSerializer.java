package dk.nydt.ore.config.serializers;

import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Generators;
import dk.nydt.ore.objects.GlobalGenerator;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.Material;

import java.util.List;

public class GeneratorSerializer implements ObjectSerializer<GlobalGenerator> {

    @Override
    public boolean supports(@NonNull Class<? super GlobalGenerator> type) {
        return type.equals(GlobalGenerator.class);
    }

    @Override
    public void serialize(@NonNull GlobalGenerator object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("name", object.getName());
        data.add("lore", object.getLore());
        data.add("tier", object.getTier());
        data.add("material", object.getMaterial());
        data.add("buyValue", object.getBuyValue());
        data.add("dropValue", object.getDropValue());
        data.add("dropMaterial", object.getDropMaterial());
        data.add("dropXP", object.getDropXP());
    }

    @Override
    @SuppressWarnings("unchecked")
    public GlobalGenerator deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        String name = data.get("name", String.class);
        List<String> lore = data.get("lore", List.class);
        int tier = data.get("tier", Integer.class);
        Material material = Material.valueOf(data.get("material", String.class));
        double buyValue = data.get("buyValue", Double.class);
        double dropValue = data.get("dropValue", Double.class);
        Material dropMaterial = Material.valueOf(data.get("dropMaterial", String.class));
        double dropXP = data.get("dropXP", Double.class);

        GlobalGenerator generator = new GlobalGenerator(name, lore, tier, material, buyValue, dropValue, dropMaterial, dropXP);

        return generator;
    }
}
