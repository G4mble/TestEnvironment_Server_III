package message;

/**
 * Message used to indicate that an operation has been rejected
 * @author Staufenberg, Thomas, 5820359
 * */
public class OperationRejectedMessage extends Message
{
	private String errorMessage;
	
	/**
	 * creates a new Message, sets the receiverID and stores an errorMessage in a global variable
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public OperationRejectedMessage(String paramErrorMessage)
	{
		super(100);
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
