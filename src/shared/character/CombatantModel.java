package shared.character;

import javax.swing.JOptionPane;

/**
 * abstract datamodel which extends "Character" and adds special attributes for combatants; e.g. attack, defense
 * @author Heusser, Caspar
 */
public abstract class CombatantModel extends Character {
	private int currentLife;
	private int maximumLife;
	private int attack;
	private int defense;
	private int movementSpeed;
	/**
	 * constructor of CombatantModel
	 * @author Heusser, Caspar
	 * @param maximumLife
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
	public CombatantModel(int maximumLife, int attack, int defense, int movementSpeed,int characterId, String characterName, int posX, int posY, boolean attackable, String imagePath){
		super(characterId, characterName, posX, posY, attackable, imagePath);
		this.currentLife = maximumLife;
		this.maximumLife = maximumLife;
		this.attack = attack;
		this.defense=defense;
		this.movementSpeed= movementSpeed;
		
	}
	/**
	 * @author Heusser, Caspar
	 * @return the life
	 */
	public int getCurrentLife() {
		return this.currentLife;
	}
	/**
	 * @author Heusser, Caspar
	 * @param life the life to set
	 */
	public boolean setCurrentLife(int life) 
	{
		if(life < this.currentLife)
		{
			if(life < 0)
				this.currentLife = 0;
			else
				this.currentLife = life;
			return true;
		}
		else if(this.currentLife < this.maximumLife)
		{
			if(life <= this.maximumLife)
				this.currentLife = life;
			else
				this.currentLife = this.maximumLife;
			return true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Sie haben bereits volle Lebenspunkte!", "Achtung!", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	
	public void setMaximumLife(int maxLife)
	{
		this.maximumLife = maxLife;
	}
	
	public int getMaximumLife()
	{
		return this.maximumLife;
	}
	
	/**
	 * @author Heusser, Caspar
	 * @return the attack
	 */
	public int getAttack() {
		return this.attack;
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
		return this.defense;
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
		return this.movementSpeed;
	}
	/**
	 * @author Heusser, Caspar
	 * @param movementSpeed the movementSpeed to set
	 */
	public void setMovementSpeed(int movementSpeed) {
		this.movementSpeed = movementSpeed;
	}
	
	
}
