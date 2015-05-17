package message;

/**
 * Message used to transfer login data for verification purpose
 * @author Staufenberg, Thomas, 5820359
 * */
public class VerifyLoginMessage extends UserDataMessage
{
	/**
	 * forwards username, password and receiverID to superclass
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public VerifyLoginMessage(String paramUsername, String paramPassword)
	{
		super(200, paramUsername, paramPassword);
	}
}
