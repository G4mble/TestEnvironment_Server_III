package message;

/**
 * abstract superclass of all Message objects that contain username and password</br>extends Message
 * @author Staufenberg, Thomas, 5820359
 * */
public abstract class UserDataMessage extends Message
{
	private String username, password;
	
	/**
	 * stores username and password in global variables, forwards receiverID to superclass
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public UserDataMessage(int paramReceiverID, String paramUsername, String paramPassword)
	{
		super(paramReceiverID);
		this.username = paramUsername;
		this.password = paramPassword;
	}
	
	/**
	 * @return current username
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public String getUsername()
	{
		return this.username;
	}

	/**
	 * @return current password
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public String getPassword()
	{
		return this.password;
	}
}
