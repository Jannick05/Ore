package dk.nydt.ore.utils;

import dk.nydt.ore.Ore;
import dk.nydt.ore.guis.MutualGUI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

@Getter
@SuppressWarnings("rawtypes")
public class GuiUpdater<T extends MutualGUI> {
    private int taskId;
    private final Player player;
    private final T gui;

    public GuiUpdater(Player player, T gui) {
        this.player = player;
        this.gui = gui;

        gui.getGui().setCloseGuiAction(event -> {
            Bukkit.getScheduler().cancelTask(taskId);
        });
    }

    public void start() {
        Runnable runnable = () -> {
            gui.clearItems();
            gui.setItems(false);
            gui.getGui().update();
            player.updateInventory();
        };

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(Ore.getInstance(), runnable, 20, 20);
        this.taskId = task.getTaskId();
    }
}
