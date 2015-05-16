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
import shared.character.PlayerCharacter;
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
	 * tries to connect to the DB to see if it is there, switches to noDB mode if an error occurs
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private DataBaseController(ProgramController paramProgramController)
	{
		
		if(this.establishConnection())
			this.closeConnection();
		else
		{
			System.err.println("Verbindung zur Datenbak fehlgeschlagen!");
			//TODO switch to no database mode...
		}
		this.incomingMessageList = new ArrayList<>();
		this.programController = paramProgramController;
		this.initializeDatabaseSchema();
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
	 * @return true: connected</br>false: couldn't connect
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private boolean establishConnection()
	{
		try
		{
//			this.dbConnection = DriverManager.getConnection("jdbc:mysql://localhost/team12", "team12", "yiekahpo");
			this.dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
			return true;
		}
		catch(SQLException sqlE)
		{
			System.err.println("Fehler in establishConnection()");
			sqlE.printStackTrace();
			return false;
		}
	}
	
	/**
	 * closes all open connections to the database to ensure data integrity
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void closeConnection()
	{
		try
		{
			if(this.dbConnection != null)
			{
				this.dbConnection.close();
				this.dbConnection = null;
			}
		}
		catch(SQLException sqlE)
		{
			System.out.println("fehler in closeConnection()");
			sqlE.printStackTrace();
		}
	}
	
	private void initializeDatabaseSchema()
	{
		this.establishConnection();
		
		try(Statement stmt = this.dbConnection.createStatement())
		{
			stmt.addBatch("CREATE TABLE IF NOT EXISTS inventory(inventoryID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, goldCount INT NOT NULL DEFAULT 0, armorPartsCount INT NOT NULL DEFAULT 0, healthPotionCount INT NOT NULL DEFAULT 0, manaPotionCount INT NOT NULL DEFAULT 0)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS item(itemID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, equipSlot INT NOT NULL, attackValue INT NOT NULL, defenseValue INT NOT NULL, hpValue INT NOT NULL, levelRestriction INT NOT NULL, itemPrice INT NOT NULL, armorPartsRevenue INT NOT NULL)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS inventoryitemallocation(allocationID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, inventoryID INT NOT NULL, slotID INT NOT NULL, itemID INT NOT NULL, CONSTRAINT FOREIGN KEY(inventoryID) REFERENCES inventory(inventoryID), CONSTRAINT FOREIGN KEY(itemID) REFERENCES item(itemID))");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS player(playerID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, charImg VARCHAR(200) NOT NULL, charName VARCHAR(30) NOT NULL, hitpoints INT NOT NULL, attackValue INT NOT NULL, defenseValue INT NOT NULL, currentLevel INT NOT NULL DEFAULT 1, experience INT NOT NULL DEFAULT 0)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS registereduser(username VARCHAR(30) NOT NULL PRIMARY KEY, password VARCHAR(30) NOT NULL)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS savegame(saveGameID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, currentStoryLevel INT NOT NULL DEFAULT 1)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS statistics(statisticsID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, monsterKillCount INT NOT NULL DEFAULT 0, numberOfDeaths INT NOT NULL DEFAULT 0, goldEarned INT NOT NULL DEFAULT 0, timePlayed INT NOT NULL DEFAULT 0)");
			stmt.addBatch("CREATE TABLE IF NOT EXISTS playerallocation(allocationID INT NOT NULL PRIMARY KEY AUTO_INCREMENT, username VARCHAR(30) NOT NULL, playerID INT NOT NULL, inventoryID INT NOT NULL, saveGameID INT NOT NULL, statisticsID INT NOT NULL, CONSTRAINT FOREIGN KEY(username) REFERENCES registereduser(username), CONSTRAINT FOREIGN KEY(playerID) REFERENCES player(playerID), CONSTRAINT FOREIGN KEY(inventoryID) REFERENCES inventory(inventoryID), CONSTRAINT FOREIGN KEY(saveGameID) REFERENCES savegame(saveGameID), CONSTRAINT FOREIGN KEY(statisticsID) REFERENCES statistics(statisticsID))");
			stmt.addBatch("INSERT IGNORE INTO item VALUES (1, -1, -1, -1, -1, -1, -1, -1), (2, 6, -1, -1, -1, -1, -1, -1), (3, 7, -1, -1, -1, -1, -1, -1)");
			stmt.addBatch("INSERT IGNORE INTO player VALUES (1, 'dummy', 'dummy', 0, 0, 0, 0, 0)");
			
			stmt.executeBatch();
		}
		catch(SQLException sqlE)
		{
			System.err.println("Fehler in DBController.initializeDatabaseSchema()");
			sqlE.printStackTrace();
			//TODO handle exception
		}
	}
	
	public void receiveMessage(Message paramMessage)
	{
		this.incomingMessageList.add(paramMessage);
		if(!this.messageIsProcessing)
			this.handleMessage();
	}
	
	private void handleMessage()
	{
		if(!this.messageIsProcessing)
			this.messageIsProcessing = true;
		while(true)
		{
			Message currentMessage;
			try
			{
				currentMessage = this.incomingMessageList.remove(0);
			}
			catch(IndexOutOfBoundsException indexExc)
			{
				break;
			}
			
			if(currentMessage instanceof VerifyLoginMessage)
			{
				String currentUsername = ((VerifyLoginMessage) currentMessage).getUsername(); 
				String currentPassword = ((VerifyLoginMessage) currentMessage).getPassword();
				if(this.verifyLogin(currentUsername, currentPassword))
					this.sendMessage(new LoginSuccessMessage(this.loadPlayerData(currentUsername, 1)));				//set default clientID = 1
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
					this.sendMessage(new OperationPerformedMessage(1));
				else
					this.sendMessage(new OperationRejectedMessage("Fehler beim Erstellen des Charakters!"));
			else if(currentMessage instanceof SavePlayerDataMessage)
				this.savePlayerData(((SavePlayerDataMessage) currentMessage).getUsername(), ((SavePlayerDataMessage) currentMessage).getPlayerCharacter());
		}
		this.messageIsProcessing = false;
	}
	
	private void sendMessage(Message paramMessage)
	{
		this.programController.receiveMessage(paramMessage);
	}
	
	
	/**
	 * verifies the correctness of the given set of username and password
	 * @author Staufenberg, Thomas, 5820359
	 * @param paramUsername username entered in the login panel
	 * @param paramPassword password entered in the login panel
	 * @return Message
	 * */
	private boolean verifyLogin(String paramUsername, String paramPassword)
	{
		this.establishConnection();
		
		try(Statement stmt = this.dbConnection.createStatement(); ResultSet registeredUserResult = stmt.executeQuery("SELECT password FROM registeredUser WHERE username = '" + paramUsername + "'"))
		{
			if(registeredUserResult.next())
				if(registeredUserResult.getString(1).equals(paramPassword))
					return true;
			return false;
		}
		catch(SQLException sqlE)
		{
			System.err.println("Fehler beim verifizieren der Login-Daten\nDataBaseController.verfiyLogin()\n\n");
			sqlE.printStackTrace();
		}
		finally
		{
			this.closeConnection();
		}
		return false;
	}
	
	/**
	 * identifies Item via slotID
	 * @return ItemModel created with data read from the database
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private ItemModel switchEquipment(ResultSet paramItemResult, ResultSet paramInventoryResult) throws SQLException
	{
		switch(paramItemResult.getInt(3))
		{
			case 0: 
				return new OneHandedWeapon(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6));
			case 1:
				return new Shield(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6));
			case 2:
				return new Helmet(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6));
			case 3:
				return new ChestArmor(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6));
			case 4:
				return new Boots(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6));
			case 5:				//two handed weapons are indentified in the database by ID 5 but in the code via boolean isTwoHand = true AND slotID = 0
				return new TwoHandedWeapon(paramItemResult.getInt(2), paramItemResult.getInt(8), paramItemResult.getInt(7), paramItemResult.getInt(4), paramItemResult.getInt(5), paramItemResult.getInt(6));
			case 6:				//HealthPotion is indicated by equipSlot = 6
				return new HealthPotion(paramInventoryResult.getInt(3));
			case 7:				//ManaPotion is indicated by equipSlot = 7
				return new ManaPotion(paramInventoryResult.getInt(4));
			default:
				return null;
		}
	}
	
	/**
	 * reads all user related data from the database, creates corresponding data model objects</br>
	 * is called by DataBaseController.verifyLogin() after confirmation of username, password
	 * @author Staufenberg, Thomas, 5820359
	 * @param paramUsername username forwarded by DataBaseController.verifyLogin()
	 * */
	private PlayerCharacter loadPlayerData(String paramUsername, int paramClientID)
	{		
		this.establishConnection();
		
		try(Statement retrieveIDStmt = this.dbConnection.createStatement(); ResultSet retrieveIDResult = retrieveIDStmt.executeQuery("SELECT playerID, inventoryID, saveGameID, statisticsID FROM registeredUser JOIN playerAllocation ON registeredUser.username = playerAllocation.username WHERE playerAllocation.username = '" + paramUsername + "'"))
		{
			retrieveIDResult.next();
			
			try(Statement playerStmt = this.dbConnection.createStatement(); ResultSet playerResult = playerStmt.executeQuery("SELECT charImg, charName, hitpoints, attackValue, defenseValue, currentLevel, experience FROM player WHERE playerID = " +  retrieveIDResult.getInt(1));
				Statement statisticsStmt = this.dbConnection.createStatement(); ResultSet statisticsResult = statisticsStmt.executeQuery("SELECT monsterKillCount, numberOfDeaths, goldEarned, timePlayed FROM statistics WHERE statisticsID = " + retrieveIDResult.getInt(4));
				Statement saveGameStmt = this.dbConnection.createStatement(); ResultSet saveGameResult = saveGameStmt.executeQuery("SELECT currentStoryLevel FROM saveGame WHERE saveGameID = " + retrieveIDResult.getInt(3));
				Statement inventoryStmt = this.dbConnection.createStatement(); ResultSet inventoryResult = inventoryStmt.executeQuery("SELECT goldCount, armorPartsCount, healthPotionCount, manaPotionCount FROM inventory WHERE inventoryID = " + retrieveIDResult.getInt(2));
				Statement itemStmt = this.dbConnection.createStatement(); ResultSet itemResult = itemStmt.executeQuery("SELECT alloc.slotID, item.itemID, equipSlot, attackValue, defenseValue, hpValue, levelRestriction, itemPrice FROM (SELECT slotID, itemID FROM inventoryItemAllocation WHERE inventoryID = " + retrieveIDResult.getInt(2) + ") AS alloc JOIN item ON alloc.itemID = item.itemID ORDER BY alloc.slotID"))
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
						currentInventoryContentList.add(this.switchEquipment(itemResult, inventoryResult));
				}
				
				//add equipment to equipmentList
				EquipmentModel[] currentEquipmentList = new EquipmentModel[5];
				for(int j = 0; j < 5; j++)
				{
					itemResult.next();
					if(itemResult.getInt(2) != 1)
						currentEquipmentList[j] = (EquipmentModel) this.switchEquipment(itemResult, inventoryResult);
				}
	
				//create inventory, link with inventoryContentList and equipmentList
				InventoryModel currentInventory = new InventoryModel(inventoryResult.getInt(1), inventoryResult.getInt(2), currentInventoryContentList, currentEquipmentList);
				
				//create new player, link with inventory, statistics
				playerResult.next();
				return new PlayerCharacter(paramClientID, playerResult.getInt(6), playerResult.getInt(7), playerResult.getInt(3), playerResult.getInt(4), playerResult.getInt(5), retrieveIDResult.getInt(1), playerResult.getString(2), -1, -1, true, playerResult.getString(1), currentInventory, currentStatistics);
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
	
	private void saveEquipment(ArrayList<Integer> paramAddedItemList, ArrayList<Integer> paramToDeleteItemList, ResultSet paramItemResult, int paramID, ItemModel paramItem) throws SQLException
	{
		EquipmentModel currentEquip = (EquipmentModel) paramItem;
		try(Statement stmt = this.dbConnection.createStatement())
		{
			if(paramID != paramItemResult.getInt(1))
			{				
				if(paramID == -1)
				{
					stmt.executeUpdate("INSERT INTO item(equipSlot, attackValue, defenseValue, hpValue, levelRestriction, itemPrice, armorPartsRevenue) VALUES(" + currentEquip.getEquipSlotID() + ", " + currentEquip.getAttackValue() + ", " + currentEquip.getDefenseValue() + ", " + currentEquip.getHpValue() + ", " + currentEquip.getLevelRestriction() + ", " + currentEquip.getItemGoldValue() + ", " + currentEquip.getArmorPartsRevenue() + ")");
					try(ResultSet newItemIDResult = stmt.executeQuery("SELECT max(itemID) FROM item"))
					{
						newItemIDResult.next();
						paramID = newItemIDResult.getInt(1);
					}
				}
				stmt.executeUpdate("UPDATE inventoryItemAllocation SET itemID = " + paramID + " WHERE allocationID = " + paramItemResult.getInt(2));
				
				paramAddedItemList.add(paramID);
				if(paramItemResult.getInt(1) != 1)
					paramToDeleteItemList.add(paramItemResult.getInt(1));
			}
		}
	}
	
	private void savePlayerData(String paramUsername, PlayerCharacter paramPlayer)
	{
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
				
				//update items stored in inventory
				ArrayList<Integer> toDeleteItemList = new ArrayList<>();
				ArrayList<Integer> addedItemList = new ArrayList<>();
				
				for(int i = 0; i < currentInventoryContentList.size(); i++)
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
						}
						else if(currentItem instanceof ManaPotion)
						{
							stmt.executeUpdate("UPDATE inventory SET manaPotionCount = " + ((ManaPotion) currentItem).getStackSize() + " WHERE inventoryID = " + retrieveIDResult.getInt(2));
							tmpID = 3;
						}
						stmt.executeUpdate("UPDATE inventoryItemAllocation SET itemID = " + tmpID + " WHERE allocationID = " + itemResult.getInt(2));
						if(itemResult.getInt(1) != 1)
								toDeleteItemList.add(itemResult.getInt(1));
						continue;
					}
					
					this.saveEquipment(addedItemList, toDeleteItemList, itemResult, currentID, currentItem);
				}
				
				//update items in equipment
				itemResult.absolute(10);				//moves the cursor to row 10, one row before equipList starts
				for(int j = 0; j < currentEquipmentList.length; j++)
				{
					ItemModel currentItem = currentEquipmentList[j];
					int currentID = currentItem.getItemID();
					itemResult.next();
					this.saveEquipment(addedItemList, toDeleteItemList, itemResult, currentID, currentItem);
				}
				
	
				//validate items to delete from DB
				for(int i = 0; i < addedItemList.size(); i++)
					for(int j = 0; j < toDeleteItemList.size(); j++)
						if(addedItemList.get(i) == toDeleteItemList.get(j))
						{
							toDeleteItemList.remove(j);
							break;
						}
	
				//delete unused items from DB
				for(int i = 0; i < toDeleteItemList.size(); i++)
					stmt.executeUpdate("DELETE FROM item WHERE itemID = " + toDeleteItemList.get(i));
				
				this.dbConnection.commit();				//end transaction
			}
		}
		catch(SQLException sqlE)
		{
			try
			{
				this.dbConnection.rollback();					//onException: attempt rollback
			}
			catch(SQLException embSqlE)
			{
				System.err.println("Fehler in DBController.savePlayerData()\nrollback failed");
				embSqlE.printStackTrace();
				//TODO error: rollback failed
			}
			System.err.println("Fehler in DBController.savePlayerData()\nSQLException");
			sqlE.printStackTrace();
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
				System.err.println("Fehler in DBController.savePlayerData()\nsetAutocommit(true) failed -> data integrity cannot be guaranteed");
				sqlE.printStackTrace();
				//TODO handle exception: setAutocommit(true) failed -> data integrity cannot be guaranteed
			}
		}
	}
	
	/**
	 * verifies if the given username is not already taken</br>
	 * only called by createNewUser() thus no connection.close() needed here
	 * @author Staufenberg, Thomas, 5820359
	 * @param paramUsername username entered in the register panel
	 * @return {true|false}
	 * */
	private boolean isValidUsername(String paramUsername)
	{
		try(Statement stmt = this.dbConnection.createStatement(); ResultSet userResult = stmt.executeQuery("SELECT username FROM registeredUser WHERE username = '" + paramUsername + "'"))
		{
			if(!(userResult.next()))
				return true;
		}
		catch(SQLException sqlE)
		{
			System.err.println("Fehler in DBController.isValidUsername()\nSQLException");
			sqlE.printStackTrace();
			//TODO handle exception
		}
		return false;
	}
	
	/**
	 * invokes username verification</br>
	 * creates data sets for the given user in the following relations:</br>
	 * -registeredUser</br>-saveGame</br>-statistics</br>-inventory</br>-inventoryItemAllocation</br>-playerAllocation
	 * @author Staufenberg, Thomas, 5820359
	 * @param paramUsername username entered in the register panel
	 * @param paramPassword password entered in the register panel
	 * @return Message
	 * */
	private boolean createNewUser(String paramUsername, String paramPassword)
	{
		this.establishConnection();
		
		int inventoryID = -1, saveGameID = -1, statisticsID = -1;
		
		if(isValidUsername(paramUsername))
		{
			try(Statement stmt = this.dbConnection.createStatement())
			{
				this.dbConnection.setAutoCommit(false);			//begin transaction
				
				stmt.executeUpdate("INSERT INTO registeredUser VALUES('" + paramUsername + "' ,'" + paramPassword + "')");
				stmt.executeUpdate("INSERT INTO saveGame VALUES()");
				stmt.executeUpdate("INSERT INTO statistics VALUES()");
				stmt.executeUpdate("INSERT INTO inventory VALUES()");
				
				
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
				
				try(PreparedStatement pstmt = this.dbConnection.prepareStatement("INSERT INTO inventoryItemAllocation(inventoryID, slotID, itemID) VALUES(" + inventoryID + ", ?, 1)"))
				{
					for(int i = 1; i <= 15; i++)
					{
						pstmt.setInt(1, i);
						pstmt.executeUpdate();
					}
				}
				
				stmt.executeUpdate("INSERT INTO playerAllocation(username, playerID, inventoryID, saveGameID, statisticsID) VALUES('" + paramUsername + "', 1, " + inventoryID + ", " + saveGameID + ", " + statisticsID +")");
				
				this.dbConnection.commit();							//end transaction
			}
			catch(SQLException sqlE)
			{
				try
				{
					this.dbConnection.rollback();					//onException: attempt rollback
				}
				catch(SQLException embSqlE)
				{
					System.err.println("Fehler in DBController.createNewUser()\nrollback failed");
					embSqlE.printStackTrace();
					//TODO return error: rollback failed
				}
				System.err.println("Fehler in DBController.createNewUser()\nsqlException");
				sqlE.printStackTrace();
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
					System.err.println("Fehler in DBController.createNewUser()\nsetAutocommit(true) failed -> data integrity cannot be guaranteed");
					sqlE.printStackTrace();
					//TODO handle exception: setAutocommit(true) failed -> data integrity cannot be guaranteed
				}
			}
			return true;
		}
		else
			return false;
	}
	
	/**
	 * creates a data set in the relation 'player'</br>links the playerID to 'playerAllocation' via username
	 * @param paramUsername username of the considered user
	 * @param paramPlayerCharakter PlayerCharacter object of the associated user
	 * @author Staufenberg, Thomas, 5820359
	 * @return Message
	 * */
	private boolean linkCharacterToUser(String paramUsername, PlayerCharacter paramPlayerCharakter)
	{
		this.establishConnection();
		
		try (Statement stmt = this.dbConnection.createStatement(); ResultSet consitencyCheckResult = stmt.executeQuery("SELECT playerID FROM playerAllocation WHERE username = '" + paramUsername + "'"))
		{
			consitencyCheckResult.next();
			if (consitencyCheckResult.getInt(1) == 1)
			{
				this.dbConnection.setAutoCommit(false);				// begin transaction

				stmt.executeUpdate("INSERT INTO player(charImg, charName, hitpoints, attackValue, defenseValue) VALUES('" + paramPlayerCharakter.getImagePath() + "', '" + paramPlayerCharakter.getCharacterName() + "', " + paramPlayerCharakter.getLife() + ", " + paramPlayerCharakter.getAttack() + ", " + paramPlayerCharakter.getDefense() + ")");

				try (ResultSet playerIDResult = stmt.executeQuery("SELECT max(playerID) FROM player"))
				{
					playerIDResult.next();
					stmt.executeUpdate("UPDATE playerAllocation SET playerID = " + playerIDResult.getInt(1) + " WHERE username = '" + paramUsername + "'");
				}
				this.dbConnection.commit();							// end transaction
				return true;
			}
		}
		catch (SQLException sqlE)
		{
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
}
