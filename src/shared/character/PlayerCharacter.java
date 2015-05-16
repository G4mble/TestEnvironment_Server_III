package shared.character;

import shared.InventoryModel;
import shared.statistics.StatisticsModel;

/**
 * datamodel for the playercharacters which provides additional specific attribute; e.g. level, sightdistance
 * @author Heusser, Caspar
 *
 */
public class PlayerCharacter extends CombatantModel{
	private int level;
	private int experiencePoints;
	//TODO abilities : Array of Ability
	private int clientId;
	private int sightDistance = 12345;			//TODO set final value for sight distance
	private InventoryModel inventory;
	private StatisticsModel statistics;
	
	/**
	 *  contructor of "PlayerCharacter" for new characters
	 *  @author Heusser, Caspar
	 */
	public PlayerCharacter(int clientId,int characterId, String characterName, String imagePath){
		
		//PlayerCharacter(clientId,1,0,10,50,10,10,10,characterId,characterName,0,0,true,image);
		this(clientId,1,0,10,10,10,characterId,characterName,0,0,true,imagePath, new InventoryModel(), new StatisticsModel());
		//TODO abilities
		//TODO inventory
	}
	/**
	 * contructor of "PlayerCharacter"; database
	 * @author Heusser, Caspar
	 * @param clientId
	 * @param level
	 * @param experiencePoints
	 * @param sightDistance
	 * @param life
	 * @param attack
	 * @param defense
	 * @param movementSpeed
	 * @param characterId
	 * @param characterName
	 * @param posX
	 * @param posY
	 * @param attackable
	 * @param imagePath
	 */
	public PlayerCharacter(int clientId, int level, int experiencePoints, int life, int attack, int defense, int characterId, String characterName, int posX, int posY,boolean attackable, String imagePath, InventoryModel inventory, StatisticsModel statistics)
	{
		//TODO set final values for movement speed "1234"
		super( life,  attack,  defense,  1234, characterId,  characterName,  posX,  posY,  attackable, imagePath);
		this.clientId=clientId;
		this.experiencePoints=experiencePoints;
		this.level=level;
		this.inventory = inventory;
		this.statistics = statistics;
	}
	private int act;
	/**
	 * @author Heusser, Caspar
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * @author Heusser, Caspar
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	/**
	 * @author Heusser, Caspar
	 * @return the experiencePoints
	 */
	public int getExperiencePoints() {
		return experiencePoints;
	}
	/**
	 * @author Heusser, Caspar
	 * @param experiencePoints the experiencePoints to set
	 */
	public void setExperiencePoints(int experiencePoints) {
		this.experiencePoints = experiencePoints;
	}
	/**
	 * @author Heusser, Caspar
	 * @return the clientId
	 */
	public int getClientId() {
		return clientId;
	}
	/**
	 * @author Heusser, Caspar
	 * @param clientId the clientId to set
	 */
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	/**
	 * @author Heusser, Caspar
	 * @return the sightDistance
	 */
	public int getSightDistance() {
		return sightDistance;
	}
	/**
	 * @author Heusser, Caspar
	 * @param sightDistance the sightDistance to set
	 */
	public void setSightDistance(int sightDistance) {
		this.sightDistance = sightDistance;
	}
	
	/**
	 * @author Heusser, Caspar
	 * @return the act
	 */
	public int getAct() {
		return act;
	}
	/**
	 * @author Heusser, Caspar
	 * @param act the act to set
	 */
	public void setAct(int act) {
		this.act = act;
	}
	
	/**
	 * @author Heusser, Caspar
	 * @return the inventory
	 * */
	public InventoryModel getInventory()
	{
		return this.inventory;
	}
	
	/**
	 * @author Heusser, Caspar
	 * @return the statistics
	 * */
	public StatisticsModel getStatistics()
	{
		return this.statistics;
	}
	
}
