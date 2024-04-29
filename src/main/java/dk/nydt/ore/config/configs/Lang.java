package dk.nydt.ore.config.configs;

import dk.nydt.ore.config.Message;
import eu.okaeri.configs.OkaeriConfig;
import lombok.Getter;

@Getter
public class Lang extends OkaeriConfig {
    public Message prefix = new Message("&e&lORE");
    public Message noPermission = new Message("{prefix} &cDu har ikke tilladelse til at udføre denne handling.");
    public Message reloadSuccess = new Message("{prefix} &aGenindlæste alle configs.");
    public Message reloadFail = new Message("{prefix} &cKunne ikke genindlæse configs.");
    public Message availableCommands = new Message("{prefix}", "&aTilgængelig kommandoer:");
    public Message availableCommand = new Message("&a{command} &7- &e{description}");
    public Message sellChestGiven = new Message("{prefix} &aGav salgskisten til &e{player}");
    public Message sellChestReceived = new Message("{prefix} &aDu modtog en salgskiste.");
    public Message sellChestRemoved = new Message("{prefix} &aDu fjernede salgskisten.");
    public Message sellChestPlaced = new Message("{prefix} &aDu placerede en salgskiste.");
    public Message sellChestAlreadyPlaced = new Message("{prefix} &cDu har allerede en salgskiste.");
    public Message cantRemoveOthersSellChest = new Message("{prefix} &cDu kan ikke fjerne &e{player}'s &csalgskiste.");
    public Message cantOpenOthersSellChest = new Message("{prefix} &cDu kan ikke åbne &e{player}'s &csalgskiste.");
    public Message cantUpgradeOthersGenerator = new Message("{prefix} &cDu kan ikke opgradere {player}'s generator.");
    public Message cantBreakOthersGenerator = new Message("{prefix} &cDu kan ikke fjerne {player}'s generator.");
    public Message generatorMaxTierReached = new Message("{prefix} &cDu har nået den maksimale tier for denne generator.");
    public Message generatorUpgraded = new Message("{prefix} &aDu har opgraderet din generator til tier &e{tier}");
    public Message generatorPlaced = new Message("{prefix} &aDu har placeret en generator af tier &e{tier}");
    public Message generatorRemoved = new Message("{prefix} &aDu har fjernet en generator af tier &e{tier}");
    public Message notEnoughMoney = new Message("{prefix} &cDu har ikke råd til at købe denne generator, du mangler &e{money} &cfor at købe den.");
    public Message notEnoughLevel = new Message("{prefix} &cDu har ikke nået det krævede level for at udføre denne handling, du skal være i level &e{level}");
    public Message generatorMaxPlacedReached = new Message("{prefix} &cDu har nået den maksimale mængde af generatorer du kan placere.");
    public Message teleportToGenerator = new Message("{prefix} &aDu blev teleporteret til generatoren.");
    public Message acceptRebirth = new Message("{prefix} &aSkriv kommandoen igen for at acceptere genfødslen.");
    public Message rebirthed = new Message("{prefix} &aDu er blevet genfødt.");
    public Message statAdded = new Message("{prefix} &aDu har tilføjet &e{amount} &a{stat} til &e{player}");
    public Message statReceived = new Message("{prefix} &aDu har modtaget &e{amount} &a{stat}");
}
