package shared.item;

public class Helmet extends Armament
{

	public Helmet(int paramID, int paramGoldValue, int paramLvlRestr, int paramAtkValue, int paramDefValue, int paramHpValue)
	{
		//TODO set final values
		super(paramID, paramGoldValue, "Kopfschutz", "helm.png", paramLvlRestr, paramAtkValue, paramDefValue, paramHpValue, 2);
	}

}
