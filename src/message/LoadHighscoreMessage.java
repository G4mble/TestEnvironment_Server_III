package message;

/**
 * Message used to request highscore data from the database
 * @author Staufenberg, Thomas, 5820359
 * */
public class LoadHighscoreMessage extends Message
{
	private String filterAttribute;

	/**
	 * creates a new Message and sets the filterAttribute
	 * @param paramFilterAttribute the String attribute by which the highscore data is to be sorted
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public LoadHighscoreMessage(String paramFilterAttribute)
	{
		this.filterAttribute = paramFilterAttribute;
	}

	/**
	 * @return the current filterAttribute
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public String getFilterAttribute()
	{
		return this.filterAttribute;
	}
}
