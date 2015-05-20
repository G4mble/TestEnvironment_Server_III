package gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import message.Message;
import message.SavePlayerDataMessage;
import platform.ProgramController;
import shared.character.PlayerCharacter;
import shared.item.Boots;
import shared.item.ChestArmor;
import shared.item.ConsumableModel;
import shared.item.EquipmentModel;
import shared.item.Helmet;
import shared.item.ItemModel;
import shared.item.OneHandedWeapon;
import shared.item.Shield;
import shared.item.TwoHandedWeapon;
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
	
	private void generateNewEquipmentByID(int paramSelectionID)
	{
		Random randomGenerator = new Random();
		int currentPlayerLevel = this.activePlayer.getLevel();
		int range = currentPlayerLevel * (2 / 7);
		int currentID = -1;
		int currentLevelRestriction = currentPlayerLevel + randomGenerator.nextInt(2);
		int currentAtkValue = 0;
		int currentDefValue = 0;
		int currentHpValue = 0;
		if(paramSelectionID < 2)
		{
			currentAtkValue = (((currentLevelRestriction * 4) / 9) + 4 + randomGenerator.nextInt(range));
			currentDefValue = ((currentLevelRestriction - 1) * (4 / 9) + 2 + randomGenerator.nextInt(range));
			currentHpValue = (((currentLevelRestriction - 4) * (4 / 10)) + 2 + randomGenerator.nextInt(range));
			if(randomGenerator.nextInt(100) >= 80)
				currentDefValue *= randomGenerator.nextInt(2);
			if(randomGenerator.nextInt(100) >= 80)
				currentHpValue *= randomGenerator.nextInt(2);
			if(randomGenerator.nextInt(100) >= 90)
				currentAtkValue -= randomGenerator.nextInt((range/2));
		}
		else
		{
			currentAtkValue = ((currentLevelRestriction - 1) * (4 / 9) + 2 + randomGenerator.nextInt(range));
			currentDefValue = (((currentLevelRestriction * 4) / 9) + 4 + randomGenerator.nextInt(range));
			currentHpValue = (((currentLevelRestriction - 3) * (4 / 10)) + 1 + randomGenerator.nextInt(range));
			if(randomGenerator.nextInt(100) >= 90)
				currentDefValue -= randomGenerator.nextInt(range/2);
			if(randomGenerator.nextInt(100) >= 90)
				currentHpValue *= randomGenerator.nextInt(2);
			if(randomGenerator.nextInt(100) >= 80)
				currentAtkValue *= randomGenerator.nextInt((range/2));
		}
		
		int currentGoldValue = ((currentAtkValue + currentDefValue + currentHpValue) * (currentLevelRestriction / 10));
		
		switch(paramSelectionID)
		{
			case 0:
				this.globalInventoryList.add(new OneHandedWeapon(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue));
				break;
			case 1:
				this.globalInventoryList.add(new TwoHandedWeapon(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue));
				break;
			case 2:
				this.globalInventoryList.add(new Shield(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue));
				break;
			case 3:
				this.globalInventoryList.add(new Helmet(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue));
				break;
			case 4:
				this.globalInventoryList.add(new ChestArmor(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue));
				break;
			case 5:
				this.globalInventoryList.add(new Boots(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue));
				break;
		}
		
		//TODO update GUI
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
		else if(actionCommand.startsWith("generate"))
		{
			switch(actionCommand.substring(9))
			{
				case "equip":
					this.generateNewEquipmentByID(this.showcaseView.getSelectedEquipmentToGenerate());
					break;
				case "consumable":
					break;
				case "gold":
					break;
			}
		}
		else if(actionCommand.equals("logout"))
		{
			this.sendMessage(new SavePlayerDataMessage(this.activeUsername, this.activePlayer));
			this.showcaseView.dispose();
			this.programController.returnToMenu();
		}
		else if(actionCommand.equals("exit"))
			System.exit(0);
		
		this.sendMessage(new SavePlayerDataMessage(this.activeUsername, this.activePlayer));
		this.showcaseView.updateShowcaseView();
	}
	
	public void sendMessage(Message paramMessage)
	{
		this.programController.receiveMessage(paramMessage);
	}
}
