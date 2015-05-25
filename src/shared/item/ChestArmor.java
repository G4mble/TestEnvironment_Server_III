package shared.item;

public class ChestArmor extends Armament
{

	public ChestArmor(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue, int paramArmorPartsRevenue)
	{
		//TODO set final values
		super(paramID, paramGoldValue, "Brustschutz", "brustschutz.png", paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, 3, paramArmorPartsRevenue);
	}
	
	public ChestArmor(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue)
	{
		//TODO set final values
		this(paramID, paramGoldValue, paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, -1);
	}
}
