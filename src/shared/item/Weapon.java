package shared.item;

/**
 * abstract superclass of all weapon equipment pieces</br>extends EquipmentModel
 * @author Staufenberg, Thomas, 5820359
 * */
public abstract class Weapon extends EquipmentModel
{	
	/**
	 * forwards paramID, paramGoldValue, paramName, paramImagePath, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, paramArmorPartsRevenue to super constructor</br>
	 * paramSlotID is set to 0
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public Weapon(int paramID, int paramGoldValue, String paramName, String paramImagePath, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, int paramArmorPartsRevenue)
	{
		super(paramID, paramGoldValue, paramName, paramImagePath, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, 0, paramArmorPartsRevenue);
	}
}
