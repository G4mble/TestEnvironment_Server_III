package message;

/**
 * Message used to transfer user data for registration purpose
 * @author Staufenberg, Thomas, 5820359
 * */
public class RegisterUserMessage extends UserDataMessage
{
	/**
	 * forwards username and password to superclass
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public RegisterUserMessage(String paramUsername, String paramPassword)
	{
		super(paramUsername, paramPassword);
	}
}
