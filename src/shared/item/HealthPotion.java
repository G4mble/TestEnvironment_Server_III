package shared.item;

import shared.character.PlayerCharacter;

/**
 * implementation of a HealthPotion</br>extends ConsumableModel
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
		super(2, 5, "Heiltrank", "heiltrank.png", 40, paramStackSize);
	}
	
	/**
	 * overrides abstract method from superclass</br>decreases stackSize by 1, increases the players life by super.valueModificator percent
	 * @param paramPlayer PlayerCharakter model of the player consuming the item
	 * @author Staufenberg, Thomas, 5820359
	 * */
	@Override
	public void consume(PlayerCharacter paramPlayer)
	{
		if(paramPlayer.setCurrentLife((paramPlayer.getCurrentLife() + (int)((double)(paramPlayer.getMaximumLife()) * ((double)((double)super.valueModificator / (double)100))))))
			if((super.modifyStackSize(-1)) == 0)
				paramPlayer.getInventory().removeItemFromInventory(this);
	}
}