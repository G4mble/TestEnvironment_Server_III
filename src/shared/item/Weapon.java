package shared.item;

import shared.InventoryModel;
import shared.character.PlayerCharacter;

/**
 * abstract superclass of all weapon equipment pieces</br>extends EquipmentModel
 * @author Staufenberg, Thomas, 5820359
 * */
public abstract class Weapon extends EquipmentModel
{
	private boolean isTwoHand;
	
	/**
	 * forwards paramID, paramGoldValue, paramName, paramImagePath, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue to super constructor</br>
	 * paramSlotID has to be set to 0</br>
	 * stores remaining parameters in global variables
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public Weapon(int paramID, int paramGoldValue, String paramName, String paramImagePath, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, boolean paramIsTwoHand)
	{
		super(paramID, paramGoldValue, paramName, paramImagePath, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, 0);
		
		this.isTwoHand = paramIsTwoHand;
	}
	
	/**
	 * equips the current weapon to the given players inventory, calls</br>
	 * if isTwoHand == true -> the weapon is added to slot 0 AND 1 to indicate so
	 * @param paramPlayer PlayerCharacter model of the player equipping the item
	 * @author Staufenberg, Thomas, 5820359
	 * */
	@Override
	public void equip(PlayerCharacter paramPlayer)
	{
		InventoryModel currentInventory = paramPlayer.getInventory();
		
		currentInventory.equipItem(this);
		
		if(this.isTwoHand)
			currentInventory.equipItem(this);
	}
	
	/**
	 * @return {true|false} wether the current weapon blocks two weapon slots (true) or not
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public boolean isTwoHand()
	{
		return this.isTwoHand;
	}
}
