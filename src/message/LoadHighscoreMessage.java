package message;

public class LoadHighscoreMessage extends Message
{
	private String filterAttribute;
	public LoadHighscoreMessage(String paramFilterAttribute)
	{
		super(0);
		this.filterAttribute = paramFilterAttribute;
	}

	public String getFilterAttribute()
	{
		return this.filterAttribute;
	}
}
