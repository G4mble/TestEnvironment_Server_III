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
	private HighscoreController highscoreController;
	private ArrayList<Message> incomingMessageList;
	private boolean messageIsProcessing;
	private String activeUsername;
	private int activeCharID;
	private Object[][] highscoreData;
	private PlayerCharacter activeCharacter;
	private boolean isHighscoreUpdateRunning;
	
	/**
	 * initializes global variables, creates a menu for the first time
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public ProgramController()
	{
		this.messageIsProcessing = false;
		this.highscoreData = null;
		this.isHighscoreUpdateRunning = false;
		this.incomingMessageList = new ArrayList<>();
		this.dbController = DataBaseController.getInstance(this);
		this.userInputController = null;
		this.highscoreController = null;
		new MenuController(this);
	}
	
	/**
	 * processes outgoing Messages and calls the receiveMessage method of the respective target adress
	 * @param paramMessage the Message to be send
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void sendMessage(Message paramMessage)
	{
		System.out.println("ProgramController: Sende " + paramMessage.getClass().getName().substring(8));
		this.dbController.receiveMessage(paramMessage);
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
	 * @param paramUsername username entered in the login panel
	 * @param paramPassword password entered in the login panel
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
	 * @param paramUsername username entered in the register panel
	 * @param paramPassword password entered in the register panel
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void initiateRegistrationProcess(String paramUsername, String paramPassword, int paramCharID)
	{
		if((paramPassword.length() < 4) || (paramUsername.length() < 4) || (paramUsername.length() > 30) || (paramPassword.length() > 30))
			this.rejectOperation("Benutzername und Passwort muessen jeweils zwischen 4 und 30 Zeichen lang sein!");
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
	private void createNewCharacter()
	{
		if(this.activeCharID == 1)
			this.activeCharacter = new Warrior(1);				//set default clientID = 1
		else
			this.activeCharacter = new Mage(1);					//set default clientID = 1
		this.sendMessage(new LinkCharacterToUserMessage(this.activeUsername, this.activeCharacter));
	}
	
	/**
	 * is called after login has been verified or user has been successfully created</br>
	 * releases unused views and controller, creates new ShowcaseController
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void finalizeAuthenticationProcess()
	{
		this.userInputController.getUserInputView().dispose();
		this.userInputController = null;
		this.activeCharacter.getInventory().initiatePlayerAttributes();
		new ShowcaseController(this, this.activeUsername, this.activeCharacter);
	}
	
	/**
	 * disposes the highscore view and releases the current highscoreController
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void closeHighscore()
	{
		if(this.highscoreController != null)
			this.highscoreController.getHighscoreView().dispose();
		this.highscoreController = null;
	}
	
	/**
	 * releases all unused controllers, creates new MenuController
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void returnToMenu()
	{
		this.userInputController = null;
		this.closeHighscore();
		new MenuController(this);
	}
	
	/**
	 * retrieves highscore data from the database via Message</br>creates new HighscoreController
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void initiateHighscore()
	{
		if(this.highscoreController == null)
		{
			this.sendMessage(new LoadHighscoreMessage("username"));
			if(this.highscoreData != null)
				this.highscoreController = new HighscoreController(this.highscoreData, this);
			else
				JOptionPane.showMessageDialog(null, "Highscore konnte nicht geladen werden!", "Fehler!", JOptionPane.ERROR_MESSAGE);
		}
		else
			this.highscoreController.getHighscoreView().requestFocus();
	}
	
	/**
	 * invokes highscore data update</br>notifies database to read highscore data
	 * @param paramFilterAttribute the attribute by which the highscore data is to be sorted
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void updateHighscore(String paramFilterAttribute)
	{
		this.isHighscoreUpdateRunning = true;
		this.sendMessage(new LoadHighscoreMessage(paramFilterAttribute));
	}
	
	/**
	 * adds the given Message to a list and starts the handle process if not already running
	 * @param paramMessage the Message to receive
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void receiveMessage(Message paramMessage)
	{
		System.out.println("ProgramController: Empfange " + paramMessage.getClass().getName().substring(8));
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
		System.out.println("ProgramController: Starte Message handling.");
		if(!this.messageIsProcessing)
			this.messageIsProcessing = true;
		while(true)
		{
			Message currentMessage;			
			try
			{
				currentMessage = this.incomingMessageList.remove(0);
				System.out.println("ProgramController: Behandle " + currentMessage.getClass().getName().substring(8));
			}
			catch(IndexOutOfBoundsException indexExc)
			{
				System.out.println("ProgramController: Keine weiteren Messages vorhanden.");
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
			else if(currentMessage instanceof HighscoreDataMessage)
			{
				this.highscoreData = ((HighscoreDataMessage) currentMessage).getHighscoreData();
				if(this.isHighscoreUpdateRunning)
				{
					this.isHighscoreUpdateRunning = false;
					this.highscoreController.updateHighscore(this.highscoreData);
				}
			}
		}
		System.out.println("ProgramController: Stoppe Message handling.");
		System.out.println("-----------------------------------------------------------");
		this.messageIsProcessing = false;
	}
}
