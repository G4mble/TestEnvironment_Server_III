package shared.character;

/**
 * abstract datamodel which extends "Character" and adds special attributes for combatants; e.g. attack, defense
 * @author Heusser, Caspar
 */
public abstract class CombatantModel extends Character {
	private int life;
	private int attack;
	private int defense;
	private int movementSpeed;
	/**
	 * constructor of CombatantModel
	 * @author Heusser, Caspar
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
	public CombatantModel(int life, int attack, int defense, int movementSpeed,int characterId, String characterName, int posX, int posY, boolean attackable, String imagePath){
		super(characterId, characterName, posX, posY, attackable, imagePath);
		this.life = life;
		this.attack = attack;
		this.defense=defense;
		this.movementSpeed= movementSpeed;
		
	}
	/**
	 * @author Heusser, Caspar
	 * @return the life
	 */
	public int getLife() {
		return life;
	}
	/**
	 * @author Heusser, Caspar
	 * @param life the life to set
	 */
	public void setLife(int life) {
		this.life = life;
	}
	/**
	 * @author Heusser, Caspar
	 * @return the attack
	 */
	public int getAttack() {
		return attack;
	}
	/**
	 * @author Heusser, Caspar
	 * @param attack the attack to set
	 */
	public void setAttack(int attack) {
		this.attack = attack;
	}
	/**
	 * @author Heusser, Caspar
	 * @return the defense
	 */
	public int getDefense() {
		return defense;
	}
	/**
	 * @author Heusser, Caspar
	 * @param defense the defense to set
	 */
	public void setDefense(int defense) {
		this.defense = defense;
	}
	/**
	 * @author Heusser, Caspar
	 * @return the movementSpeed
	 */
	public int getMovementSpeed() {
		return movementSpeed;
	}
	/**
	 * @author Heusser, Caspar
	 * @param movementSpeed the movementSpeed to set
	 */
	public void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	
}
