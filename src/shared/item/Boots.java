package shared.item;

public class Boots extends Armament
{

	public Boots(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue,	int paramDefValue, int paramHpValue, int paramArmorPartsRevenue)
	{
		//TODO set final values
		super(paramID, paramGoldValue, "Stiefel", "stiefel.png", paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, 4, paramArmorPartsRevenue);
	}
	
	public Boots(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue,	int paramDefValue, int paramHpValue)
	{
		//TODO set final values
		this(paramID, paramGoldValue, paramLvlRestr, paramAtkValue,	paramDefValue, paramHpValue, -1);
	}

}
