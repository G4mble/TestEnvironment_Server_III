package shared.item;

import shared.character.PlayerCharacter;

public class ManaPotion extends ConsumableModel
{

	public ManaPotion(int paramStackSize)
	{
		super(1234, 1234, "Manatrank", "manaTrank.png", 1234, paramStackSize);
	}

	@Override
	public boolean consume(PlayerCharacter paramPlayer)
	{
		// TODO implement method
		return false;
	}
}
