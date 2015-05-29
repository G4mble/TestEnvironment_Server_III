package message;

/**
 * Message used to transfer highscoreData from the database to the highscoreView
 * @author Staufenberg, Thomas, 5820359
 * */
public class HighscoreDataMessage extends Message
{
	private Object[][] highscoreData;

	/**
	 * creates a new Message and sets the highscoreData
	 * @param paramHighscoreData the highscore data to be transferred
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public HighscoreDataMessage(Object[][] paramHighscoreData)
	{
		this.highscoreData = paramHighscoreData;
	}

	/**
	 * @return the currently stored highscore data
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public Object[][] getHighscoreData()
	{
		return this.highscoreData;
	}
}
