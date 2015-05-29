package message;

import shared.character.PlayerCharacter;

/**
 * Message used to transfer playerData to the database after the user has selected a character
 * @author Staufenberg, Thomas, 5820359
 * */
public class LinkCharacterToUserMessage extends PlayerDataMessage
{
	/**
	 * forwards username, paramPlayer and to superclass
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public LinkCharacterToUserMessage(String paramUsername, PlayerCharacter paramPlayer)
	{
		super(paramUsername, paramPlayer);
	}
}
