package platform;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import shared.character.Mage;
import shared.character.PlayerCharacter;
import shared.character.Warrior;
import message.*;
import dataBase.DataBaseController;
import gui.controller.*;

/**
 * gateway class; is in control of all other controller classes</br>redirect calls between different controllers
 * @author Staufenberg, Thomas, 5820359
 * */
public class ProgramController
{
	private UserInputController userInputController;
	private DataBaseController dbController;
	private ArrayList<Message> incomingMessageList;
	private boolean messageIsProcessing = false;
	private String activeUsername;
	private int activeCharID;
	private PlayerCharacter activeCharacter;
	
	/**
	 * initializes global variables, creates a menu for the first time
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public ProgramController()
	{
		this.incomingMessageList = new ArrayList<>();
		this.dbController = DataBaseController.getInstance(this);
		new MenuController(this);
	}
	
	/**
	 * releases unused menuController</br>
	 * creates new UserInputController as login- or register view, depending on the action specified
	 * @param paramAction {"Einloggen", "Registrieren"} action requested by user via menu
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void initiateAuthentication(String paramAction)
	{
		this.userInputController = new UserInputController(this, paramAction);
	}
	
	/**
	 * is called after user entered a set of username/password for login purpose</br>
	 * forwards given data to dataBase for verification via Message
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void initiateLoginProcess(String paramUsername, String paramPassword)
	{
		this.activeUsername = paramUsername;
		this.sendMessage(new VerifyLoginMessage(paramUsername, paramPassword));
	}
	
	/**
	 * is called after user entered a set of username/password for registration purpose</br>
	 * forwards given data to dataBase for verification via Message
	 * @param paramCharID ID of the selected character
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void initiateRegistrationProcess(String paramUsername, String paramPassword, int paramCharID)
	{
		if((paramPassword.length() < 4) || (paramUsername.length() < 4))
			this.rejectOperation("Benutzername und Passwort muessen jeweils mindestens 4 Zeichen lang sein!");
		else
		{
			this.activeUsername = paramUsername;
			this.activeCharID = paramCharID;
			this.sendMessage(new RegisterUserMessage(paramUsername, paramPassword));
		}
	}
	
	/**
	 * is called after RegisterSuccessMessage has been received</br>
	 * creates a new character depending on the users selection</br>invokes link operation of user and character
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void createNewCharacter()
	{
		if(this.activeCharID == 1)
			this.activeCharacter = new Warrior(1);				//set default clientID = 1
		else
			this.activeCharacter = new Mage(1);					//set default clientID = 1
		this.sendMessage(new LinkCharacterToUserMessage(this.activeUsername, this.activeCharacter));
	}
	
	/**
	 * is called after login has been verified or user has been successfully created</br>
	 * invokes save operation for userdata if specified</br>
	 * releases unused views and controller, creates new ShowcaseController
	 * @param paramSavePlayerData true: invokes save operation for userData; false: additional save operation will not be executed
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void finalizeAuthenticationProcess()
	{
		this.userInputController.getUserInputView().dispose();
		this.userInputController = null;
		new ShowcaseController(this, this.activeUsername, this.activeCharacter);
	}
	
	/**
	 * is called after RejectOperationMessage has been received</br>
	 * notifies the user that some operation requested by him was rejected, displays corresponding message
	 * @param paramMessage text to be displayed in error message
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void rejectOperation(String paramMessage)
	{
		this.activeUsername = "";
		JOptionPane.showMessageDialog(null, paramMessage, "Achtung!", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * releases all unused controllers, creates new MenuController
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void returnToMenu()
	{
		this.userInputController = null;
		new MenuController(this);
	}
	
	/**
	 * adds the given Message to a list and starts the handle process if not already running
	 * @author Staufenberg, Thomas, 5820359
	 * @param paramMessage the Message to receive
	 * */
	public void receiveMessage(Message paramMessage)
	{
		this.incomingMessageList.add(paramMessage);
		if(!this.messageIsProcessing)
			this.handleMessage();
	}
	
	/**
	 * called by this.receiveMessage, iterates over incomingMessageList while not empty
	 * @author Staufenberg, Thomas, 5820359
	 * */
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
				this.finalizeAuthenticationProcess();
			else if(currentMessage instanceof LoginSuccessMessage)
			{
				this.activeCharacter = ((LoginSuccessMessage) currentMessage).getPlayerCharacter();
				this.finalizeAuthenticationProcess();
			}
			else if(currentMessage instanceof RegisterSuccessMessage)
				this.createNewCharacter();
			else if(currentMessage instanceof SavePlayerDataMessage)
				this.sendMessage(currentMessage);
		}
		this.messageIsProcessing = false;
	}
	
	/**
	 * processes outgoing Messages and calls the receiveMessage method of the respective target adress
	 * @param paramMessage the Message to be send
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void sendMessage(Message paramMessage)
	{
		this.dbController.receiveMessage(paramMessage);
	}
}
