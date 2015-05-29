package shared.item;

/**
 * implementation of TwoHandedWeapon</br>extends Weapon
 * @author Staufenberg, Thomas, 5820359
 * */
public class TwoHandedWeapon extends Weapon
{
	/**
	 * primary constructor used for creating TwoHandedWeapon after database read</br>forwards all parameter to the superclass
	 * @author Staufenberg, Thomas, 5820359 
	 * */
	public TwoHandedWeapon(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, int paramArmorPartsRevenue)
	{
		//TODO set final values
		super(paramID, paramGoldValue, "Zweihandschwert", "zhSchwert.png", paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, paramArmorPartsRevenue);
	}
	
	/**
	 * secondary constructor used to create a new default TwoHandedWeapon</br>
	 * sets default values and calls primary contructor
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public TwoHandedWeapon(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue)
	{
		this(paramID, paramGoldValue, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, -1);
	}
}