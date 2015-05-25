package shared.item;

public class OneHandedWeapon extends Weapon
{
	public OneHandedWeapon(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, int paramArmorPartsRevenue)
	{
		//TODO set final values
		super(paramID, paramGoldValue, "Einhandschwert", "ehSchwert.png", paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, paramArmorPartsRevenue);
	}
	
	public OneHandedWeapon(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue)
	{
		//TODO set final values
		this(paramID, paramGoldValue, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, -1);
	}
}
