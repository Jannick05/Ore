package dk.nydt.ore.config;

import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.guis.states.GUIState;
import dk.nydt.ore.utils.ColorUtils;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Message {
    @Getter
    private final List<String> message;

    public Message(String... message) {
        this.message = Arrays.asList(message);
    }

    public Message(List<String> message) {
        this.message = message;
    }

    public void send(Player player) {
        Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
        message.forEach(msg -> {
            msg = msg.replace("{prefix}", lang.getPrefix().getMessage().get(0));
            player.sendMessage(ColorUtils.getColored(msg));
        });
    }

    public void send(Player player, String... replacements) {
        Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
        message.forEach(msg -> {
            msg = msg.replace("{prefix}", lang.getPrefix().getMessage().get(0));
            for (int i = 0; i < replacements.length; i += 2) {
                msg = msg.replace(replacements[i], replacements[i + 1]);
            }
            player.sendMessage(ColorUtils.getColored(msg));
        });
    }

    public <T extends GUIState> void send(Player player, T state) {
        message.forEach(msg -> {
            CompiledMessage compiledMessage = CompiledMessage.of(msg);
            PlaceholderContext placeholder = PlaceholderContext.of(compiledMessage);
            applyPlaceholder(placeholder, state);
            player.sendMessage(ColorUtils.getColored(placeholder.apply()));
        });
    }

    public <T extends GUIState> void broadcast(T state) {
        message.forEach(msg -> {
            CompiledMessage compiledMessage = CompiledMessage.of(msg);
            PlaceholderContext placeholder = PlaceholderContext.of(compiledMessage);
            applyPlaceholder(placeholder, state);
        });
    }

    public void applyPlaceholder(PlaceholderContext placeholder) {
        Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
        placeholder.with("prefix", lang.getPrefix());
    }

    public <T extends GUIState> void applyPlaceholder(PlaceholderContext placeholder, T state) {
        Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
        placeholder.with("prefix", lang.getPrefix());
        state.applyPlaceholder(placeholder);
    }
}