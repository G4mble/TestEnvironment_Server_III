package message;

/**
 * abstract superclass of all Message objects, used for communication between controller classes
 * @author Staufenberg, Thomas, 5820359
 * */
public abstract class Message
{
	private int receiverID;				//ProgramController: 100 ; DataBaseController: 200 ; 

	/**
	 * creates new Message and sets the receiverID
	 * @param paramReceiverID ID of the controller specified as target adress
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public Message(int paramReceiverID)
	{
		this.receiverID = paramReceiverID;
	}

	/**
	 * @return the ID of the receiving controller
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getReceiverID()
	{
		return this.receiverID;
	}
}
