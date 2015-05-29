package shared.item;

/**
 * implementation of Boots(piece of armament)</br>extends Armament
 * @author Staufenberg, Thomas, 5820359
 * */
public class Boots extends Armament
{

	/**
	 * primary constructor used for creating Boots after database read</br>forwards all parameter to the superclass
	 * @author Staufenberg, Thomas, 5820359 
	 * */
	public Boots(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue,	int paramDefValue, int paramHpValue, int paramArmorPartsRevenue)
	{
		//TODO set final values
		super(paramID, paramGoldValue, "Stiefel", "stiefel.png", paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, 4, paramArmorPartsRevenue);
	}
	
	/**
	 * secondary constructor used to create new default Boots</br>
	 * sets default values and calls primary contructor
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public Boots(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue,	int paramDefValue, int paramHpValue)
	{
		this(paramID, paramGoldValue, paramLvlRestr, paramAtkValue,	paramDefValue, paramHpValue, -1);
	}
}