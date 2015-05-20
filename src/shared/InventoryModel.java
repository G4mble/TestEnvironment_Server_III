package shared;

import java.util.ArrayList;

import shared.item.ConsumableModel;
import shared.item.EquipmentModel;
import shared.item.GoldStack;
import shared.item.ItemModel;
import shared.item.Weapon;

/**
 * model class for player inventory, stores all inventory related data
 * @author Staufenberg, Thomas, 5820359
 * */
public class InventoryModel
{
	private int goldCount, armorPartsCount;				//armorParts are used as crafting material, can be gained by salvaging equipment
	private final int INVENTORY_SIZE = 10;
	private ArrayList<ItemModel> inventoryContentList;
	private EquipmentModel[] equipmentList;							//equipSlotIDs: weaponHand: 0 ; shieldHand: 1 ; helmet: 2 ; chest: 3 ; boots: 4
	
	/**
	 * stores given parameters in global variables
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public InventoryModel(int paramGoldCount, int paramArmorPartsCount, ArrayList<ItemModel> paramInventoryContentList, EquipmentModel[] paramEquipmentList)
	{
		this.goldCount = paramGoldCount;
		this.armorPartsCount = paramArmorPartsCount;
		this.inventoryContentList = paramInventoryContentList;
		this.equipmentList = paramEquipmentList;
	}
	
	/**
	 * sets default values and calls second constructor
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public InventoryModel()
	{
		//TODO set final values: gold, maybe default equipment?
		this(1234, 0, new ArrayList<ItemModel>(), new EquipmentModel[5]);
	}
	
	
	/**
	 * tries to add given item to the inventoryContentList
	 * @return true: item successfully added</br>false: inventory full
	 * @author Staufenberg, Thomas, 5820359
	 * @param paramItem item to be added to the inventory
	 * */
	public boolean addItemToInventory(ItemModel paramItem)
	{
		if(paramItem instanceof ConsumableModel)
			for(int i = 0; i < this.inventoryContentList.size(); i++)
			{
				ConsumableModel currentItem = (ConsumableModel) this.inventoryContentList.get(i);
				if(currentItem.getItemID() == paramItem.getItemID())
					currentItem.increaseStackSize(((ConsumableModel) paramItem).getStackSize());
			}
		else if(paramItem instanceof GoldStack)
			this.modifyGoldCount(paramItem.getItemGoldValue());
		else if(this.inventoryContentList.size() < INVENTORY_SIZE)
			this.inventoryContentList.add(paramItem);
		else
			return false;
		return true;
	}
	
	
	/**
	 * removes given item from the inventory if existing
	 * @param paramItem the item to be removed
	 * @return true: item removed from the inventory</br>false: item not in inventory
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public boolean removeItemFromInventory(ItemModel paramItem)
	{
		return this.inventoryContentList.remove(paramItem);
	}
	
	/**
	 * equips the given item to the player charakter, removes the equipped item from the inventory</br>
	 * adds the previously equipped item to the inventory if not null
	 * @param paramItem the EquipmentModel to be equipped
	 * @param paramSlotID ID of the slot where the item is to be equipped
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void equipItem(EquipmentModel paramItem)
	{
		EquipmentModel previousItem = this.equipmentList[paramItem.getEquipSlotID()];
		this.equipmentList[paramItem.getEquipSlotID()] = paramItem;
		this.removeItemFromInventory(paramItem);
		if(previousItem != null)
			this.addItemToInventory(previousItem);
	}
	
	/**
	 * tries to add the given Item to the inventory</br>
	 * removes the Item from the equipment if the previous operation returned true</br>
	 * if Item is a Weapon && Weapon.isTwoHand == true -> the weapon is also removed from the shieldHand: slot 1
	 * @param paramItem the Item to be removed
	 * @param paramAddToInventory wheter the item should be added to the inventory (true) or not (false)
	 * @return true: Item successfully removed and added to the inventory</br>false: inventory full
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public boolean removeItemFromEquip(EquipmentModel paramItem, boolean paramAddToInventory)
	{
		if(paramAddToInventory)
			if(!(this.addItemToInventory(paramItem)))
				return false;
		
		this.equipmentList[paramItem.getEquipSlotID()] = null;
		if((paramItem instanceof Weapon) && (((Weapon) paramItem).isTwoHand()))
			this.equipmentList[paramItem.getEquipSlotID() + 1] = null;
		return true;
	}
	
	/**
	 * salvages the given Item, this results in the Item being destroyed</br>
	 * adds the armorPartsRevenue of the salvaged Item to the players armorPartsCount
	 * @param paramItem the Item to be salvaged
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void salvageEquipment(EquipmentModel paramItem)
	{
		if(!(this.removeItemFromInventory(paramItem)))
		{
			this.removeItemFromEquip(paramItem, false);
		}
		this.modifyArmorPartsCount(paramItem.getArmorPartsRevenue());
	}
	
	/**
	 * sells the given Item</br>adds the itemGoldValue of the given Item to the players goldCount
	 * @param paramItem the Item to be sold
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void sellItem(ItemModel paramItem)
	{
		if(!(this.removeItemFromInventory(paramItem)))
		{
			this.removeItemFromEquip((EquipmentModel)paramItem, false);
		}
		this.modifyGoldCount(paramItem.getItemGoldValue());
	}
	
	/**
	 * increases/decreases the goldCound by the given value
	 * @author Staufenberg, Thomas, 5820359
	 * @param paramModificator gold amount to be added/subtracted
	 * */
	public void modifyGoldCount(int paramModificator)
	{
		this.goldCount += paramModificator;
	}
	
	/**
	 * increases/decreases the armorPartsCount by the given value
	 * @author Staufenberg, Thomas, 5820359
	 * @param paramModificator amount of armorParts to be added/subtracted
	 * */
	public void modifyArmorPartsCount(int paramModificator)
	{
		this.armorPartsCount += paramModificator;
	}
	
	/**
	 * @return current goldCount
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getGoldCount()
	{
		return this.goldCount;
	}
	
	/**
	 * @return current armorPartsCount
	 * @authro Staufenberg, Thomas, 5820359
	 * */
	public int getArmorPartsCount()
	{
		return this.armorPartsCount;
	}
	
	/**
	 * @return current inventoryContentList
	 * @author Staufenberg, Thomas, 5820359 
	 * */
	public ArrayList<ItemModel> getInventoryContentList()
	{
		return this.inventoryContentList;
	}
	
	/**
	 * @return current equipmentList
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public EquipmentModel[] getEquipmentList()
	{
		return this.equipmentList;
	}
	
	/**
	 * @return maximum amount of inventory slots available
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getInventorySize()
	{
		return this.INVENTORY_SIZE;
	}
	
	/**
	 * ovewrites the current equipmentList with the given one
	 * @param paramEquipmentList the equipmentList to be set
	 * @author Staufenberg, Thomas 5820359
	 * */
	public void setEquipmentList(EquipmentModel[] paramEquipmentList)
	{
		this.equipmentList = paramEquipmentList;
	}
}
