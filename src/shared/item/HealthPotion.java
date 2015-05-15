package shared.item;

import shared.character.PlayerCharacter;

/**
 * implementation of a global HealthPotionModel</br>extends ConsumableModel
 * @author Staufenberg, Thomas, 5820359
 * */
public class HealthPotion extends ConsumableModel
{
	/**
	 * sets itemID, itemGoldValue, itemName, itemImagePath, valueModificator, stackSize and forwards these values to the super constructor 
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public HealthPotion(int paramStackSize)
	{
		//TODO set correct final values
		super(1234, 1234, "Heiltrank", "heiltrank.png", 1234, paramStackSize);
	}
	
	
	/**
	 * overrides abstract method from superclass</br>decreases stackSize by 1, increases the players life by super.valueModificator percent
	 * @author Staufenberg, Thomas, 5820359
	 * @param paramPlayer PlayerCharakter model of the player consuming the item
	 * @return true: stack is not empty yet, false: stack is empty, Object can be removed from inventory
	 * */
	@Override
	public boolean consume(PlayerCharacter paramPlayer)
	{
		paramPlayer.setLife((paramPlayer.getLife() * (1 + super.valueModificator)));
		
		if((--super.stackSize) == 0)
			return false;
		return true;
	}

}
