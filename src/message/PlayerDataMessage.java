package message;

import shared.character.PlayerCharacter;

/**
 * abstract superclass of all Message objects that contain username and PlayerCharacter</br>extends Message
 * @author Staufenberg, Thomas, 5820359
 * */
public abstract class PlayerDataMessage extends Message
{

	private String username;
	private PlayerCharacter playerCharacter;
	
	/**
	 * stores username and PlayerCharacter in global variables, forwards receiverID to superclass
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public PlayerDataMessage(int paramReceiverID, String paramUsername, PlayerCharacter paramPlayer)
	{
		super(paramReceiverID);
		this.username = paramUsername;
		this.playerCharacter = paramPlayer;
	}
	
	/**
	 * @return the current username
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public String getUsername()
	{
		return this.username;
	}

	/**
	 * @return the current playerCharacter
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public PlayerCharacter getPlayerCharacter()
	{
		return this.playerCharacter;
	}
}
