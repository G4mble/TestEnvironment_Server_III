package message;

public class RegisterUserMessage extends Message
{
	private String username, password;
	
	public RegisterUserMessage(String paramUsername, String paramPassword)
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
