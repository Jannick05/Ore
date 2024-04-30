package dk.nydt.ore.commands.subcommands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import dk.nydt.ore.Ore;
import dk.nydt.ore.commands.interfaces.ICommand;
import dk.nydt.ore.config.configs.Generators;
import dk.nydt.ore.config.configs.Lang;
import dk.nydt.ore.objects.GlobalGenerator;
import org.bukkit.entity.Player;

import java.util.Map;

@CommandAlias("admin")
@Subcommand("calculate|calc")
@CommandPermission("ore.admin")
public class AdminCalculate extends BaseCommand implements ICommand {
    @Override
    public String defaultMessage() {
        return "Calculates new prices for all generators.";
    }
    @Default
    public void onDefault(Player player) {
        Lang lang = (Lang) Ore.getConfigHandler().getConfig("Lang");
        Generators generators = (Generators) dk.nydt.ore.Ore.getConfigHandler().getConfig("Generators");
        Map<Integer, GlobalGenerator> globalGenerators = generators.getGenerators();
        globalGenerators.forEach((tier, generator) -> {
            double price = 20 * Math.pow(1.8, tier - 1);
            double value = 40 * Math.pow(1.25, tier - 1) / 12;
            double xp = value / 10;

            String formattedPrice = String.format("%.2f", price);
            String formattedValue = String.format("%.2f", value);
            String formattedXp = String.format("%.2f", xp);

            generator.setBuyValue(Double.parseDouble(formattedPrice.replace(",", ".")));
            generator.setDropValue(Double.parseDouble(formattedValue.replace(",", ".")));
            generator.setDropXP(Double.parseDouble(formattedXp.replace(",", ".")));
        });
        generators.save();
        lang.getCalculatedPrices().send(player);
    }
}
