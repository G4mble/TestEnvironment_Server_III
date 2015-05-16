package platform;

import java.util.ArrayList;

import shared.character.Mage;
import shared.character.PlayerCharacter;
import shared.character.Warrior;
import message.*;
import dataBase.DataBaseController;
import gui.controller.*;

public class ProgramController
{
	private MenuController menuController;
	private UserInputController userInputController;
	private DataBaseController dbController;
	private ShowcaseController showcaseController;
	private ArrayList<Message> incomingMessageList;
	private boolean messageIsProcessing = false;
	private String activeUsername;
	private int activeCharID;
	private PlayerCharacter activeCharacter;
	
	public ProgramController()
	{
		this.incomingMessageList = new ArrayList<>();
		this.dbController = DataBaseController.getInstance(this);
		this.menuController = new MenuController(this);
	}
	
	public void initiateAuthentication(String paramAction)
	{
		this.menuController = null;
		this.userInputController = new UserInputController(this, paramAction);
	}
	
	public void returnToMenu()
	{
		//TODO set all unused controller to null
		this.userInputController = null;
		this.menuController = new MenuController(this);
	}
	
	public void beginLoginProcess(String paramUsername, String paramPassword)
	{
		this.activeUsername = paramUsername;
		this.sendMessage(new VerifyLoginMessage(paramUsername, paramPassword));
	}
	
	private void rejectOperation(String paramMessage)
	{
		this.activeUsername = "";
		System.out.println(paramMessage);
	}
	
	private void finalizeAuthenticationProcess(boolean paramSavePlayerData)
	{
		if(paramSavePlayerData)
			this.sendMessage(new SavePlayerDataMessage(this.activeUsername, this.activeCharacter));
		this.userInputController.getUserInputView().dispose();
		this.userInputController = null;
		this.showcaseController = new ShowcaseController(this);
	}
	
	public void invokeUserRegistration(String paramUsername, String paramPassword, int paramCharID)
	{
		this.activeUsername = paramUsername;
		this.activeCharID = paramCharID;
		this.sendMessage(new RegisterUserMessage(paramUsername, paramPassword));
	}
	
	public void finalizeRegistrationProcess()
	{
		if(this.activeCharID == 1)
			this.activeCharacter = new Warrior(1);				//set default clientID = 1
		else
			this.activeCharacter = new Mage(1);					//set default clientID = 1
		this.sendMessage(new LinkCharacterToUserMessage(this.activeUsername, this.activeCharacter));
	}
	
	private void invokeFollowUpAction(int paramOperationID)
	{
		switch(paramOperationID)
		{
			case 1:				//successfull regestration and character creation
				this.finalizeAuthenticationProcess(true);
				break;
			case 2:				//successfully logged in; playerData read from DB
				this.finalizeAuthenticationProcess(false);
				break;
		}
	}
	
	private void sendMessage(Message paramMessage)
	{
		if(paramMessage.getReceiverID() == 200)
			this.dbController.receiveMessage(paramMessage);
	}
	
	public void receiveMessage(Message paramMessage)
	{
		this.incomingMessageList.add(paramMessage);
		if(!this.messageIsProcessing)
			this.handleMessage();
	}
	
	private void handleMessage()
	{
		if(!this.messageIsProcessing)
			this.messageIsProcessing = true;
		while(true)
		{
			Message currentMessage;			
			try
			{
				currentMessage = this.incomingMessageList.remove(0);
			}
			catch(IndexOutOfBoundsException indexExc)
			{
				break;
			}
			
			if(currentMessage instanceof OperationRejectedMessage)
				this.rejectOperation(((OperationRejectedMessage) currentMessage).getErrorMessage());
			else if(currentMessage instanceof OperationPerformedMessage)
				this.invokeFollowUpAction(((OperationPerformedMessage) currentMessage).getOperationID());
			else if(currentMessage instanceof LoginSuccessMessage)
			{
				this.activeCharacter = ((LoginSuccessMessage) currentMessage).getPlayerCharacter();
				this.invokeFollowUpAction(2);
			}
			else if(currentMessage instanceof RegisterSuccessMessage)
				this.finalizeRegistrationProcess();
		}
		this.messageIsProcessing = false;
	}
	
	public void exitProgram(int paramState)
	{
		this.dbController.closeConnection();
		System.exit(paramState);
	}
}
