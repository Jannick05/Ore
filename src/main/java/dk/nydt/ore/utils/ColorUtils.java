package dk.nydt.ore.utils;

import dk.nydt.ore.guis.states.GUIState;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class ColorUtils {
    public static String plain(String s) {
        return s.replaceAll("§", "&");
    }
    public static String[] getColored(String... stringList) {
        if (stringList == null)
            return null;
        for (int i = 0; i < stringList.length; i++)
            stringList[i] = getColored(stringList[i]);
        return stringList;
    }

    public static List<String> getColored(List<String> stringList) {
        if (stringList == null)
            return null;
        for (int i = 0; i < stringList.size(); i++)
            stringList.set(i, getColored(stringList.get(i)));
        return stringList;
    }

    public static String getColored(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String getColored(String s, GUIState state) {
        CompiledMessage compiledMessage = CompiledMessage.of(s);
        PlaceholderContext placeholder = PlaceholderContext.of(compiledMessage);
        state.applyPlaceholder(placeholder);
        return getColored(placeholder.apply());
    }

    public static ItemStack getColored(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null && meta.hasDisplayName()) {
            meta.setDisplayName(getColored(meta.getDisplayName()));
            if (meta.hasLore()) {
                meta.setLore(getColored(meta.getLore()));
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    public static <T extends GUIState> ItemStack getColored(ItemStack item, T state) {

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            CompiledMessage compiledMessage = CompiledMessage.of((meta.getDisplayName() == null) ? item.getType().toString() : meta.getDisplayName());
            PlaceholderContext placeholder = PlaceholderContext.of(compiledMessage);
            state.applyPlaceholder(placeholder);

            meta.setDisplayName(getColored(placeholder.apply()));
            if (meta.hasLore()) {
                List<String> newLore = meta.getLore().stream()
                        .map(CompiledMessage::of)
                        .map(PlaceholderContext::of)
                        .peek(state::applyPlaceholder)
                        .map(PlaceholderContext::apply)
                        .collect(Collectors.toList());
                meta.setLore(getColored(newLore));
            }
            item.setItemMeta(meta);
        }
        return item;
    }

    public static <T extends GUIState> ItemStack getColored(ItemStack item, T state, int slot) {

        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            CompiledMessage compiledMessage = CompiledMessage.of(meta.getDisplayName());
            PlaceholderContext placeholder = PlaceholderContext.of(compiledMessage);
            state.applyPlaceholder(placeholder, slot);

            meta.setDisplayName(getColored(placeholder.apply()));
            if (meta.hasLore()) {
                List<String> newLore = meta.getLore().stream()
                        .map(CompiledMessage::of)
                        .map(PlaceholderContext::of)
                        .peek(state::applyPlaceholder)
                        .map(PlaceholderContext::apply)
                        .collect(Collectors.toList());
                meta.setLore(getColored(newLore));
            }
            item.setItemMeta(meta);
        }
        return item;
    }
}
