package message;

public class OperationRejectedMessage extends Message
{
	private String errorMessage;
	
	public OperationRejectedMessage(String paramErrorMessage)
	{
		super(100);
		this.errorMessage = paramErrorMessage;
	}

	public String getErrorMessage()
	{
		return this.errorMessage;
	}
}
