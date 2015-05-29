package shared.item;

/**
 * implementation of OneHandedWeapon</br>extends Weapon
 * @author Staufenberg, Thomas, 5820359
 * */
public class OneHandedWeapon extends Weapon
{
	/**
	 * primary constructor used for creating OneHandedWeapon after database read</br>forwards all parameter to the superclass
	 * @author Staufenberg, Thomas, 5820359 
	 * */
	public OneHandedWeapon(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, int paramArmorPartsRevenue)
	{
		//TODO set final values
		super(paramID, paramGoldValue, "Einhandschwert", "ehSchwert.png", paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, paramArmorPartsRevenue);
	}
	
	/**
	 * secondary constructor used to create a new default OneHandedWeapon</br>
	 * sets default values and calls primary contructor
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public OneHandedWeapon(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue)
	{
		this(paramID, paramGoldValue, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, -1);
	}
}