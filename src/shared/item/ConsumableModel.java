package shared.item;

import shared.character.PlayerCharacter;

/**
 * abstract superclass of all consumables</br>extends ItemModel
 * @author Staufenberg, Thomas, 5820359
 * */
public abstract class ConsumableModel extends ItemModel
{
	protected int stackSize, valueModificator;
	
	/**
	 * forwards paramID, paramGoldValue, paramName, paramImagePath to super constructor</br>
	 * stores remaining parameters in global variables
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public ConsumableModel(int paramID, int paramGoldValue, String paramName, String paramImagePath, int paramValueMod, int paramStackSize)
	{
		super(paramID, paramGoldValue, paramName, paramImagePath);
		
		this.valueModificator = paramValueMod;
		this.stackSize = paramStackSize;
	}

	
	/**
	 * abstract method to consume the current item
	 * @return {true|false}
	 * @param paramPlayer PlayerCharakter model of the player consuming the item
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public abstract boolean consume(PlayerCharacter paramPlayer);
	
	/**
	 * increases the stackSize of the current consumable
	 * @param paramModificator amount by which the stackSize is to be increased
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void increaseStackSize(int paramModificator)
	{
		this.stackSize += paramModificator;
	}
	
	/**
	 * @return stackSize of the current consumable
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getStackSize()
	{
		return this.stackSize;
	}
}