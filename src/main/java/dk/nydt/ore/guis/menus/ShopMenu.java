package dk.nydt.ore.guis.menus;

import dev.triumphteam.gui.guis.Gui;
import dk.nydt.ore.Ore;
import dk.nydt.ore.guis.MutualGUI;
import dk.nydt.ore.guis.config.configs.Shop;
import dk.nydt.ore.guis.states.ShopState;
import org.bukkit.entity.Player;

import java.util.Collections;

public class ShopMenu extends MutualGUI<Shop, ShopState, Gui> {
    public ShopMenu(Player player) {
        Shop shop = (Shop) Ore.getConfigHandler().getConfig("shop");
        ShopState state = new ShopState(player);
        Gui gui = create(shop, 5, state, Collections.emptySet());

        disableAllInteractions();
        buildDecorations();
        setItems(true);
    }

    @Override
    public void setItems(boolean init) {

    }
}
