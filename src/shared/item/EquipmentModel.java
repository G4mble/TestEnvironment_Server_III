package shared.item;

import shared.character.PlayerCharacter;

/**
 * abstract superclass of all player equipment pieces: armament, weapons</br>extends ItemModel
 * @author Staufenberg, Thomas, 5820359
 * */
public abstract class EquipmentModel extends ItemModel
{
	private int levelRestriction, attackValue, defenseValue, hpValue, armorPartsRevenue, equipSlotID;
	
	/**
	 * forwards paramID, paramGoldValue, paramName, paramImagePath to super constructor</br>
	 * stores remaining parameters in global variables
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public EquipmentModel(int paramID, int paramGoldValue, String paramName, String paramImagePath, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, int paramEquipSlotID, int paramArmorPartsRevenue)
	{
		super(paramID, paramGoldValue, paramName, paramImagePath);
		
		this.levelRestriction = paramLvlRestr;
		this.attackValue = paramAtkValue;
		this.defenseValue = paramDefValue;
		this.hpValue = paramHpValue;
		this.equipSlotID = paramEquipSlotID;
		if(paramArmorPartsRevenue == -1)
			this.armorPartsRevenue = this.calculateArmorPartsRevenue();
		else
			this.armorPartsRevenue = paramArmorPartsRevenue;
	}
	
	/**
	 * equips the current item to the given players equipment
	 * @param paramPlayer PlayerCharacter model of the player equipping the item
	 * @return true: item successfully added</br>false: inventory full
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public boolean equip(PlayerCharacter paramPlayer)
	{
		return paramPlayer.getInventory().equipItem(this);
	}
	
	/**
	 * calculates the amount of armorParts gained by salvaging the current item
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private int calculateArmorPartsRevenue()
	{
		return (Math.max(1, (this.levelRestriction / 5)));
	}
	
	/**
	 * salvages the given Item, this results in the Item being destroyed</br>
	 * adds the armorPartsRevenue of the salvaged Item to the players armorPartsCount
	 * @param paramPlayer PlayerCharacter model of the player the item belongs to
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void salvage(PlayerCharacter paramPlayer)
	{
		paramPlayer.getInventory().salvageEquipment(this);
	}
	
	/**
	 * removes the current item from the given players equipment</br>
	 * if paramAddToInventory == true: adds the item to the players inventory
	 * @param paramPlayer PlayerCharacter model of the player the item belongs to
	 * @param paramAddToInventory wheter the item should be added to the inventory (true) or not (false)
	 * @author Staufenberg, Thomas, 5820359
	 * @return true: item successfully removed from the equipment and added to the inventory</br>false: inventory full
	 * */
	protected boolean removeFromEquip(PlayerCharacter paramPlayer, boolean paramAddToInventory)
	{
		return paramPlayer.getInventory().removeItemFromEquip(this, paramAddToInventory);
	}
	
	/**
	 * removes the current item from the given players equipment</br>
	 * adds the item to the players inventory (calls overloaded method with paramAddToInventory = true)
	 * @param paramPlayer PlayerCharacter model of the player the item belongs to
	 * @return true: item successfully removed from the equipment and added to the inventory</br>false: inventory full
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public boolean removeFromEquip(PlayerCharacter paramPlayer)
	{
		return this.removeFromEquip(paramPlayer, true);
	}
	
	/**
	 * @return minimum level to equip the current item
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getLevelRestriction()
	{
		return this.levelRestriction;
	}
	
	/**
	 * @return additional attack damage gained by equipping the current item
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getAttackValue()
	{
		return this.attackValue;
	}
	
	/**
	 * @return additional defense power gained by equipping the current item
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getDefenseValue()
	{
		return this.defenseValue;
	}
	
	/**
	 * @return additional health boost gained by equipping the current item
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getHpValue()
	{
		return this.hpValue;
	}
	
	/**
	 * @return revenue in armorParts gained by salvaging the current item
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getArmorPartsRevenue()
	{
		return this.armorPartsRevenue;
	}
	
	/**
	 * @return ID of the slot the item is to be equipped in, depends on item type
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getEquipSlotID()
	{
		return this.equipSlotID;
	}

	/**
	 * overwrites the current itemID in ItemModel with the given one
	 * @param paramID the ID to be set
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void setItemID(int paramID)
	{
		super.itemID = paramID;
	}
	
	public void setArmorPartsRevenue(int paramArmorParts)
	{
		this.armorPartsRevenue = paramArmorParts;
	}
}
