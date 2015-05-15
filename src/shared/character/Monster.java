package shared.character;

public class Monster extends CombatantModel {
	private int scaling;
	private int actID;
	private boolean hasKey;
	private boolean isAlive;
	/**
	 * constructor of "Monster"
	 * @author Heusser, Caspar
	 * @param scaling
	 * @param actID
	 * @param life
	 * @param attack
	 * @param defense
	 * @param movementSpeed
	 * @param characterId
	 * @param characterName
	 * @param posX
	 * @param posY
	 * @param imagePath
	 */
	public Monster(int scaling, int actID,int life, int attack, int defense, int movementSpeed, int characterId, String characterName, int posX, int posY,String imagePath ){
		super(life,  attack,  defense, movementSpeed, characterId,  characterName, posX,  posY,  true, imagePath);
		this.hasKey=false;
		this.isAlive=true;
		this.scaling=scaling;
		this.actID=actID;
	}
	/**
	 * @return the scaling
	 */
	public int getScaling() {
		return scaling;
	}
	/**
	 * @param scaling the scaling to set
	 */
	public void setScaling(int scaling) {
		this.scaling = scaling;
	}
	/**
	 * @return the actID
	 */
	public int getActID() {
		return actID;
	}
	/**
	 * @param actID the actID to set
	 */
	public void setActID(int actID) {
		this.actID = actID;
	}
	/**
	 * @return the hasKey
	 */
	public boolean isHasKey() {
		return hasKey;
	}
	/**
	 * @param hasKey the hasKey to set
	 */
	public void setHasKey(boolean hasKey) {
		this.hasKey = hasKey;
	}
	/**
	 * @return the isAlive
	 */
	public boolean isAlive() {
		return isAlive;
	}
	/**
	 * @param isAlive the isAlive to set
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
}

