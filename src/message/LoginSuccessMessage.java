package message;

import shared.character.PlayerCharacter;

public class LoginSuccessMessage extends Message
{
	private PlayerCharacter playerCharacter;
	
	public LoginSuccessMessage(PlayerCharacter paramPlayer)
	{
		super(100);
		this.playerCharacter = paramPlayer;
	}
	
	public PlayerCharacter getPlayerCharacter()
	{
		return this.playerCharacter;
	}
}
