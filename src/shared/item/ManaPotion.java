package shared.item;

import javax.swing.JOptionPane;

import shared.character.Mage;
import shared.character.PlayerCharacter;

public class ManaPotion extends ConsumableModel
{

	public ManaPotion(int paramStackSize)
	{
		super(3, 5, "Manatrank", "manaTrank.png", 30, paramStackSize);
	}

	@Override
	public void consume(PlayerCharacter paramPlayer)
	{
		if(paramPlayer instanceof Mage)
		{
			Mage currentPlayer = (Mage) paramPlayer;
			if(currentPlayer.setMana((int)((double)(currentPlayer.getMana()) * (double)((double)super.valueModificator / (double)100))))
				if((--super.stackSize) == 0)
					currentPlayer.getInventory().removeItemFromInventory(this);
		}
		else
			JOptionPane.showMessageDialog(null, "Ihr Charakter kann diesen Gegenstand nicht benutzen!", "Achtung!", JOptionPane.WARNING_MESSAGE);
	}
}
