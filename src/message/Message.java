package message;

public abstract class Message
{
	private int receiverID;				//ProgramController: 100 ; DataBaseController: 200 ; 
	
	public Message(int paramReceiverID)
	{
		this.receiverID = paramReceiverID;
	}
	
	public int getReceiverID()
	{
		return this.receiverID;
	}
}
