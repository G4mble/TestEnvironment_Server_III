package message;

/**
 * Message used to indicate that an operation has been rejected
 * @author Staufenberg, Thomas, 5820359
 * */
public class OperationRejectedMessage extends Message
{
	private String errorMessage;
	
	/**
	 * creates a new Message and stores an errorMessage in a global variable
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public OperationRejectedMessage(String paramErrorMessage)
	{
		this.errorMessage = paramErrorMessage;
	}

	/**
	 * @return the current errorMessage
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public String getErrorMessage()
	{
		return this.errorMessage;
	}
}
