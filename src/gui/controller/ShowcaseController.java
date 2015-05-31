package gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import message.Message;
import message.SavePlayerDataMessage;
import platform.ProgramController;
import shared.InventoryModel;
import shared.character.PlayerCharacter;
import shared.item.Boots;
import shared.item.ChestArmor;
import shared.item.ConsumableModel;
import shared.item.EquipmentModel;
import shared.item.GoldStack;
import shared.item.HealthPotion;
import shared.item.Helmet;
import shared.item.ItemModel;
import shared.item.ManaPotion;
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
	
	/**
	 * generates new equipment according to given selectionID</br>is used by 'random world drops' and the crafting system</br>
	 * adds the generated item either to the globalInventory(randomDrop) or the players inventory (crafting)
	 * @param paramSelectionID equipSlotID of the item to be generated
	 * @param paramIsCrafted wether the item is generated in a crafting process(true) or 'randomly dropped'
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void generateNewEquipment(int paramSelectionID, boolean paramIsCrafted)
	{
		Random randomGenerator = new Random();
		int currentPlayerLevel = this.activePlayer.getLevel();
		//calculate range used to generate some random values
		int range = (int) ((5 / (double) 7) + (currentPlayerLevel * (2 / (double) 7)));
		int currentID = -1;
		//calculate levelRestriction depending on playerLevel and if the item is not crafted add a random value (0;1)
		int currentLevelRestriction = currentPlayerLevel;
		if(!paramIsCrafted)
			currentLevelRestriction += randomGenerator.nextInt(2);
		int currentAtkValue = 0;
		int currentDefValue = 0;
		int currentHpValue = 0;
		
		//calculate values for weapons
		if(paramSelectionID < 2)
		{
			//calculate base values
			currentAtkValue = ((currentLevelRestriction * 4) / 9) + 4 + randomGenerator.nextInt(range);
			currentDefValue = (int)((currentLevelRestriction - 1) * (4 / (double)9) + 2 + randomGenerator.nextInt(range));
			currentHpValue = (int)(((currentLevelRestriction - 4) * (4 / (double)10)) + 2 + randomGenerator.nextInt(range));
			//add bonus/malus to base values depending on probability
			if(randomGenerator.nextInt(100) >= 80)
				currentDefValue *= randomGenerator.nextInt(2);
			if(randomGenerator.nextInt(100) >= 90)
				currentHpValue *= randomGenerator.nextInt(2);
			if(randomGenerator.nextInt(100) >= 90)
				currentAtkValue -= randomGenerator.nextInt(((int) Math.round(range/(double)2)));
			//add random bonus values for two hand weapons
			if(paramSelectionID == 1)
			{
				currentAtkValue *= (Math.round(((double)2 - (double)randomGenerator.nextDouble())));
				currentDefValue *= (Math.round(((double)2 - (double)randomGenerator.nextDouble())));
				currentHpValue *= (Math.round(((double)2 - (double)randomGenerator.nextDouble())));
			}
		}
		//calculate values for equipment that is not a weapon
		else
		{
			//calculate base values
			currentAtkValue = (int)((currentLevelRestriction - 1) * (4 / (double)9) + 2 + randomGenerator.nextInt(range));
			currentDefValue = ((currentLevelRestriction * 4) / 9) + 4 + randomGenerator.nextInt(range);
			currentHpValue = (int)(((currentLevelRestriction - 3) * (4 / (double)10)) + 1 + randomGenerator.nextInt(range));
			//add bonus/malus to base values depending on probability
			if(randomGenerator.nextInt(100) >= 90)
				currentDefValue -= randomGenerator.nextInt(((int) Math.round(range/(double)2)));
			if(randomGenerator.nextInt(100) >= 90)
				currentHpValue *= randomGenerator.nextInt(2);
			if(randomGenerator.nextInt(100) >= 80)
				currentAtkValue *= randomGenerator.nextInt((2));
		}
		
		//calculate gold value
		int currentGoldValue = (int)((currentAtkValue + currentDefValue + currentHpValue) * (currentLevelRestriction / (double)5) + 1);
		
		//modify item values even more if the item got crafted
		if(paramIsCrafted)
		{
			//level 1 P(0,61 <= X <= 0,64)
			if(randomGenerator.nextInt(100) >= 40)
				currentAtkValue += randomGenerator.nextInt(range);
			if(randomGenerator.nextInt(100) >= 38)
				currentDefValue += randomGenerator.nextInt(range);
			if(randomGenerator.nextInt(100) >= 36)
				currentHpValue += randomGenerator.nextInt(range);
			
			//level 2 P(0,09 <= X <= 0,18)
			if(randomGenerator.nextInt(100) >= 90)
				currentAtkValue += range;
			if(randomGenerator.nextInt(100) >= 87)
				currentDefValue += range;
			if(randomGenerator.nextInt(100) >= 83)
				currentHpValue += range;
			
			//level 3 P(0,02 <= X <= 0,04)
			if(randomGenerator.nextInt(100) >= 99)
				currentAtkValue *= ((double)2 - (double)randomGenerator.nextDouble());
			if(randomGenerator.nextInt(100) >= 98)
				currentDefValue *= ((double)2 - (double)randomGenerator.nextDouble());
			if(randomGenerator.nextInt(100) >= 97)
				currentHpValue *= ((double)2 - (double)randomGenerator.nextDouble());
			
			currentGoldValue = (int)((currentAtkValue + currentDefValue + currentHpValue) * (currentLevelRestriction / (double)2) + 1);
		}
		
		EquipmentModel currentItem = null;
		
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
		
		if(paramIsCrafted)
		{
			currentItem.setArmorPartsRevenue((Math.max(1, ((currentItem.getArmorPartsRevenue()) / 2))));
			currentItem.pickup(this.activePlayer);
		}
		else
		{
			this.globalInventoryList.add(currentItem);
			this.showcaseView.addItemToGlobalInventory(currentItem.getItemName());
		}
	}
	
	/**
	 * invokes crafting process</br>calculates crafting costs and perfoms a consistency check on these costs</br>
	 * calls this.generateNewEquipmentByID() if the process is good to go
	 * @param paramSelectionID equipSlotID of the item to be generated
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void craftEquipmentByID(int paramSelectionID)
	{
		InventoryModel currentInventory = this.activePlayer.getInventory();
		int currentPlayerLevel = this.activePlayer.getLevel();
		int armorPartsCosts = ((Math.max(1, (currentPlayerLevel / 5))) * 6);
		int goldCosts = (int)((((currentPlayerLevel / (double)15) * 900) * (currentPlayerLevel / (double)36)) + 20);
		if((currentInventory.getArmorPartsCount() >= armorPartsCosts) && (currentInventory.getGoldCount() >= goldCosts))
		{
			if((currentInventory.getInventoryContentList().size()) < (currentInventory.getInventorySize()))
			{
				this.generateNewEquipment(paramSelectionID, true);
				currentInventory.modifyArmorPartsCount(-armorPartsCosts);
				currentInventory.modifyGoldCount(-goldCosts);
			}
			else
				this.showcaseView.displayErrorMessage(4);
		}
		else
			this.showcaseView.displayErrorMessage(5);
	}
	
	/**
	 * creates a new consumable depending on the selectionID
	 * @param paramSelectionID ID of the consumable to be generated
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void generateNewConsumableByID(int paramSelectionID)
	{
		Random randomGenerator = new Random();
		int currentStackSize = Math.max(1, (randomGenerator.nextInt(3)));
		ConsumableModel currentItem = null; 
		
		switch(paramSelectionID)
		{
			case 0:
				currentItem = new HealthPotion(currentStackSize);
				break;
			case 1:
				currentItem = new ManaPotion(currentStackSize);
				break;
		}
		this.globalInventoryList.add(currentItem);
		this.showcaseView.addItemToGlobalInventory(currentItem.getItemName());
	}
	
	/**
	 * generates a new GoldStack
	 * @param paramSelectedValue amount of gold the GoldStack is worth
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void generateNewGoldStack(int paramSelectedValue)
	{
		GoldStack currentGoldStack = new GoldStack(paramSelectedValue);
		this.globalInventoryList.add(currentGoldStack);
		this.showcaseView.addItemToGlobalInventory(currentGoldStack.getItemName());
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
					this.generateNewEquipment(this.showcaseView.getSelectedEquipmentToGenerate(), false);
					break;
				case "consumable":
					this.generateNewConsumableByID(this.showcaseView.getSelectedConsumableToGenerate());
					break;
				case "gold":
					this.generateNewGoldStack(this.showcaseView.getSelectedGoldValueToGenerate());
					break;
			}
		}
		else if(actionCommand.startsWith("modify"))
		{
			switch(actionCommand.substring(7))
			{
				case "level":
					this.activePlayer.setLevel((this.activePlayer.getLevel() + 1));
					break;
				case "kills":
					this.activePlayer.getStatistics().updateMonsterKillCount(1);
					break;
				case "deaths":
					this.activePlayer.getStatistics().updateDeathCount(1);
					break;
				case "time":
					this.activePlayer.getStatistics().updateTimePlayed(1);
					break;
				case "life":
					this.activePlayer.setCurrentLife((this.activePlayer.getCurrentLife() - 10));
					break;
			}
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
		else if(actionCommand.equals("tgl_crafting"))
			this.showcaseView.switchCraftingMode();
		else if(actionCommand.equals("craft_equip"))
			this.craftEquipmentByID(this.showcaseView.getSelectedEquipmentToGenerate());
		else if(actionCommand.equals("highscore"))
			this.programController.initiateHighscore();
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
	
	/**
	 * processes outgoing Messages and calls the receiveMessage method of the respective target address
	 * @param paramMessage the Message to be send
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void sendMessage(Message paramMessage)
	{
		this.programController.receiveMessage(paramMessage);
	}
}