package shared.item;

/**
 * implementation of Helmet(piece of armament)</br>extends Armament
 * @author Staufenberg, Thomas, 5820359
 * */
public class Helmet extends Armament
{
	/**
	 * primary constructor used for creating Helmet after database read</br>forwards all parameter to the superclass
	 * @author Staufenberg, Thomas, 5820359 
	 * */
	public Helmet(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, int paramArmorPartsRevenue)
	{
		//TODO set final values
		super(paramID, paramGoldValue, "Kopfschutz", "helm.png", paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, 2, paramArmorPartsRevenue);
	}
	
	/**
	 * secondary constructor used to create a new default Helmet</br>
	 * sets default values and calls primary contructor
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public Helmet(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue)
	{
		this(paramID, paramGoldValue, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, -1);
	}
}