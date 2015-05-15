package shared.item;

import shared.character.PlayerCharacter;

/**
 * abstract superclass of all armor equipment pieces</br>extends EquipmentModel
 * @author Staufenberg, Thomas, 5820359
 * */
public abstract class Armament extends EquipmentModel
{
	//slotID: helmet = 2; chest = 3; boots = 4;
	
	/**
	 * forwards paramID, paramGoldValue, paramName, paramImagePath, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue to super constructor</br>
	 * stores remaining parameters in global variables
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public Armament(int paramID, int paramGoldValue, String paramName, String paramImagePath, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, int paramEqSlotID)
	{
		super(paramID, paramGoldValue, paramName, paramImagePath, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, paramEqSlotID);
	}
	
	/**
	 * equips the current armament to the given players inventory
	 * @param paramPlayer PlayerCharacter model of the player equipping the item
	 * @author Staufenberg, Thomas, 5820359
	 * */
	@Override
	public void equip(PlayerCharacter paramPlayer)
	{
		paramPlayer.getInventory().equipItem(this);
	}
}
