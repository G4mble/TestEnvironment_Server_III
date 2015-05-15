package shared.character;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/**
 * abstract datamodel which provides the basic attributes for all characters in the game, e.g. monster and players
 * @author Heusser, Caspar
 *
 */
public abstract class Character {
	private int characterId;
	private String characterName;
	private int posX;
	private int posY;
	private boolean attackable; 					//fuer evtuelle NPC
	private Image characterImage;					//Textur fuer den Spielercharacter
	private String imagePath;						//Dateipfad der Textur (wird fuer die Datenbankspeicherung benoetigt)
	
	/**
	 * constructor of "Character
	 * @author Heusser, Caspar
	 * @param characterId
	 * @param characterName
	 * @param posX
	 * @param posY
	 * @param attackable
	 * @param imagePath
	 */
	public Character(int characterId,String characterName, int posX, int posY, boolean attackable, String imagePath){
		this.characterId = characterId;
		this.characterName = characterName;
		this.posX=posX;
		this.posY=posY;
		this.attackable=attackable;
		this.imagePath = imagePath;
		try
		{
			this.characterImage= ImageIO.read(new File(imagePath));
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
		}
	}
	
	/**
	 * @author Heusser, Caspar
	 * @return the characterId
	 */
	public int getCharacterId() {
		return characterId;
	}
	/**
	 * @author Heusser, Caspar
	 * @param characterId the characterId to set
	 */
	public void setCharacterId(int characterId) {
		this.characterId = characterId;
	}
	/**
	 * @author Heusser, Caspar
	 * @return the characterName
	 */
	public String getCharacterName() {
		return characterName;
	}
	/**
	 * @author Heusser, Caspar
	 * @param characterName the characterName to set
	 */
	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}
	/**
	 * @author Heusser, Caspar
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}
	/**
	 * @author Heusser, Caspar
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}
	/**
	 * @author Heusser, Caspar
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}
	/**
	 * @author Heusser, Caspar
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}
	/**
	 * @author Heusser, Caspar
	 * @return the attackable
	 */
	public boolean isAttackable() {
		return attackable;
	}
	/**
	 * @author Heusser, Caspar
	 * @param attackable the attackable to set
	 */
	public void setAttackable(boolean attackable) {
		this.attackable = attackable;
	}
	/**
	 * @author Heusser, Caspar
	 * @return the characterImage
	 */
	public Image getCharacterImage() {
		return characterImage;
	}
	/**
	 * @author Heusser, Caspar
	 * @param characterImage the characterImage to set
	 */
	public void setCharacterImage(Image characterImage) {
		this.characterImage = characterImage;
	}
	/**
	 * @author Staufenberg, Thomas
	 * @return texture image path as String
	 * */
	public String getImagePath(){
		return this.imagePath;
	}
}
