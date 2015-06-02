import java.util.Random;


public class CalculationTest
{
	public static void main(String[] args)
	{
		for(int currentLevelRestriction = 1; currentLevelRestriction <= 100; currentLevelRestriction++)
		{
//			int range = (int) (((double) 5 / (double) 7) + ((double) currentPlayerLevel * ((double) 2 / (double) 7)));
//			Random randomGenerator = new Random();
			
			int currentAtkValue = (int)((double)(currentLevelRestriction - 1) * (double)((double)4 / (double)9) + 2);
			int currentDefValue = (int)(((double)(currentLevelRestriction * 4) / (double)9) + 4 );
			int currentHpValue = (int)(((double)(currentLevelRestriction - 3) * (double)((double)4 / (double)10)) + 1 );
			
			int currentGoldValue = (int)((((double)currentLevelRestriction / (double)18) * (double)700) * ((double)currentLevelRestriction / (double)48) + 1 );
			int tmpGold = (int)(((currentLevelRestriction / (double)18) * 700) * (currentLevelRestriction / (double)48) + 1 );
			System.out.println(currentGoldValue);
			System.out.println(tmpGold);
			if(currentGoldValue != tmpGold)
				System.err.println("Error!");
			System.out.println("-----------------------------------------");
		}
	}
}