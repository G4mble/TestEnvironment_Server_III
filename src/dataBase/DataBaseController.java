package dataBase;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import platform.ProgramController;
import message.*;
import shared.InventoryModel;
import shared.character.Mage;
import shared.character.PlayerCharacter;
import shared.character.Warrior;
import shared.item.Boots;
import shared.item.ChestArmor;
import shared.item.ConsumableModel;
import shared.item.EquipmentModel;
import shared.item.HealthPotion;
import shared.item.Helmet;
import shared.item.ItemModel;
import shared.item.ManaPotion;
import shared.item.OneHandedWeapon;
import shared.item.Shield;
import shared.item.TwoHandedWeapon;
import shared.statistics.StatisticsModel;

/**
 * manages connection to the database and processes all database queries</br>
 * implements singleton -> private constructor -> use getInstance() instead
 * @author Staufenberg, Thomas, 5820359
 * */
public class DataBaseController
{
	private ProgramController programController;
	private Connection dbConnection;
	private static DataBaseController currentDataBaseController = null;
	private ArrayList<Message> incomingMessageList;
	private boolean messageIsProcessing = false;
	
	/**
	 * private constructor to guarantee only one instance of this class at a time</br>
	 * tries to connect to the DB to see if it is there; switches to noDB mode if an error occurs</br>
	 * initializes the database schema if not already done
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private DataBaseController(ProgramController paramProgramController)
	{
		System.out.println("DataBaseController: Pruefe ob Datenbank vorhanden.");
		if(this.establishConnection())
		{
			this.closeConnection();
			System.out.println("DataBaseController: Pruefung abgeschlossen, Datenbank vorhanden.");
			this.incomingMessageList = new ArrayList<>();
			this.programController = paramProgramController;
			this.initializeDatabaseSchema();
		}
		else
		{
			System.err.println("DataBaseController: Datenbank konnte nicht gefunden werden!");
			//TODO switch to no database mode
		}
	}
	
	/**
	 * works as a replacement for the private constructor due to singleton</br>
	 * if currentDataBaseController is null, it will be initiated
	 * @return current instance of the DataBaseController
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public static DataBaseController getInstance(ProgramController paramProgramController)
	{
		if(currentDataBaseController == null)
			currentDataBaseController = new DataBaseController(paramProgramController);
		return currentDataBaseController;
	}
	
	/**
	 * tries to establish a connection to the database
	 * @return true: connected</br>false: could not establish connection
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private boolean establishConnection()
	{
		try
		{
//			this.dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/team12", "team12", "yiekahpo");
			this.dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
			System.out.println("DataBaseController: Verbindung zur Datenbank hergestellt.");
			return true;
		}
		catch(SQLException sqlE)
		{
			System.err.println("DataBaseController: Verbindung zur Datenbank konnte nicht hergestellt werden!");
			//TODO handle exception
			return false;
		}
	}
	
	/**
	 * closes the open connection to the database to ensure data integrity
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void closeConnection()
	{
		try
		{
			if(this.dbConnection != null)
			{
				this.dbConnection.close();
				this.dbConnection = null;
				System.out.println("DataBaseController: Datenbankverbindung getrennt.");
			}
		}
		catch(SQLException sqlE)
		{
			System.out.println("DataBaseController: Datenbankverbindung konnte nicht getrennt werden oder ist bereits getrennt!");
			//TODO handle exception
		}
	}
	
	/**
	 * creates all required tables in the database and inserts required data for the game to work</br>
	 * if the database has been initialized before, this method changes nothing
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void initializeDatabaseSchema()
	{
		this.establishConnection();
		
		try(Statement stmt = this.dbConnection.createStatement())
		{
			stmt.addBatch("CREATE TABLE IF NOT EXISTS inventory(inventoryID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, goldCount INT NOT NULL DEFAULT 0, armorPartsCount INT NOT NULL DEFAULT 0, healthPotionCount INT NOT NULL DEFAULT 0, manaPotionCount INT NOT NULL DEFAULT 0)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS item(itemID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, equipSlot INT NOT NULL, attackValue INT NOT NULL, defenseValue INT NOT NULL, hpValue INT NOT NULL, levelRestriction INT NOT NULL, itemPrice INT NOT NULL, armorPartsRevenue INT NOT NULL)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS inventoryitemallocation(allocationID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, inventoryID INT NOT NULL, slotID INT NOT NULL, itemID INT NOT NULL, quickSlotID INT NOT NULL DEFAULT -1, CONSTRAINT FOREIGN KEY(inventoryID) REFERENCES inventory(inventoryID), CONSTRAINT FOREIGN KEY(itemID) REFERENCES item(itemID))");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS player(playerID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, charName VARCHAR(30) NOT NULL, hitpoints INT NOT NULL, attackValue INT NOT NULL, defenseValue INT NOT NULL, currentLevel INT NOT NULL DEFAULT 1, experience INT NOT NULL DEFAULT 0)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS registereduser(username VARCHAR(30) NOT NULL PRIMARY KEY, password VARCHAR(30) NOT NULL)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS savegame(saveGameID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, currentStoryLevel INT NOT NULL DEFAULT 1)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS statistics(statisticsID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, monsterKillCount INT NOT NULL DEFAULT 0, numberOfDeaths INT NOT NULL DEFAULT 0, goldEarned INT NOT NULL DEFAULT 0, timePlayed INT NOT NULL DEFAULT 0)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS playerallocation(allocationID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, username VARCHAR(30) NOT NULL, playerID INT NOT NULL, inventoryID INT NOT NULL, saveGameID INT NOT NULL, statisticsID INT NOT NULL, CONSTRAINT FOREIGN KEY(username) REFERENCES registereduser(username), CONSTRAINT FOREIGN KEY(playerID) REFERENCES player(playerID), CONSTRAINT FOREIGN KEY(inventoryID) REFERENCES inventory(inventoryID), CONSTRAINT FOREIGN KEY(saveGameID) REFERENCES savegame(saveGameID), CONSTRAINT FOREIGN KEY(statisticsID) REFERENCES statistics(statisticsID))");
			stmt.addBatch("INSERT IGNORE INTO item VALUES (1, -1, -1, -1, -1, -1, -1, -1), (2, 6, -1, -1, -1, -1, -1, -1), (3, 7, -1, -1, -1, -1, -1, -1)");
			stmt.addBatch("INSERT IGNORE INTO player VALUES (1, 'dummy', 0, 0, 0, 0, 0)");
			
			stmt.executeBatch();
		}
		catch(SQLException sqlE)
		{
			System.err.println("Fehler in DBController.initializeDatabaseSchema()");
			sqlE.printStackTrace();
			//TODO handle exception
		}
		finally
		{
			this.closeConnection();
		}
	}
	
	/**
	 * processes outgoing Messages and calls the receiveMessage method of the respective target address
	 * @param paramMessage the Message to be send
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void sendMessage(Message paramMessage)
	{
		System.out.println("DataBaseController: Sende " + paramMessage.getClass().getName().substring(8));
		this.programController.receiveMessage(paramMessage);
	}
	
	/**
	 * identifies Item via slotID</br>is called by this.loadPlayerData()
	 * @return ItemModel created with data read from the database
	 * @param paramItemResult ResultSet of the item to be identified
	 * @param paramInventoryResult ResultSet of the inventory the item belongs to
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private ItemModel identifyEquipment(ResultSet paramItemResult, ResultSet paramInventoryResult) throws SQLException
	{
		switch(paramItemResult.getInt(3))
		{
			case 0: 
				return new OneHandedWeapon(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6), paramItemResult.getInt(9));
			case 1:
				return new Shield(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6), paramItemResult.getInt(9));
			case 2:
				return new Helmet(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6), paramItemResult.getInt(9));
			case 3:
				return new ChestArmor(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6), paramItemResult.getInt(9));
			case 4:
				return new Boots(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6), paramItemResult.getInt(9));
			case 5:				//two handed weapons are identified in the database by slotID 5 but has slotID 0 ingame
				return new TwoHandedWeapon(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6), paramItemResult.getInt(9));
			case 6:				//HealthPotion is indicated by equipSlot = 6
				return new HealthPotion(paramInventoryResult.getInt(3), paramItemResult.getInt(10));
			case 7:				//ManaPotion is indicated by equipSlot = 7
				return new ManaPotion(paramInventoryResult.getInt(4), paramItemResult.getInt(10));
			default:
				return null;
		}
	}
	
	/**
	 * reads all user related data from the database, creates corresponding data model objects</br>
	 * @param paramUsername name of the user requesting the playerData
	 * @param paramClientID clientID to assign PlayerCharacter to specific client
	 * @return the PlayerCharacter created from the data read from the database
	 * @author Staufenberg, Thomas, 5820359
	 * */
	protected PlayerCharacter loadPlayerData(String paramUsername, int paramClientID)
	{		
		this.establishConnection();
		
		try(Statement retrieveIDStmt = this.dbConnection.createStatement(); ResultSet retrieveIDResult = retrieveIDStmt.executeQuery("SELECT playerID, inventoryID, saveGameID, statisticsID FROM registeredUser JOIN playerAllocation ON registeredUser.username = playerAllocation.username WHERE playerAllocation.username = '" + paramUsername + "'"))
		{
			retrieveIDResult.next();
			
			try(Statement playerStmt = this.dbConnection.createStatement(); ResultSet playerResult = playerStmt.executeQuery("SELECT charName, hitpoints, attackValue, defenseValue, currentLevel, experience FROM player WHERE playerID = " +  retrieveIDResult.getInt(1));
				Statement statisticsStmt = this.dbConnection.createStatement(); ResultSet statisticsResult = statisticsStmt.executeQuery("SELECT monsterKillCount, numberOfDeaths, goldEarned, timePlayed FROM statistics WHERE statisticsID = " + retrieveIDResult.getInt(4));
				Statement saveGameStmt = this.dbConnection.createStatement(); ResultSet saveGameResult = saveGameStmt.executeQuery("SELECT currentStoryLevel FROM saveGame WHERE saveGameID = " + retrieveIDResult.getInt(3));
				Statement inventoryStmt = this.dbConnection.createStatement(); ResultSet inventoryResult = inventoryStmt.executeQuery("SELECT goldCount, armorPartsCount, healthPotionCount, manaPotionCount FROM inventory WHERE inventoryID = " + retrieveIDResult.getInt(2));
				Statement itemStmt = this.dbConnection.createStatement(); ResultSet itemResult = itemStmt.executeQuery("SELECT alloc.slotID, item.itemID, equipSlot, attackValue, defenseValue, hpValue, levelRestriction, itemPrice, armorPartsRevenue, alloc.quickSlotID FROM (SELECT slotID, itemID, quickSlotID FROM inventoryItemAllocation WHERE inventoryID = " + retrieveIDResult.getInt(2) + ") AS alloc JOIN item ON alloc.itemID = item.itemID ORDER BY alloc.slotID"))
			{
				//TODO do something with saveGameData
				saveGameResult.next();
	
				//create statistics
				statisticsResult.next();
				StatisticsModel currentStatistics = new StatisticsModel(statisticsResult.getInt(1), statisticsResult.getInt(2), statisticsResult.getInt(3), statisticsResult.getInt(4));
				
				//add items to the inventory
				inventoryResult.next();
				ArrayList<ItemModel> currentInventoryContentList = new ArrayList<>();
				for(int i = 0; i < 10; i++)
				{	
					itemResult.next();
					if(itemResult.getInt(2) != 1)
						currentInventoryContentList.add(this.identifyEquipment(itemResult, inventoryResult));
				}
				
				//add equipment to equipmentList
				EquipmentModel[] currentEquipmentList = new EquipmentModel[5];
				for(int j = 0; j < 5; j++)
				{
					itemResult.next();
					if(itemResult.getInt(2) != 1)
						currentEquipmentList[j] = (EquipmentModel) this.identifyEquipment(itemResult, inventoryResult);
				}
	
				//create inventory, link with inventoryContentList and equipmentList
				InventoryModel currentInventory = new InventoryModel(inventoryResult.getInt(1), inventoryResult.getInt(2), currentInventoryContentList, currentEquipmentList);
				
				//create new player, link with inventory, statistics
				playerResult.next();
				PlayerCharacter currentPlayer = null;
				switch(playerResult.getString(1))
				{
					case "Krieger":
						currentPlayer = new Warrior(paramClientID, playerResult.getInt(5), playerResult.getInt(6), playerResult.getInt(2), playerResult.getInt(3), playerResult.getInt(4), retrieveIDResult.getInt(1), currentInventory, currentStatistics);
						break;
					case "Magier":
						currentPlayer = new Mage(paramClientID, playerResult.getInt(5), playerResult.getInt(6), playerResult.getInt(2), playerResult.getInt(3), playerResult.getInt(4), retrieveIDResult.getInt(1), currentInventory, currentStatistics);
						break;
				}
				return currentPlayer;
			}
		}
		catch(SQLException sqlE)
		{
			System.err.println("Fehler in DBController.loadPlayerData()");
			sqlE.printStackTrace();
			//TODO handle exception
		}
		finally
		{
			this.closeConnection();
		}
		return null;
	}
	
	/**
	 * reads the highscore data from the database and sorts it DESC by paramFilterAttribute
	 * @param paramFilterAttribute the String attribute by which the retrieved data is sorted
	 * @return highscore data stored in a Object[][]
	 * @author Staufenberg, Thomas, 5820359 
	 * */
	private Object[][] loadHighscore(String paramFilterAttribute)
	{
		System.out.println("DataBaseController: Lade Highscoredaten mit Filter: " + paramFilterAttribute);
		this.establishConnection();
		
		try(Statement stmt = this.dbConnection.createStatement(); ResultSet highscoreResult = stmt.executeQuery("SELECT alloc.username, monsterKillCount, numberOfDeaths, goldEarned, timePlayed FROM statistics JOIN(SELECT playerAllocation.username, statisticsID FROM registeredUser JOIN playerAllocation ON registeredUser.username = playerAllocation.username) as alloc ON statistics.statisticsID = alloc.statisticsID ORDER BY " + paramFilterAttribute + " DESC"))
		{
			int rowCount = 0;
			Object[][] highscoreData = null;
			if(highscoreResult.last())
			{
				rowCount = highscoreResult.getRow();
				highscoreData = new Object[rowCount][5];
				highscoreResult.beforeFirst();
			}
			
			while(highscoreResult.next())
			{
				highscoreData[(highscoreResult.getRow() - 1)][0] = highscoreResult.getString(1);
				for(int i = 1; i < 5; i++)
					highscoreData[(highscoreResult.getRow() - 1)][i] = highscoreResult.getInt((i + 1));
			}
			System.out.println("DataBaseController: Highscoredaten geladen.");
			return highscoreData;
		}
		catch(SQLException sqlE)
		{
			System.err.println("DataBaseController: Fehler beim Auslesen von Highscoredateb.");
			//TODO handle exception
			return null;
		}
		finally
		{
			this.closeConnection();
		}
	}
	
	/**
	 * adds the Item to the database if not already done, updates inventorySlot of the item in the inventoryItemAllocation table</br> 
	 * is called by this.savePlayerData()
	 * @param paramAddedItemList ArrayList<Integer> contains the itemID of all items added to the inventory during the current save process
	 * @param paramToDeleteItemList ArrayList<Integer> contains the itemID of all items that might have to be removed from the database
	 * @param paramItemResult ResultSet of the current item
	 * @param paramID itemID of the current item
	 * @param paramItem the current item 
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void saveEquipment(ArrayList<Integer> paramAddedItemList, ArrayList<Integer> paramToDeleteItemList, ResultSet paramItemResult, int paramID, ItemModel paramItem) throws SQLException
	{
		EquipmentModel currentEquip = (EquipmentModel) paramItem;
		try(Statement stmt = this.dbConnection.createStatement())
		{
			if(paramID != paramItemResult.getInt(1))
			{
				if(paramID == -1)
				{
					int currentEquipSlotID = -1;
					if(currentEquip instanceof TwoHandedWeapon)
						currentEquipSlotID = 5;
					else
						currentEquipSlotID = currentEquip.getEquipSlotID();
					
					stmt.executeUpdate("INSERT INTO item(equipSlot, attackValue, defenseValue, hpValue, levelRestriction, itemPrice, armorPartsRevenue) VALUES(" + currentEquipSlotID + ", " + currentEquip.getAttackValue() + ", " + currentEquip.getDefenseValue() + ", " + currentEquip.getHpValue() + ", " + currentEquip.getLevelRestriction() + ", " + currentEquip.getItemGoldValue() + ", " + currentEquip.getArmorPartsRevenue() + ")");
					try(ResultSet newItemIDResult = stmt.executeQuery("SELECT max(itemID) FROM item"))
					{
						newItemIDResult.next();
						paramID = newItemIDResult.getInt(1);
					}
					currentEquip.setItemID(paramID);
				}
				stmt.executeUpdate("UPDATE inventoryItemAllocation SET itemID = " + paramID + " WHERE allocationID = " + paramItemResult.getInt(2));
				
				if(paramItemResult.getInt(1) > 3)
					paramToDeleteItemList.add(paramItemResult.getInt(1));
			}
			paramAddedItemList.add(paramID);
		}
	}
	
	/**
	 * sets the itemID of the current itemSlot to defautl = 1 (database)</br>
	 * adds the previously stored itemID to the deleteList if it is not a default item</br>is called by this.savePlayerData()
	 * @param paramToDeleteItemList ArrayList<Integer> contains the itemID of all items that might have to be removed from the database
	 * @param paramItemResult ResultSet of the currentItem
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void deleteItem(ArrayList<Integer> paramToDeleteItemList, ResultSet paramItemResult)
	{
		try(Statement stmt = this.dbConnection.createStatement())
		{
			stmt.executeUpdate("UPDATE inventoryItemAllocation SET itemID = 1 WHERE allocationID = " + paramItemResult.getInt(2) );
			if(paramItemResult.getInt(1) > 3)
				paramToDeleteItemList.add(paramItemResult.getInt(1));
		}
		catch(SQLException sqlE)
		{
			System.err.println("DataBaseController: Fehler in this.deleteItem()");
		}
	}
	
	/**
	 * saves all relevant data of the given player to the database</br>deletes unused items from the database
	 * @param paramUsername name of the current player
	 * @param paramPlayer the current player
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void savePlayerData(String paramUsername, PlayerCharacter paramPlayer)
	{
		System.out.println("DataBaseController: Speichere Spielerdaten.");
		//collect data to save
		StatisticsModel currentStatistics = paramPlayer.getStatistics();
		InventoryModel currentInventory = paramPlayer.getInventory();
		ArrayList<ItemModel> currentInventoryContentList = currentInventory.getInventoryContentList();
		EquipmentModel[] currentEquipmentList = currentInventory.getEquipmentList();
		
		this.establishConnection();
		
		//retrieve old data from DB for comparison
		try(Statement retrieveIDStmt = this.dbConnection.createStatement(); ResultSet retrieveIDResult = retrieveIDStmt.executeQuery("SELECT playerID, inventoryID, saveGameID, statisticsID FROM registeredUser JOIN playerAllocation ON registeredUser.username = playerAllocation.username WHERE playerAllocation.username = '" + paramUsername + "'");
			Statement stmt = this.dbConnection.createStatement())
		{
			retrieveIDResult.next();
			try(Statement itemStmt = this.dbConnection.createStatement(); ResultSet itemResult = itemStmt.executeQuery("SELECT itemID, allocationID, slotID FROM inventoryItemAllocation WHERE inventoryID = " + retrieveIDResult.getInt(2) + " ORDER BY slotID"))
			{
				
				this.dbConnection.setAutoCommit(false);			//begin transaction
				
				//update statistics, player experience and level
				stmt.executeUpdate("UPDATE statistics SET monsterKillCount = " + currentStatistics.getMonsterKillCount() + ", numberOfDeaths = " + currentStatistics.getDeathCount() + ", goldEarned = " + currentStatistics.getGoldEarned() + ", timePlayed = " + currentStatistics.getTimePlayed() + " WHERE statisticsID = " + retrieveIDResult.getInt(4));
				stmt.executeUpdate("UPDATE player SET currentLevel = " + paramPlayer.getLevel() + ", experience = " + paramPlayer.getExperiencePoints() + " WHERE playerID = " + retrieveIDResult.getInt(1));
				
				//update inventory values: gold and armorParts				
				stmt.executeUpdate("UPDATE inventory SET goldCount = " + currentInventory.getGoldCount() + ", armorPartsCount = " + currentInventory.getArmorPartsCount() + " WHERE inventoryID = " + retrieveIDResult.getInt(2));
				
				//update items stored in inventory
				ArrayList<Integer> toDeleteItemList = new ArrayList<>();
				ArrayList<Integer> addedItemList = new ArrayList<>();
				boolean hasHealthPotion = false;
				boolean hasManaPotion = false;
				
				for(int i = 0; i < currentInventory.getInventorySize(); i++)
				{
					try
					{
						ItemModel currentItem = currentInventoryContentList.get(i);
						int currentID = currentItem.getItemID();
						itemResult.next();
						
						if(currentItem instanceof ConsumableModel)
						{
							int tmpID = 0;
							if(currentItem instanceof HealthPotion)
							{
								stmt.executeUpdate("UPDATE inventory SET healthPotionCount = " + ((HealthPotion) currentItem).getStackSize() + " WHERE inventoryID = " + retrieveIDResult.getInt(2));
								tmpID = 2;
								hasHealthPotion = true;
							}
							else if(currentItem instanceof ManaPotion)
							{
								stmt.executeUpdate("UPDATE inventory SET manaPotionCount = " + ((ManaPotion) currentItem).getStackSize() + " WHERE inventoryID = " + retrieveIDResult.getInt(2));
								tmpID = 3;
								hasManaPotion = true;
							}
							stmt.executeUpdate("UPDATE inventoryItemAllocation SET itemID = " + tmpID + " ,quickSlotID = " + ((ConsumableModel)currentItem).getQuickSlotID() + " WHERE allocationID = " + itemResult.getInt(2));
							if(itemResult.getInt(1) > 3)
									toDeleteItemList.add(itemResult.getInt(1));
							continue;
						}
						this.saveEquipment(addedItemList, toDeleteItemList, itemResult, currentID, currentItem);
					}
					catch(IndexOutOfBoundsException iOoBE)
					{
						//if the rest of the inventory is empty, delete all previously stored items
						for(int j = i; j < 10; j++)
						{
							itemResult.next();
							this.deleteItem(toDeleteItemList, itemResult);
						}
						// exit main loop
						break;
					}
					finally
					{
						if(!hasHealthPotion)
							stmt.executeUpdate("UPDATE inventory SET healthPotionCount = 0 WHERE inventoryID = " + retrieveIDResult.getInt(2));
						if(!hasManaPotion)
							stmt.executeUpdate("UPDATE inventory SET manaPotionCount = 0 WHERE inventoryID = " + retrieveIDResult.getInt(2));
					}
				}
				
				//update items in equipment
				itemResult.absolute(10);				//moves the cursor to row 10, one row before equipList starts
				for(int j = 0; j < currentEquipmentList.length; j++)
				{
					EquipmentModel currentItem = currentEquipmentList[j];
					itemResult.next();
					if(currentItem != null)
					{
						int currentID = currentItem.getItemID();
						this.saveEquipment(addedItemList, toDeleteItemList, itemResult, currentID, currentItem);
					}
					else
						this.deleteItem(toDeleteItemList, itemResult);		//if the current slot is empty, delete the previously stored item if exists
				}
				
	
				//validate items to delete from DB
				for(int i = 0; i < addedItemList.size(); i++)
					for(int j = 0; j < toDeleteItemList.size(); j++)
						{
							int addID = addedItemList.get(i);
							int removeID = toDeleteItemList.get(j);
							if(addID == removeID)
							{
								toDeleteItemList.remove(j);
								break;
							}
						}
	
				//delete unused items from DB
				for(int i = 0; i < toDeleteItemList.size(); i++)
					stmt.executeUpdate("DELETE FROM item WHERE itemID = " + toDeleteItemList.get(i));
				
				this.dbConnection.commit();				//end transaction
				System.out.println("DataBaseController: Spielerdaten gespeichert.");
			}
		}
		catch(SQLException sqlE)
		{
			try
			{
				System.err.println("DataBaseController: Fehler beim Speichern von Spielerdaten: attempt dbConnection.rollback()");
				this.dbConnection.rollback();					//onException: attempt rollback
			}
			catch(SQLException embSqlE)
			{
				System.err.println("DataBaseController: Fehler beim Speichern von Spielerdaten: dbConntection.rollback() -> failed");
				//TODO error: rollback failed
			}
			System.err.println("DataBaseController: Fehler beim Speichern von Spielerdaten: dbConnection.rollback() -> success");
			//TODO handle exception
		}
		finally
		{
			try
			{
				this.dbConnection.setAutoCommit(true);			// restore default: autoCommit = true
				this.closeConnection();
			}
			catch(SQLException sqlE)
			{
				System.err.println("DataBaseController: Fehler beim Speichern von Spielerdaten: dbConnection.setAutocommit(true) -> failed");
				//TODO handle exception: setAutocommit(true) failed -> data integrity cannot be guaranteed
			}
		}
	}
	
	/**
	 * verifies the correctness of the given set of username and password
	 * @author Staufenberg, Thomas, 5820359
	 * @param paramUsername username entered in the login panel
	 * @param paramPassword password entered in the login panel
	 * @return true: login successful</br>false: error on username or password
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private boolean verifyLogin(String paramUsername, String paramPassword)
	{
		System.out.println("DataBaseController: Verifiziere Login Daten.");
		this.establishConnection();
		
		try(Statement stmt = this.dbConnection.createStatement(); ResultSet registeredUserResult = stmt.executeQuery("SELECT password FROM registeredUser WHERE username = '" + paramUsername + "'"))
		{
			if(registeredUserResult.next())
				if(registeredUserResult.getString(1).equals(paramPassword))
				{
					System.out.println("DataBaseController: Logindaten verifiziert, Benutzername, Password sind korrekt.");
					return true;
				}
			System.out.println("DataBaseController: Logindaten verifiziert, Benutzername oder Passwort falsch.");
			return false;
		}
		catch(SQLException sqlE)
		{
			System.err.println("DataBaseController: Fehler beim verifizieren der Login Daten.");
		}
		finally
		{
			this.closeConnection();
		}
		return false;
	}
	
	/**
	 * verifies if the given username is not already taken</br>called by this.createNewUser()
	 * @param paramUsername username entered in the register panel
	 * @return true: username not taken yet</br>false: username already taken
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private boolean isValidUsername(String paramUsername)
	{
		System.out.println("DataBaseController: Pruefe ob Benutzername bereits vergeben.");
		try(Statement stmt = this.dbConnection.createStatement(); ResultSet userResult = stmt.executeQuery("SELECT username FROM registeredUser WHERE username = '" + paramUsername + "'"))
		{
			if(!(userResult.next()))
			{
				System.out.println("DataBaseController: Benutzername noch nicht vergeben.");
				return true;
			}
		}
		catch(SQLException sqlE)
		{
			System.err.println("Fehler in DBController.isValidUsername()\nSQLException");
			//TODO handle exception
		}
		System.out.println("DataBaseController: Benutzername bereits vergeben.");
		return false;
	}
	
	/**
	 * invokes username verification</br>creates data sets for the given user in the following relations:</br>
	 * -registeredUser</br>-saveGame</br>-statistics</br>-inventory</br>-inventoryItemAllocation</br>-playerAllocation
	 * @param paramUsername username entered in the register panel
	 * @param paramPassword password entered in the register panel
	 * @return true: user successfully created</br>false: username already taken
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private boolean createNewUser(String paramUsername, String paramPassword)
	{
		System.out.println("DataBaseController: Erstelle neuen Benutzer in Datenbank.");
		this.establishConnection();
		
		int inventoryID = -1, saveGameID = -1, statisticsID = -1;
		
		if(isValidUsername(paramUsername))
		{
			try(Statement stmt = this.dbConnection.createStatement())
			{
				this.dbConnection.setAutoCommit(false);			//begin transaction
				
				//insert data into db; create new rows, let db fill default values
				stmt.executeUpdate("INSERT INTO registeredUser VALUES('" + paramUsername + "' ,'" + paramPassword + "')");
				stmt.executeUpdate("INSERT INTO saveGame VALUES()");
				stmt.executeUpdate("INSERT INTO statistics VALUES()");
				stmt.executeUpdate("INSERT INTO inventory VALUES()");
				
				
				//retrieve all previously by db generated ID's for further usage 
				try(Statement saveGameIDStmt = this.dbConnection.createStatement(); ResultSet saveGameIDResult = saveGameIDStmt.executeQuery("SELECT max(saveGameID) FROM saveGame");
					Statement statisticsIDStmt = this.dbConnection.createStatement(); ResultSet statisticsIDResult = statisticsIDStmt.executeQuery("SELECT max(statisticsID) FROM statistics");
					Statement inventoryIDStmt = this.dbConnection.createStatement(); ResultSet inventoryIDResult = inventoryIDStmt.executeQuery("SELECT max(inventoryID) FROM inventory"))
				{
					saveGameIDResult.next();
					statisticsIDResult.next();
					inventoryIDResult.next();
					
					inventoryID = inventoryIDResult.getInt(1);
					saveGameID = saveGameIDResult.getInt(1);
					statisticsID = statisticsIDResult.getInt(1);
				}
				
				//create inventory slots
				try(PreparedStatement pstmt = this.dbConnection.prepareStatement("INSERT INTO inventoryItemAllocation(inventoryID, slotID, itemID) VALUES(" + inventoryID + ", ?, 1)"))
				{
					for(int i = 1; i <= 15; i++)
					{
						pstmt.setInt(1, i);
						pstmt.executeUpdate();
					}
				}
				
				//link player in playerAllocation with previously created data sets via corresponding ID
				stmt.executeUpdate("INSERT INTO playerAllocation(username, playerID, inventoryID, saveGameID, statisticsID) VALUES('" + paramUsername + "', 1, " + inventoryID + ", " + saveGameID + ", " + statisticsID +")");
				
				this.dbConnection.commit();							//end transaction
				System.out.println("DataBaseController: Benutzer erfolgreich erstellt.");
			}
			catch(SQLException sqlE)
			{
				try
				{
					System.err.println("DataBaseController: Fehler bei Erstellung von Benutzer: attempt dbConnection.rollback()");
					this.dbConnection.rollback();					//onException: attempt rollback
				}
				catch(SQLException embSqlE)
				{
					System.err.println("DataBaseController: Fehler bei Erstellung von Benutzer: dbConntection.rollback() -> failed");
					//TODO return error: rollback failed
				}
				System.err.println("DataBaseController: Fehler bei Erstellung von Benutzer!");
				//TODO handle exception
			}
			finally
			{
				try
				{
					this.dbConnection.setAutoCommit(true);			// restore default: autoCommit = true
					this.closeConnection();
				}
				catch(SQLException sqlE)
				{
					System.err.println("DataBaseController: Fehler bei Erstellung von Benutzer: dbConnection.setAutocommit(true) -> failed");
					//TODO handle exception: setAutocommit(true) failed -> data integrity cannot be guaranteed
				}
			}
			return true;
		}
		else
			return false;
	}
	
	/**
	 * creates a data set in the relation 'player'</br>links the playerID to 'playerAllocation' via username</br>
	 * invokes save operation if the process finished without exception
	 * @param paramUsername username of the considered user
	 * @param paramPlayer PlayerCharacter object of the associated user
	 * @return true: character successfully linked to user</br>false: error during the process
	 * @author Staufenberg, Thomas, 5820359
	 * */
	protected boolean linkCharacterToUser(String paramUsername, PlayerCharacter paramPlayer)
	{
		boolean noErrorDetected = true;
		this.establishConnection();
		
		try (Statement stmt = this.dbConnection.createStatement(); ResultSet consitencyCheckResult = stmt.executeQuery("SELECT playerID FROM playerAllocation WHERE username = '" + paramUsername + "'"))
		{
			consitencyCheckResult.next();
			if (consitencyCheckResult.getInt(1) == 1)
			{
				this.dbConnection.setAutoCommit(false);				// begin transaction

				//insert character into player
				stmt.executeUpdate("INSERT INTO player(charName, hitpoints, attackValue, defenseValue) VALUES('" + paramPlayer.getCharacterName() + "', " + paramPlayer.getCurrentLife() + ", " + paramPlayer.getAttack() + ", " + paramPlayer.getDefense() + ")");

				//retrieve autogenerated playerID from db 
				try (ResultSet playerIDResult = stmt.executeQuery("SELECT max(playerID) FROM player"))
				{
					//link player in playerAllocation to the given playerID
					playerIDResult.next();
					stmt.executeUpdate("UPDATE playerAllocation SET playerID = " + playerIDResult.getInt(1) + " WHERE username = '" + paramUsername + "'");
				}
				this.dbConnection.commit();							// end transaction
				return true;
			}
		}
		catch (SQLException sqlE)
		{
			noErrorDetected = false;
			try
			{
				this.dbConnection.rollback(); 						// onException: attempt rollback
			}
			catch (SQLException embSqlE)
			{
				System.err.println("Fehler in DBController.linkCharacterToUser()\nrollback failed");
				embSqlE.printStackTrace();
				// TODO return error: rollback failed
			}
			System.err.println("Fehler in DBController.linkCharacterToUser()");
			sqlE.printStackTrace();
			// TODO handle exception
		}
		finally
		{
			try
			{
				this.dbConnection.setAutoCommit(true);				// restore default: autoCommit = true
				this.closeConnection();
				if(noErrorDetected)
					this.savePlayerData(paramUsername, paramPlayer);
			}
			catch(SQLException sqlE)
			{
				System.err.println("Fehler in DBController.linkCharacterToUser()\nsetAutocommit(true) failed -> data integrity cannot be guaranteed");
				sqlE.printStackTrace();
				//TODO handle exception: setAutocommit(true) failed -> data integrity cannot be guaranteed
			}
		}
		return false;
	}
	
	/**
	 * called by ServerEngine_I for communication purpose</br>
	 * adds the given Message to a list and starts the handling process if it is not already running
	 * @param paramMessage the Message to receive
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void receiveMessage(Message paramMessage)
	{
		System.out.println("DataBaseController: Empfange " + paramMessage.getClass().getName().substring(8));
		this.incomingMessageList.add(paramMessage);
		if(!this.messageIsProcessing)
			this.handleMessage();
	}
	
	/**
	 * called by this.receiveMessage(), iterates over incomingMessageList while not empty</br>
	 * handles all incoming Messages
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void handleMessage()
	{
		System.out.println("DataBaseController: Starte Message handling.");
		if(!this.messageIsProcessing)
			this.messageIsProcessing = true;
		while(true)
		{
			Message currentMessage;
			try
			{
				currentMessage = this.incomingMessageList.remove(0);
				System.out.println("DataBaseController: Behandle " + currentMessage.getClass().getName().substring(8));
			}
			catch(IndexOutOfBoundsException indexExc)
			{
				//stop message handling if there is no message left
				System.out.println("DataBaseController: Keine weiteren Messages vorhanden.");
				break;
			}
			
			if(currentMessage instanceof VerifyLoginMessage)
			{
				String currentUsername = ((VerifyLoginMessage) currentMessage).getUsername(); 
				String currentPassword = ((VerifyLoginMessage) currentMessage).getPassword();
				if(this.verifyLogin(currentUsername, currentPassword))
					this.sendMessage(new LoginSuccessMessage(this.loadPlayerData(currentUsername, 1)));				//set default clientID = 1 //TODO read clientID from LoginMessage
				else
					this.sendMessage(new OperationRejectedMessage("Benutzername oder Passwort falsch!"));
			}
			else if(currentMessage instanceof RegisterUserMessage)
				if(this.createNewUser(((RegisterUserMessage) currentMessage).getUsername(), ((RegisterUserMessage) currentMessage).getPassword()))
					this.sendMessage(new RegisterSuccessMessage());
				else
					this.sendMessage(new OperationRejectedMessage("Benutzername bereits vergeben!"));
			else if(currentMessage instanceof LinkCharacterToUserMessage)
				if(this.linkCharacterToUser(((LinkCharacterToUserMessage) currentMessage).getUsername(), ((LinkCharacterToUserMessage) currentMessage).getPlayerCharacter()))
					this.sendMessage(new OperationPerformedMessage());
				else
					this.sendMessage(new OperationRejectedMessage("Fehler beim Erstellen des Charakters!"));
			else if(currentMessage instanceof SavePlayerDataMessage)
				this.savePlayerData(((SavePlayerDataMessage) currentMessage).getUsername(), ((SavePlayerDataMessage) currentMessage).getPlayerCharacter());
			else if(currentMessage instanceof LoadHighscoreMessage)
				this.sendMessage(new HighscoreDataMessage((this.loadHighscore(((LoadHighscoreMessage) currentMessage).getFilterAttribute()))));
		}
		System.out.println("DataBaseController: Stoppe Message handling.");
		System.out.println("-----------------------------------------------------------");
		this.messageIsProcessing = false;
	}
}