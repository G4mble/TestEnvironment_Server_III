package shared.character;

import shared.InventoryModel;
import shared.item.Boots;
import shared.item.ChestArmor;
import shared.item.EquipmentModel;
import shared.item.Helmet;
import shared.item.OneHandedWeapon;
import shared.item.Shield;
import shared.statistics.StatisticsModel;

public class Warrior extends PlayerCharacter
{

	public Warrior(int clientId)
	{
		this(clientId, 19, 0, 100, 7, 3, 345, "Krieger", "krieger.png", new InventoryModel(), new StatisticsModel());
		this.generateDefaultEquipment();
	}
	
	public Warrior(int clientId, int level, int experiencePoints, int life, int attack, int defense, int characterId, String characterName, String imagePath, InventoryModel inventory, StatisticsModel statistics)
	{
		super(clientId, level, experiencePoints, life, attack, defense, characterId, characterName, -1, -1, true, imagePath, inventory, statistics);
	}

	private void generateDefaultEquipment()
	{
		EquipmentModel[] defaultEquip = new EquipmentModel[5];
		defaultEquip[0] = new OneHandedWeapon(-1, 1, 1, 1, 0, 0);
		defaultEquip[1] = new Shield(-1, 1, 1, 0, 1, 1);
		defaultEquip[2] = new Helmet(-1, 1, 1, 0, 1, 1);
		defaultEquip[3] = new ChestArmor(-1, 1, 1, 0, 1, 1);
		defaultEquip[4] = new Boots(-1, 1, 1, 0, 1, 1);
		this.getInventory().setEquipmentList(defaultEquip);
	}
}
