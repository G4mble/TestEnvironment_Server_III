package message;

import shared.character.PlayerCharacter;

/**
 * Message used to indicate that the previously given login credentials were correct</br>
 * also contains the corresponding PlayerCharacter
 * @author Staufenberg, Thomas, 5820359
 * */
public class LoginSuccessMessage extends Message
{
	private PlayerCharacter playerCharacter;
	
	/**
	 * creates a new Message, sets the receiverID and stores the currentPlayer in a global variable
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public LoginSuccessMessage(PlayerCharacter paramPlayer)
	{
		super(100);
		this.playerCharacter = paramPlayer;
	}
	
	/**
	 * @return the current PlayerCharacter
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public PlayerCharacter getPlayerCharacter()
	{
		return this.playerCharacter;
	}
}
