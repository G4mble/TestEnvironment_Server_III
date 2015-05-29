package shared.item;

/**
 * abstract superclass of all armor equipment pieces</br>extends EquipmentModel
 * @author Staufenberg, Thomas, 5820359
 * */
public abstract class Armament extends EquipmentModel
{
	//slotID: helmet = 2; chest = 3; boots = 4;
	
	/**
	 * forwards paramID, paramGoldValue, paramName, paramImagePath, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, paramEqSlotID, paramArmorPartsRevenue to super constructor</br>
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public Armament(int paramID, int paramGoldValue, String paramName, String paramImagePath, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, int paramEqSlotID, int paramArmorPartsRevenue)
	{
		super(paramID, paramGoldValue, paramName, paramImagePath, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, paramEqSlotID, paramArmorPartsRevenue);
	}
}
