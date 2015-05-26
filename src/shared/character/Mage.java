package shared.character;

import javax.swing.JOptionPane;

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
	private int currentMana, maximumMana;
	
	public Mage(int clientId)
	{
		this(clientId, 1, 0, 70, 10, 1, 567, "Magier", "Magier.png", new InventoryModel(), new StatisticsModel());
		this.generateDefaultEquipment();
	}
	
	public Mage(int clientId, int level, int experiencePoints, int life, int attack, int defense, int characterId, String characterName, String imagePath, InventoryModel inventory, StatisticsModel statistics)
	{
		super(clientId, level, experiencePoints, life, attack, defense, characterId, characterName, -1, -1, true, imagePath, inventory, statistics);
		this.maximumMana = 100;
		this.currentMana = this.maximumMana;
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

	public boolean setCurrentMana(int paramMana)
	{
		if(paramMana < this.currentMana)
		{
			if(paramMana < 0)
				this.currentMana = 0;
			else
				this.currentMana = paramMana;
			return true;
		}
		if(this.currentMana < this.maximumMana)
		{
			if(paramMana <= this.maximumMana)
				this.currentMana = paramMana;
			else
				this.currentMana = this.maximumMana;
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Sie haben bereits volles Mana!", "Achtung!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	
	public int getCurrentMana()
	{
		return this.currentMana;
	}
	
	public int getMaximumMana()
	{
		return this.maximumMana;
	}
}
