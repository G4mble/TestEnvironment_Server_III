package shared.item;

/**
 * implementation of ChestArmor(piece of armament)</br>extends Armament
 * @author Staufenberg, Thomas, 5820359
 * */
public class ChestArmor extends Armament
{
	/**
	 * primary constructor used for creating ChestArmor after database read</br>forwards all parameter to the superclass
	 * @author Staufenberg, Thomas, 5820359 
	 * */
	public ChestArmor(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, int paramArmorPartsRevenue)
	{
		//TODO set final values
		super(paramID, paramGoldValue, "Brustschutz", "brustschutz.png", paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, 3, paramArmorPartsRevenue);
	}
	
	/**
	 * secondary constructor used to create a new default ChestArmor</br>
	 * sets default values and calls primary contructor
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public ChestArmor(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue)
	{
		this(paramID, paramGoldValue, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, -1);
	}
}