package gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import message.Message;
import message.SavePlayerDataMessage;
import platform.ProgramController;
import shared.InventoryModel;
import shared.character.PlayerCharacter;
import shared.item.ConsumableModel;
import shared.item.EquipmentModel;
import shared.item.ItemModel;
import shared.statistics.StatisticsModel;
import gui.view.ShowcaseView;

/**
 * controller of ShowcaseView, processes ActionEvents triggered there
 * @author Staufenberg, Thomas, 5820359
 * */
public class ShowcaseController implements ActionListener
{
	private ShowcaseView showcaseView;
	private ProgramController programController;
	private PlayerCharacter activePlayer;
	private String activeUsername;
	private ArrayList<ItemModel> globalInventoryList;
	
	/**
	 * instantiates ShowcaseView
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public ShowcaseController(ProgramController paramProgramController, String paramUsername, PlayerCharacter paramPlayer)
	{
		this.activePlayer = paramPlayer;
		this.activeUsername = paramUsername;
		this.globalInventoryList = new ArrayList<>();
		this.programController = paramProgramController;
		this.showcaseView = new ShowcaseView(this, paramUsername, paramPlayer);
	}

	/**
	 * processes ActionEvents triggered in this.showcaseView
	 * @author Staufenberg, Thomas, 5820359
	 * */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String actionCommand = e.getActionCommand();
		if(actionCommand.startsWith("inventory"))
		{
			ItemModel currentItem = this.showcaseView.getSelectedItemFromInventory();
			if(currentItem != null)
				switch(actionCommand.substring(10))
				{
					case "dropItem":
						currentItem.drop(this.activePlayer, this.globalInventoryList);					
						break;
					case "equipItem":
						if(currentItem instanceof EquipmentModel)
							((EquipmentModel) currentItem).equip(this.activePlayer);
						else
							this.showcaseView.displayErrorMessage(1);
						break;
					case "consumeItem":
						if(currentItem instanceof ConsumableModel)
							((ConsumableModel) currentItem).consume(this.activePlayer);
						else
							this.showcaseView.displayErrorMessage(2);
						break;
					case "sellItem":
						currentItem.sell(this.activePlayer);
						break;
					case "salvageItem":
						if(currentItem instanceof EquipmentModel)
							((EquipmentModel) currentItem).salvage(this.activePlayer);
						else
							this.showcaseView.displayErrorMessage(3);
						break;
				}
		}
		else if(actionCommand.startsWith("equipment"))
		{
			EquipmentModel currentItem = this.showcaseView.getSelectedItemFromEquipment();
			if(currentItem != null)
				switch(actionCommand.substring(10))
				{
					case "dropItem":
						currentItem.drop(this.activePlayer, this.globalInventoryList);
						break;
					case "sellItem":
						currentItem.sell(this.activePlayer);
						break;
					case "removeItem":
						if(!(currentItem.removeFromEquip(this.activePlayer)))
							this.showcaseView.displayErrorMessage(4);
						break;
					case "salvageItem":
						currentItem.salvage(this.activePlayer);
						break;
				}
		}
		else
			System.exit(0);
		
		this.sendMessage(new SavePlayerDataMessage(this.activeUsername, this.activePlayer));
	}
	
	public void sendMessage(Message paramMessage)
	{
		this.programController.receiveMessage(paramMessage);
	}
}
