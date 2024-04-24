package dk.nydt.ore.config.serializers;

import dk.nydt.ore.config.Message;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

import java.util.List;

public class MessageSerializer implements ObjectSerializer<Message> {

    @Override
    public boolean supports(@NonNull Class<? super Message> type) {
        return type.equals(Message.class);
    }

    @Override
    public void serialize(@NonNull Message object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.setValue(object.getMessage());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Message deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Message((List<String>) data.getValue(List.class));
    }
}