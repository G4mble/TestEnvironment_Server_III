package gui.view;

import gui.controller.ShowcaseController;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JSeparator;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import shared.InventoryModel;
import shared.character.PlayerCharacter;
import shared.item.ConsumableModel;
import shared.item.EquipmentModel;
import shared.item.ItemModel;
import shared.statistics.StatisticsModel;

/**
 * displays the showcase view
 * @author Staufenberg, Thomas, 5820359
 * */
public class ShowcaseView extends JFrame
{
	private static final long serialVersionUID = -1847497075140092998L;
	private JTextField txtFExperience;
	private JTextField txtFLevel;
	private JTextField txtFName;
	private PlayerCharacter activeCharacter;
	private String charName;
	private int level, experience, goldCount, armorPartsCount;
	private ArrayList<ItemModel> inventoryContentList;
	private EquipmentModel[] equipmentList;
	private InventoryModel inventory;
	private StatisticsModel statistics;
	private Object[] inventoryTableColumnNames = {"Name", "Anzahl", "Level", "Position", "Angriff", "Verteidigung", "Leben", "Goldwert", "Ruestungsteile"};
	private Object[][] inventoryTableRowData = new Object[10][9];
	private JTable inventoryTable;
	

	/**
	 * creates all window components
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public ShowcaseView(ShowcaseController paramShowcaseController, String paramUsername, PlayerCharacter paramPlayer)
	{
		this.activeCharacter = paramPlayer;
		this.initiateShowcase();
		
		getContentPane().setLayout(null);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.setBounds(691, 514, 81, 32);
		btnExit.addActionListener(paramShowcaseController);
		btnExit.setActionCommand("exit");
		getContentPane().add(btnExit);
		
		JLabel lblCharakter = new JLabel("Charakter:");
		lblCharakter.setBounds(10, 11, 70, 14);
		getContentPane().add(lblCharakter);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(34, 36, 46, 14);
		getContentPane().add(lblName);
		
		JLabel lblLevel = new JLabel("Level:");
		lblLevel.setBounds(34, 61, 46, 14);
		getContentPane().add(lblLevel);
		
		JLabel lblErfahrung = new JLabel("Erfahrung:");
		lblErfahrung.setBounds(34, 86, 67, 14);
		getContentPane().add(lblErfahrung);
		
		txtFExperience = new JTextField();
		txtFExperience.setEditable(false);
		txtFExperience.setBounds(111, 83, 86, 20);
		getContentPane().add(txtFExperience);
		txtFExperience.setColumns(10);
		
		txtFLevel = new JTextField();
		txtFLevel.setEditable(false);
		txtFLevel.setBounds(111, 58, 86, 20);
		getContentPane().add(txtFLevel);
		txtFLevel.setColumns(10);
		
		txtFName = new JTextField();
		txtFName.setEditable(false);
		txtFName.setBounds(111, 33, 86, 20);
		getContentPane().add(txtFName);
		txtFName.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 25, 200, 7);
		getContentPane().add(separator);
		
		this.inventoryTable = new JTable(this.inventoryTableRowData, this.inventoryTableColumnNames);
		this.inventoryTable.setEnabled(false);
		this.inventoryTable.setBounds(50, 237, 700, 180);
		
		JScrollPane scrollPane = new JScrollPane(this.inventoryTable);
		scrollPane.setBounds(50, 237, 700, 180);
		this.inventoryTable.setFillsViewportHeight(true);
		getContentPane().add(scrollPane);
		
		this.setSize(new Dimension(788, 585));
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Showcase");
		this.setVisible(true);
	}
	
	private void initiateShowcase()
	{
		this.charName = this.activeCharacter.getCharacterName();
		this.level = this.activeCharacter.getLevel();
		this.experience = this.activeCharacter.getExperiencePoints();
		this.inventory = this.activeCharacter.getInventory();
		this.statistics = this.activeCharacter.getStatistics();
		this.goldCount = this.inventory.getGoldCount();
		this.armorPartsCount = this.inventory.getArmorPartsCount();
		this.inventoryContentList = this.inventory.getInventoryContentList();
		this.equipmentList = this.inventory.getEquipmentList();	
		
		for(int i = 0; i < 10; i++)
		{
			try
			{
				ItemModel currentItem = this.inventoryContentList.get(i);
				this.inventoryTableRowData[i][0] = currentItem.getItemName();
				if(currentItem instanceof ConsumableModel)
				{
					this.inventoryTableRowData[i][1] = ((ConsumableModel) currentItem).getStackSize();
					this.inventoryTableRowData[i][2] = "-";
					this.inventoryTableRowData[i][3] = "-";
					this.inventoryTableRowData[i][4] = "-";
					this.inventoryTableRowData[i][5] = "-";
					this.inventoryTableRowData[i][6] = "-";
					this.inventoryTableRowData[i][8] = "-";
				}
				else
				{
					EquipmentModel currentEquip = (EquipmentModel) currentItem;
					this.inventoryTableRowData[i][1] = "-";
					this.inventoryTableRowData[i][2] = currentEquip.getLevelRestriction();
					this.inventoryTableRowData[i][3] = currentEquip.getEquipSlotID();
					this.inventoryTableRowData[i][4] = currentEquip.getAttackValue();
					this.inventoryTableRowData[i][5] = currentEquip.getDefenseValue();
					this.inventoryTableRowData[i][6] = currentEquip.getHpValue();
					this.inventoryTableRowData[i][8] = currentEquip.getArmorPartsRevenue();
				}
				this.inventoryTableRowData[i][7] = currentItem.getItemGoldValue();
			}
			catch(IndexOutOfBoundsException iOoBE)
			{
				for(int k = 0; k < 9; k++)
					this.inventoryTableRowData[i][k] = "-";
			}
		}
	}
}
