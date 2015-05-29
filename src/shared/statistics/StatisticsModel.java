package shared.statistics;

/**
 * data storage for player statistics
 * @author Staufenberg, Thomas, 5820359
 * */
public class StatisticsModel
{
	private int monsterKillCount, deathCount, goldEarned, timePlayed;
	
	/**
	 * StatisticsModel constructor; stores parameters in global variables
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public StatisticsModel(int paramKills, int paramDeaths, int paramGold, int paramTimePlayed)
	{
		this.monsterKillCount = paramKills;
		this.deathCount = paramDeaths;
		this.goldEarned = paramGold;
		this.timePlayed = paramTimePlayed;
	}
	
	/**
	 * constructor for a new default statistics model</br>
	 * calls second constructor with all values set to default
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public StatisticsModel()
	{
		this(0, 0, 0, 0);
	}
	
	/**
	 * @return amount of monsters killed since charakter creation
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getMonsterKillCount()
	{
		return this.monsterKillCount;
	}

	/**
	 * @return how many times the player has been killed since charakter creation
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getDeathCount()
	{
		return this.deathCount;
	}
	
	/**
	 * @return amount of gold earned since charakter creation
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getGoldEarned()
	{
		return this.goldEarned;
	}
	
	/**
	 * @return time spent playing since charakter creation
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getTimePlayed()
	{
		return this.timePlayed;
	}
	
	/**
	 * adds paramValue to the current value
	 * @param paramValue amount of kills to be added
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void updateMonsterKillCount(int paramValue)
	{
		this.monsterKillCount += paramValue;
	}
	
	/**
	 * adds paramValue to the current value
	 * @param paramValue amount of deaths to be added
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void updateDeathCount(int paramValue)
	{
		this.deathCount += paramValue;
	}
	
	/**
	 * adds paramValue to the current value
	 * @param paramValue amount of gold to be added
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void updateGoldEarned(int paramValue)
	{
		this.goldEarned += paramValue;
	}
	
	/**
	 * adds paramValue to the current value
	 * @param paramValue amount of time to be added
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void updateTimePlayed(int paramValue)
	{
		this.timePlayed += paramValue;
	}
}
