package shared.character;

import shared.InventoryModel;
import shared.item.Boots;
import shared.item.ChestArmor;
import shared.item.EquipmentModel;
import shared.item.Helmet;
import shared.item.OneHandedWeapon;
import shared.item.Shield;
import shared.statistics.StatisticsModel;

public class Mage extends PlayerCharacter
{

	public Mage(int clientId)
	{
		super(clientId, 1, 0, 70, 10, 1, 567, "Magier", -1, -1, true, "Magier.png", new InventoryModel(), new StatisticsModel());
		this.generateDefaultEquipment();
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
