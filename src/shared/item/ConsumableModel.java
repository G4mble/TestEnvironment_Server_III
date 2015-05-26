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
		super(paramID, (paramGoldValue * paramStackSize), paramName, paramImagePath);
		
		this.valueModificator = paramValueMod;
		this.stackSize = paramStackSize;
	}

	
	/**
	 * abstract method to consume the current item
	 * @return {true|false}
	 * @param paramPlayer PlayerCharakter model of the player consuming the item
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public abstract void consume(PlayerCharacter paramPlayer);
	
	/**
	 * increases the stackSize of the current consumable</br>recalculates the itemGoldValue depending on the new stackSize
	 * @param paramModificator amount by which the stackSize is to be modified (+ / -)
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int modifyStackSize(int paramModificator)
	{
		int itemGoldValuePerUnit = (super.itemGoldValue / this.stackSize);
		this.stackSize += paramModificator;
		super.itemGoldValue = (itemGoldValuePerUnit * this.stackSize);
		return this.stackSize;
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