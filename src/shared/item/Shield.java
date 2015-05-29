package shared.item;

/**
 * implementation of Shield(piece of armament)</br>extends Armament
 * @author Staufenberg, Thomas, 5820359
 * */
public class Shield extends Armament
{
	/**
	 * primary constructor used for creating Shield after database read</br>forwards all parameter to the superclass
	 * @author Staufenberg, Thomas, 5820359 
	 * */
	public Shield(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, int paramArmorPartsRevenue)
	{
		//TODO set final values
		super(paramID, paramGoldValue, "Schild", "schild.png", paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, 1, paramArmorPartsRevenue);
	}
	
	/**
	 * secondary constructor used to create new default Shield</br>
	 * sets default values and calls primary contructor
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public Shield(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue)
	{
		this(paramID, paramGoldValue, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, -1);
	}
}