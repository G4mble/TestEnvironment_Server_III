package message;

/**
 * Message used to transfer login data for verification purpose
 * @author Staufenberg, Thomas, 5820359
 * */
public class VerifyLoginMessage extends UserDataMessage
{
	/**
	 * forwards username and password to superclass
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public VerifyLoginMessage(String paramUsername, String paramPassword)
	{
		super(paramUsername, paramPassword);
	}
}
