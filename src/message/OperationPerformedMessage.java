package message;

/**
 * Message used to indicate that an operation has been successfully performed
 * @author Staufenberg, Thomas, 5820359
 * */
public class OperationPerformedMessage extends Message
{	
	/**
	 * creates a new Message and sets the receiverID
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public OperationPerformedMessage()
	{
		super(100);
	}
}
