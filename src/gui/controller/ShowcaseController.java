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
		//TODO update all values as (double)
		
		Random randomGenerator = new Random();
		int currentPlayerLevel = this.activePlayer.getLevel();
		int range = (int) (((double) 5 / (double) 7) + ((double) currentPlayerLevel * ((double) 2 / (double) 7)));
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
			if(randomGenerator.nextInt(100) >= 0)
				currentAtkValue -= randomGenerator.nextInt(((int) Math.round((double)range/(double)2)));
		}
		else
		{
			currentAtkValue = ((currentLevelRestriction - 1) * (4 / 9) + 2 + randomGenerator.nextInt(range));
			currentDefValue = (((currentLevelRestriction * 4) / 9) + 4 + randomGenerator.nextInt(range));
			currentHpValue = (((currentLevelRestriction - 3) * (4 / 10)) + 1 + randomGenerator.nextInt(range));
			if(randomGenerator.nextInt(100) >= 90)
				currentDefValue -= randomGenerator.nextInt(((int) Math.round((double)range/(double)2)));
			if(randomGenerator.nextInt(100) >= 90)
				currentHpValue *= randomGenerator.nextInt(2);
			if(randomGenerator.nextInt(100) >= 80)
				currentAtkValue *= randomGenerator.nextInt((2));
		}
		
		int currentGoldValue = ((currentAtkValue + currentDefValue + currentHpValue) * (currentLevelRestriction / 10));
		
		ItemModel currentItem = null;
		
		switch(paramSelectionID)
		{
			case 0:
				currentItem = new OneHandedWeapon(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue);
				break;
			case 1:
				currentItem = new TwoHandedWeapon(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue);
				break;
			case 2:
				currentItem = new Shield(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue);
				break;
			case 3:
				currentItem = new Helmet(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue);
				break;
			case 4:
				currentItem = new ChestArmor(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue);
				break;
			case 5:
				currentItem = new Boots(currentID, currentGoldValue, currentLevelRestriction, currentAtkValue, currentDefValue, currentHpValue);
				break;
		}
		
		this.globalInventoryList.add(currentItem);
		this.showcaseView.addItemToGlobalInventory(currentItem.getItemName());
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
						this.showcaseView.addItemToGlobalInventory(currentItem.getItemName());
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
						this.showcaseView.addItemToGlobalInventory(currentItem.getItemName());
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
			//TODO update GUI
		}
		else if(actionCommand.equals("pickup"))
		{
			int itemIndex = this.showcaseView.getSelectedIDFromGlobalInventory(); 
			if(itemIndex != -1)
			{
				ItemModel currentItem = this.globalInventoryList.get(itemIndex);
				if(currentItem.pickup(this.activePlayer))
				{
					this.globalInventoryList.remove(itemIndex);
					this.showcaseView.removeItemFromGlobalInventory(itemIndex);
				}
				else
					this.showcaseView.displayErrorMessage(4);
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
