package shared.item;

/**
 * implementation of a goldStack</br>extends ItemModel
 * @author Staufenberg, Thomas, 5820359
 * */
public class GoldStack extends ItemModel
{
	/**
	 * sets standard values and forwards all parameter to superconstructor
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public GoldStack(int paramGoldValue)
	{
		//TODO set final values
		super(1234, paramGoldValue, "Gold", "gold.png");
	}
}