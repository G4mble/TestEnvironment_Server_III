package message;

public class VerifyLoginMessage extends Message
{
	private String username, password;
	
	public VerifyLoginMessage(String paramUsername, String paramPassword)
	{
		super(200);
		this.username = paramUsername;
		this.password = paramPassword;
	}
	
	public String getUsername()
	{
		return this.username;
	}
	
	public String getPassword()
	{
		return this.password;
	}

}
