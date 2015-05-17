package message;

/**
 * Message used to transfer user data for registration purpose
 * @author Staufenberg, Thomas, 5820359
 * */
public class RegisterUserMessage extends UserDataMessage
{
	/**
	 * forwards username, password and receiverID to superclass
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public RegisterUserMessage(String paramUsername, String paramPassword)
	{
		super(200, paramUsername, paramPassword);
	}
}
