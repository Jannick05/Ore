package dk.nydt.ore.guis.menus;

import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.PaginatedGui;
import dk.nydt.ore.Ore;
import dk.nydt.ore.config.configs.Generators;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.database.StoreManager;
import dk.nydt.ore.guis.MutualGUI;
import dk.nydt.ore.guis.config.configs.PlayerGenerators;
import dk.nydt.ore.guis.states.PlayerGeneratorsState;
import dk.nydt.ore.objects.UserGenerator;
import dk.nydt.ore.utils.ColorUtils;
import dk.nydt.ore.utils.GuiUpdater;
import dk.nydt.ore.utils.ItemStackUtils;
import dk.nydt.ore.utils.LocationUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class PlayerGeneratorsMenu extends MutualGUI<PlayerGenerators, PlayerGeneratorsState, PaginatedGui> {
    private final Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
    private Player targetPlayer;
    private final ItemStack generatorItemConfig;
    public PlayerGeneratorsMenu(Player player, Player targetPlayer) {
        PlayerGenerators playerGenerators = (PlayerGenerators) Ore.getConfigHandler().getConfig("playergenerators");
        PlayerGeneratorsState state = new PlayerGeneratorsState(player);
        PaginatedGui gui = create(playerGenerators, 6, state, Collections.emptySet());

        this.targetPlayer = targetPlayer;
        this.generatorItemConfig = playerGenerators.getGeneratorItem().getItem();

        disableAllInteractions();
        buildBottomDecoration();
        setItems(true);
        new GuiUpdater<>(player, this).start();
    }

    @Override
    public PaginatedGui createGui(Set<InteractionModifier> modifiers) {
            return Gui.paginated()
            .title(Component.text(ColorUtils.getColored(getConfig().getTitle())))
            .rows(getRows())
            .pageSize(36)
            .disableAllInteractions()
            .create();
            }

    @Override
    public void setItems(boolean init) {
        addItem(45, getConfig().getPreviousPage(), (Consumer<InventoryClickEvent>) event -> {
            getGui().previous();
        });
        addItem(53, getConfig().getNextPage(), (Consumer<InventoryClickEvent>) event -> {
            getGui().next();
        });
        Generators generators = (Generators) Ore.getConfigHandler().getConfig("generators");
        List<UserGenerator> userGenerators = StoreManager.getUserGeneratorStore().getAll("user", StoreManager.getUserStore().getUser(targetPlayer));
        if(!userGenerators.isEmpty()) {
            userGenerators.forEach(userGenerator -> {

                ItemStack genItem = generators.getGenerators().get(userGenerator.getTier()).getItemStack();
                String name = generators.getGenerators().get(userGenerator.getTier()).getName();
                ItemStack item = ItemStackUtils.uniteItemStacks(generatorItemConfig.clone(), genItem);
                PlayerGeneratorsState state = new PlayerGeneratorsState(targetPlayer, name, userGenerator.getTier(), userGenerator.getLocation());

                addPaginatedItem(item, state, event -> {
                    Location location = LocationUtils.deserialize(state.getLocation());
                    event.getWhoClicked().teleport(location);
                    lang.getTeleportToGenerator().send((Player) event.getWhoClicked());
                });
            });
        }
    }
}
