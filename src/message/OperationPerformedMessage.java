package message;

public class OperationPerformedMessage extends Message
{
	private int operationID;				//linkCharToUser: 1 ; ... 
	
	public OperationPerformedMessage(int paramOperationID)
	{
		super(100);
		this.operationID = paramOperationID;
	}
	
	public int getOperationID()
	{
		return this.operationID;
	}

}
