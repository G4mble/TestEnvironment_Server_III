package shared.character;

import shared.InventoryModel;
import shared.item.Boots;
import shared.item.ChestArmor;
import shared.item.EquipmentModel;
import shared.item.Helmet;
import shared.item.OneHandedWeapon;
import shared.item.Shield;
import shared.statistics.StatisticsModel;

/**
 * implementation of the warrior class (playable character)
 * @author Staufenberg, Thomas, 5820359
 * */
public class Warrior extends PlayerCharacter
{

	/**
	 * secondary constructor, calls primary constructor</br>
	 * sets all default values and generates default equipment</br>
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public Warrior(int clientId)
	{
		this(clientId, 1, 0, 100, 7, 3, 345, new InventoryModel(), new StatisticsModel());
		this.generateDefaultEquipment();
	}
	
	/**
	 * primary constructor, calls super constructor</br>
	 * creates new Warrior and forwards all given values to the superclass
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public Warrior(int clientId, int level, int experiencePoints, int life, int attack, int defense, int characterId, InventoryModel inventory, StatisticsModel statistics)
	{
		super(clientId, level, experiencePoints, life, attack, defense, characterId, "Krieger", -1, -1, true, "krieger.png", inventory, statistics);
	}

	/**
	 * generates default equipment for the warrior
	 * @author Staufenberg, Thomas, 5820359
	 * */
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
