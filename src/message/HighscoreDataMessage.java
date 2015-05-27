package message;

public class HighscoreDataMessage extends Message
{
	private Object[][] highscoreData;
	
	public HighscoreDataMessage(Object[][] paramHighscoreData)
	{
		super(0);
		this.highscoreData = paramHighscoreData;
	}

	public Object[][] getHighscoreData()
	{
		return this.highscoreData;
	}
}
