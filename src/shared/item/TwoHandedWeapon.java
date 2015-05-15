package shared.item;

public class TwoHandedWeapon extends Weapon
{

	public TwoHandedWeapon(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue)
	{
		//TODO set final values
		super(paramID, paramGoldValue, "Zweihandschwert", "zhSchwert.png", paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, true);
	}

}
