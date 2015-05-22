package gui.view;

import gui.controller.ShowcaseController;

import javax.swing.JFrame;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import shared.InventoryModel;
import shared.character.PlayerCharacter;
import shared.item.ConsumableModel;
import shared.item.EquipmentModel;
import shared.item.ItemModel;
import shared.statistics.StatisticsModel;

import javax.swing.JRadioButton;
import javax.swing.SwingConstants;


import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JList;

/**
 * displays the showcase view
 * @author Staufenberg, Thomas, 5820359
 * */
public class ShowcaseView extends JFrame
{
	private static final long serialVersionUID = -1847497075140092998L;
	private JTextField txtFExperience, txtFLevel, txtFName, txtFGold, txtFArmorParts, txtfKills, txtfDeaths, txtfTimePlayed, txtfGoldEarned, txtfDef, txtfAtk, txtfLife;
	private PlayerCharacter activeCharacter;
	private String charName;
	private int level, experience, goldCount, armorPartsCount, killCount, deathCount, timePlayed, goldEarned;
	private ArrayList<ItemModel> inventoryContentList;
	private EquipmentModel[] equipmentList;
	private InventoryModel inventory;
	private StatisticsModel statistics;
	private Object[] inventoryTableColumnNames = {"Name", "Anzahl", "Level", "Position", "Angriff", "Verteidigung", "Leben", "Goldwert", "Ruestungsteile"}, equipmentTableColumnNames = {"SlotID", "Name", "Level", "Position", "Angriff", "Verteidigung", "Lebel", "Goldwert", "Ruestungsteile"};
	private Object[][] inventoryTableRowData = new Object[10][9], equipmentTableRowData = new Object[5][9];
	private JTable inventoryTable, equipmentTable;
	private ButtonGroup inventoryRadioBtnGroup, equipmentRadioBtnGroup;
	private JComboBox<String> combEquip, combConsumable;	
	private DefaultListModel<String> globalInventoryListModel;
	private JList<String> globalInventoryJList;

	/**
	 * creates all window components
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public ShowcaseView(ShowcaseController paramShowcaseController, String paramUsername, PlayerCharacter paramPlayer)
	{
		this.activeCharacter = paramPlayer;
		this.initiateShowcase();
		
		getContentPane().setLayout(null);

//----------JButton----------

		JButton btnLogout = new JButton("Abmelden");
		btnLogout.setBounds(917, 538, 96, 27);
		btnLogout.addActionListener(paramShowcaseController);
		btnLogout.setActionCommand("logout");
		getContentPane().add(btnLogout);
		
		JButton btnDropItem = new JButton("Ablegen");
		btnDropItem.setBounds(469, 379, 100, 23);
		btnDropItem.setActionCommand("inventory_dropItem");
		btnDropItem.addActionListener(paramShowcaseController);
		getContentPane().add(btnDropItem);
		
		JButton btnEquipItem = new JButton("Ausruesten");
		btnEquipItem.setBounds(579, 379, 100, 23);
		btnEquipItem.setActionCommand("inventory_equipItem");
		btnEquipItem.addActionListener(paramShowcaseController);
		getContentPane().add(btnEquipItem);
		
		JButton btnConsumeItem = new JButton("Benutzen");
		btnConsumeItem.setBounds(689, 379, 100, 23);
		btnConsumeItem.setActionCommand("inventory_consumeItem");
		btnConsumeItem.addActionListener(paramShowcaseController);
		getContentPane().add(btnConsumeItem);
		
		JButton btnSellItem = new JButton("Verkaufen");
		btnSellItem.setBounds(469, 408, 100, 23);
		btnSellItem.setActionCommand("inventory_sellItem");
		btnSellItem.addActionListener(paramShowcaseController);
		getContentPane().add(btnSellItem);
		
		JButton btnSalvageItem = new JButton("Zerlegen");
		btnSalvageItem.setBounds(579, 408, 100, 23);
		btnSalvageItem.setActionCommand("inventory_salvageItem");
		btnSalvageItem.addActionListener(paramShowcaseController);
		getContentPane().add(btnSalvageItem);
		
		JButton btnAblegen = new JButton("Ablegen");
		btnAblegen.setBounds(558, 594, 100, 23);
		btnAblegen.setActionCommand("equipment_dropItem");
		btnAblegen.addActionListener(paramShowcaseController);
		getContentPane().add(btnAblegen);
		
		JButton btnVerkaufen = new JButton("Verkaufen");
		btnVerkaufen.setBounds(558, 623, 100, 23);
		btnVerkaufen.setActionCommand("equipment_sellItem");
		btnVerkaufen.addActionListener(paramShowcaseController);
		getContentPane().add(btnVerkaufen);
		
		JButton btnAbruesten = new JButton("Abruesten");
		btnAbruesten.setBounds(668, 594, 100, 23);
		btnAbruesten.setActionCommand("equipment_removeItem");
		btnAbruesten.addActionListener(paramShowcaseController);
		getContentPane().add(btnAbruesten);
		
		JButton btnZerlegen = new JButton("Zerlegen");
		btnZerlegen.setBounds(668, 623, 100, 23);
		btnZerlegen.setActionCommand("equipment_salvageItem");
		btnZerlegen.addActionListener(paramShowcaseController);
		getContentPane().add(btnZerlegen);
		
		JButton btnGenerateEquip = new JButton("Erzeugen");
		btnGenerateEquip.setBounds(949, 36, 108, 22);
		btnGenerateEquip.addActionListener(paramShowcaseController);
		btnGenerateEquip.setActionCommand("generate_equip");
		getContentPane().add(btnGenerateEquip);

		JButton btnGenerateConsumable = new JButton("Erzeugen");
		btnGenerateConsumable.setBounds(949, 63, 108, 22);
		btnGenerateConsumable.addActionListener(paramShowcaseController);
		btnGenerateConsumable.setActionCommand("generate_consumable");
		getContentPane().add(btnGenerateConsumable);

		JButton btnGenerateGold = new JButton("Erzeugen");
		btnGenerateGold.setBounds(949, 90, 108, 22);
		btnGenerateGold.addActionListener(paramShowcaseController);
		btnGenerateGold.setActionCommand("generate_gold");
		getContentPane().add(btnGenerateGold);
		
		JButton btnHighscore = new JButton("Highscore");
		btnHighscore.setBounds(551, 100, 96, 23);
		btnHighscore.addActionListener(paramShowcaseController);
		btnHighscore.setActionCommand("highscore");
		getContentPane().add(btnHighscore);		
		
		JButton btnPickupFromGlobal = new JButton("Aufnehmen");
		btnPickupFromGlobal.setBounds(917, 414, 108, 23);
		btnPickupFromGlobal.addActionListener(paramShowcaseController);
		btnPickupFromGlobal.setActionCommand("pickup");
		getContentPane().add(btnPickupFromGlobal);
		
		JButton btnExit = new JButton("Beenden");
		btnExit.setBounds(917, 580, 96, 27);
		btnExit.addActionListener(paramShowcaseController);
		btnExit.setActionCommand("exit");
		getContentPane().add(btnExit);
		
		JButton btnSave = new JButton("Speichern");
		btnSave.setBounds(917, 500, 96, 27);
		btnSave.addActionListener(paramShowcaseController);
		btnSave.setActionCommand("save");
		getContentPane().add(btnSave);

//----------JTextField----------
		
		txtFExperience = new JTextField();
		txtFExperience.setEditable(false);
		txtFExperience.setBounds(88, 86, 86, 20);
		txtFExperience.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtFExperience.setText(Integer.toString(this.experience));
		getContentPane().add(txtFExperience);
		
		txtFLevel = new JTextField();
		txtFLevel.setEditable(false);
		txtFLevel.setBounds(88, 61, 86, 20);
		txtFLevel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtFLevel.setText(Integer.toString(this.level));
		getContentPane().add(txtFLevel);
		
		txtFName = new JTextField();
		txtFName.setEditable(false);
		txtFName.setBounds(88, 36, 86, 20);
		txtFName.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtFName.setText(this.charName);
		getContentPane().add(txtFName);
		
		txtFGold = new JTextField();
		txtFGold.setEditable(false);
		txtFGold.setBounds(257, 380, 86, 20);
		txtFGold.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtFGold.setText(Integer.toString(this.goldCount));
		getContentPane().add(txtFGold);
		
		txtFArmorParts = new JTextField();
		txtFArmorParts.setEditable(false);
		txtFArmorParts.setBounds(257, 405, 86, 20);
		txtFArmorParts.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtFArmorParts.setText(Integer.toString(this.armorPartsCount));
		getContentPane().add(txtFArmorParts);
		
		txtfKills = new JTextField();
		txtfKills.setEditable(false);
		txtfKills.setBounds(448, 36, 86, 20);
		getContentPane().add(txtfKills);
		txtfKills.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtfKills.setText(Integer.toString(this.killCount));
		txtfKills.setColumns(10);
		
		txtfDeaths = new JTextField();
		txtfDeaths.setEditable(false);
		txtfDeaths.setColumns(10);
		txtfDeaths.setBounds(448, 61, 86, 20);
		txtfDeaths.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtfDeaths.setText(Integer.toString(this.deathCount));
		getContentPane().add(txtfDeaths);
		
		txtfTimePlayed = new JTextField();
		txtfTimePlayed.setEditable(false);
		txtfTimePlayed.setColumns(10);
		txtfTimePlayed.setBounds(448, 86, 86, 20);
		txtfTimePlayed.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtfTimePlayed.setText(Integer.toString(this.timePlayed));
		getContentPane().add(txtfTimePlayed);
		
		txtfGoldEarned = new JTextField();
		txtfGoldEarned.setEditable(false);
		txtfGoldEarned.setBounds(551, 61, 96, 20);
		txtfGoldEarned.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtfGoldEarned.setText(Integer.toString(this.goldEarned));
		getContentPane().add(txtfGoldEarned);
		
		txtfDef = new JTextField();
		txtfDef.setText((String) null);
		txtfDef.setEditable(false);
		txtfDef.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtfDef.setBounds(265, 86, 86, 20);
		getContentPane().add(txtfDef);
		
		txtfAtk = new JTextField();
		txtfAtk.setText((String) null);
		txtfAtk.setEditable(false);
		txtfAtk.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtfAtk.setBounds(265, 61, 86, 20);
		getContentPane().add(txtfAtk);
		
		txtfLife = new JTextField();
		txtfLife.setText((String) null);
		txtfLife.setEditable(false);
		txtfLife.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		txtfLife.setBounds(265, 36, 86, 20);
		getContentPane().add(txtfLife);
		
//----------JRadioButton----------
		
		JRadioButton rdnBtnOne = new JRadioButton("");
		rdnBtnOne.setBounds(815, 166, 21, 25);
		rdnBtnOne.setName("0");
		getContentPane().add(rdnBtnOne);
		
		JRadioButton rdnBtnTwo = new JRadioButton("");
		rdnBtnTwo.setBounds(815, 186, 21, 25);
		rdnBtnTwo.setName("1");
		getContentPane().add(rdnBtnTwo);
		
		JRadioButton rdnBtnThree = new JRadioButton("");
		rdnBtnThree.setBounds(815, 206, 21, 25);
		rdnBtnThree.setName("2");
		getContentPane().add(rdnBtnThree);
		
		JRadioButton rdnBtnFour = new JRadioButton("");
		rdnBtnFour.setBounds(815, 226, 21, 25);
		rdnBtnFour.setName("3");
		getContentPane().add(rdnBtnFour);
		
		JRadioButton rdnBtnFive = new JRadioButton("");
		rdnBtnFive.setBounds(815, 246, 21, 25);
		rdnBtnFive.setName("4");
		getContentPane().add(rdnBtnFive);
		
		JRadioButton rdnBtnSix = new JRadioButton("");
		rdnBtnSix.setBounds(815, 266, 21, 25);
		rdnBtnSix.setName("5");
		getContentPane().add(rdnBtnSix);
		
		JRadioButton rdnBtnSeven = new JRadioButton("");
		rdnBtnSeven.setBounds(815, 286, 21, 25);
		rdnBtnSeven.setName("6");
		getContentPane().add(rdnBtnSeven);
		
		JRadioButton rdnBtnEight = new JRadioButton("");
		rdnBtnEight.setBounds(815, 306, 21, 25);
		rdnBtnEight.setName("7");
		getContentPane().add(rdnBtnEight);
		
		JRadioButton rdnBtnNine = new JRadioButton("");
		rdnBtnNine.setBounds(815, 326, 21, 25);
		rdnBtnNine.setName("8");
		getContentPane().add(rdnBtnNine);
		
		JRadioButton rdnBtnTen = new JRadioButton("");
		rdnBtnTen.setBounds(815, 346, 21, 25);
		rdnBtnTen.setName("9");
		getContentPane().add(rdnBtnTen);
		
		inventoryRadioBtnGroup = new ButtonGroup();
		inventoryRadioBtnGroup.add(rdnBtnOne);
		inventoryRadioBtnGroup.add(rdnBtnTwo);
		inventoryRadioBtnGroup.add(rdnBtnThree);
		inventoryRadioBtnGroup.add(rdnBtnFour);
		inventoryRadioBtnGroup.add(rdnBtnFive);
		inventoryRadioBtnGroup.add(rdnBtnSix);
		inventoryRadioBtnGroup.add(rdnBtnSeven);
		inventoryRadioBtnGroup.add(rdnBtnEight);
		inventoryRadioBtnGroup.add(rdnBtnNine);
		inventoryRadioBtnGroup.add(rdnBtnTen);
		
		JRadioButton eqRdBtnOne = new JRadioButton("");
		eqRdBtnOne.setBounds(815, 480, 21, 25);
		eqRdBtnOne.setName("0");
		getContentPane().add(eqRdBtnOne);
		
		JRadioButton eqRdBtnTwo = new JRadioButton("");
		eqRdBtnTwo.setBounds(815, 500, 21, 25);
		eqRdBtnTwo.setName("1");
		getContentPane().add(eqRdBtnTwo);
		
		JRadioButton eqRdBtnThree = new JRadioButton("");
		eqRdBtnThree.setBounds(815, 520, 21, 25);
		eqRdBtnThree.setName("2");
		getContentPane().add(eqRdBtnThree);
		
		JRadioButton eqRdBtnFour = new JRadioButton("");
		eqRdBtnFour.setBounds(815, 540, 21, 25);
		eqRdBtnFour.setName("3");
		getContentPane().add(eqRdBtnFour);
		
		JRadioButton eqRdBtnFive = new JRadioButton("");
		eqRdBtnFive.setBounds(815, 560, 21, 25);
		eqRdBtnFive.setName("4");
		getContentPane().add(eqRdBtnFive);
		
		equipmentRadioBtnGroup = new ButtonGroup();
		equipmentRadioBtnGroup.add(eqRdBtnOne);
		equipmentRadioBtnGroup.add(eqRdBtnTwo);
		equipmentRadioBtnGroup.add(eqRdBtnThree);
		equipmentRadioBtnGroup.add(eqRdBtnFour);
		equipmentRadioBtnGroup.add(eqRdBtnFive);
		
//----------JComboBox----------
		
		combEquip = new JComboBox<>();
		combEquip.setBounds(792, 36, 137, 22);
		combEquip.addItem("Einhandschwert");
		combEquip.addItem("Zweihandschwert");
		combEquip.addItem("Schild");
		combEquip.addItem("Kopfschutz");
		combEquip.addItem("Brutschutz");
		combEquip.addItem("Stiefel");
		getContentPane().add(combEquip);
		
		combConsumable = new JComboBox<>();
		combConsumable.setBounds(792, 63, 137, 22);
		combConsumable.addItem("Heiltrank");
		combConsumable.addItem("Manatrank");
		getContentPane().add(combConsumable);
		
//----------JSpinner----------
		
		JSpinner spinGoldValue = new JSpinner();
		spinGoldValue.setModel(new SpinnerNumberModel(1, 1, 1000, 1));
		spinGoldValue.setBounds(851, 90, 78, 22);
		getContentPane().add(spinGoldValue);

//----------JList----------
		
		globalInventoryListModel = new DefaultListModel<>();
		globalInventoryJList = new JList<>(globalInventoryListModel);
		globalInventoryJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		getContentPane().add(globalInventoryJList);
		
		JScrollPane globalInventoryScrollPanel = new JScrollPane(globalInventoryJList);
		globalInventoryScrollPanel.setBounds(900, 180, 137, 223);
		getContentPane().add(globalInventoryScrollPanel);
		
//----------JTable----------		
		
		this.initiateJTables();
		
//----------JSeperator----------

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 25, 345, 22);
		getContentPane().add(separator);
			
		JSeparator separator_3 = new JSeparator();
		separator_3.setOrientation(SwingConstants.VERTICAL);
		separator_3.setBounds(861, 136, 21, 512);
		getContentPane().add(separator_3);
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(688, 25, 200, 22);
		getContentPane().add(separator_4);
		
		JSeparator separator_5 = new JSeparator();
		separator_5.setOrientation(SwingConstants.VERTICAL);
		separator_5.setBounds(365, 11, 14, 116);
		getContentPane().add(separator_5);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 449, 841, 14);
		getContentPane().add(separator_2);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 136, 841, 14);
		getContentPane().add(separator_1);
		
		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(378, 25, 288, 22);
		getContentPane().add(separator_6);
		
		JSeparator separator_7 = new JSeparator();
		separator_7.setOrientation(SwingConstants.VERTICAL);
		separator_7.setBounds(676, 11, 14, 116);
		getContentPane().add(separator_7);
		
		JSeparator separator_9 = new JSeparator();
		separator_9.setBounds(877, 449, 166, 22);
		getContentPane().add(separator_9);
		
//----------JLabel----------
		
		JLabel labelAuswahlZwei = new JLabel("Auswahl");
		labelAuswahlZwei.setBounds(802, 460, 54, 14);
		getContentPane().add(labelAuswahlZwei);
		
		JLabel lblInventar = new JLabel("Inventar:");
		lblInventar.setBounds(10, 121, 70, 14);
		getContentPane().add(lblInventar);
		
		JLabel lblCharakter = new JLabel("Charakter:");
		lblCharakter.setBounds(10, 11, 70, 14);
		getContentPane().add(lblCharakter);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(20, 39, 46, 14);
		getContentPane().add(lblName);
		
		JLabel lblLevel = new JLabel("Level:");
		lblLevel.setBounds(20, 64, 46, 14);
		getContentPane().add(lblLevel);
		
		JLabel lblErfahrung = new JLabel("Erfahrung:");
		lblErfahrung.setBounds(20, 89, 67, 14);
		getContentPane().add(lblErfahrung);
		
		JLabel lblEquipment = new JLabel("Equipment:");
		lblEquipment.setBounds(10, 435, 70, 14);
		getContentPane().add(lblEquipment);

		JLabel lblGold = new JLabel("Gold:");
		lblGold.setBounds(164, 383, 46, 14);
		getContentPane().add(lblGold);
		
		JLabel lblRstungsteile = new JLabel("R\u00FCstungsteile:");
		lblRstungsteile.setBounds(164, 408, 96, 14);
		getContentPane().add(lblRstungsteile);
		
		JLabel lblAuswahl = new JLabel("Auswahl");
		lblAuswahl.setBounds(802, 146, 54, 14);
		getContentPane().add(lblAuswahl);
		
		JLabel lblEquipment_1 = new JLabel("Equipment:");
		lblEquipment_1.setBounds(698, 39, 67, 14);
		getContentPane().add(lblEquipment_1);
		
		JLabel lblConsumable = new JLabel("Consumable:");
		lblConsumable.setBounds(698, 66, 84, 14);
		getContentPane().add(lblConsumable);
		
		JLabel lblGold_1 = new JLabel("Gold:");
		lblGold_1.setBounds(698, 93, 46, 14);
		getContentPane().add(lblGold_1);
		
		JLabel lblItemGenerieren = new JLabel("Item generieren:");
		lblItemGenerieren.setBounds(688, 11, 96, 14);
		getContentPane().add(lblItemGenerieren);
		
		JLabel lblStatistik = new JLabel("Statistik:");
		lblStatistik.setBounds(376, 11, 54, 14);
		getContentPane().add(lblStatistik);
		
		JLabel lblKills = new JLabel("Kills:");
		lblKills.setBounds(387, 39, 46, 14);
		getContentPane().add(lblKills);
		
		JLabel lblTode = new JLabel("Tode:");
		lblTode.setBounds(387, 64, 46, 14);
		getContentPane().add(lblTode);
		
		JLabel lblGoldGesamt = new JLabel("gesammeltes Gold:");
		lblGoldGesamt.setBounds(551, 39, 120, 14);
		getContentPane().add(lblGoldGesamt);
		
		JLabel lblSpielzeit = new JLabel("Spielzeit:");
		lblSpielzeit.setBounds(387, 89, 70, 14);
		getContentPane().add(lblSpielzeit);
		
		JLabel lblGlobalesInventar = new JLabel("Globales Inventar");
		lblGlobalesInventar.setBounds(917, 156, 100, 14);
		getContentPane().add(lblGlobalesInventar);
		
		JSeparator separator_8 = new JSeparator();
		separator_8.setBounds(877, 136, 166, 22);
		getContentPane().add(separator_8);
		
		JLabel lblAngriff = new JLabel("Angriff:");
		lblAngriff.setBounds(184, 64, 46, 14);
		getContentPane().add(lblAngriff);
		
		JLabel lblVerteidigung = new JLabel("Verteidigung:");
		lblVerteidigung.setBounds(184, 89, 76, 14);
		getContentPane().add(lblVerteidigung);
		
		JLabel lblLeben = new JLabel("Leben:");
		lblLeben.setBounds(184, 39, 46, 14);
		getContentPane().add(lblLeben);
		
//----------WindowConstants----------
		
		this.setSize(new Dimension(1070, 689));
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
		this.killCount = this.statistics.getMonsterKillCount();
		this.deathCount = this.statistics.getDeathCount();
		this.timePlayed = this.statistics.getTimePlayed();
		this.goldEarned = this.statistics.getGoldEarned();
		
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
				for(int j = 0; j < 9; j++)
					this.inventoryTableRowData[i][j] = "-";
			}
		}
		
		for(int i = 0; i < 5; i++)
		{
			try
			{
				EquipmentModel currentItem = this.equipmentList[i];
				this.equipmentTableRowData[i][0] = i;
				this.equipmentTableRowData[i][1] = currentItem.getItemName();
				this.equipmentTableRowData[i][2] = currentItem.getLevelRestriction();
				this.equipmentTableRowData[i][3] = currentItem.getEquipSlotID();
				this.equipmentTableRowData[i][4] = currentItem.getAttackValue();
				this.equipmentTableRowData[i][5] = currentItem.getDefenseValue();
				this.equipmentTableRowData[i][6] = currentItem.getHpValue();
				this.equipmentTableRowData[i][7] = currentItem.getItemGoldValue();
				this.equipmentTableRowData[i][8] = currentItem.getArmorPartsRevenue();
			}
			catch(NullPointerException npE)
			{
				for(int j = 1; j < 9; j++)
					this.equipmentTableRowData[i][j] = "-";
			}
		}
	}
	
	public void initiateJTables()
	{
		this.inventoryTable = new JTable(new DefaultTableModel(this.inventoryTableRowData, this.inventoryTableColumnNames));
		this.inventoryTable.setEnabled(false);
		this.inventoryTable.setRowHeight(20);
		this.inventoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumnModel inventoryTCM = this.inventoryTable.getColumnModel();
		inventoryTCM.getColumn(0).setPreferredWidth(150);
		inventoryTCM.getColumn(1).setPreferredWidth(71);
		inventoryTCM.getColumn(2).setPreferredWidth(71);
		inventoryTCM.getColumn(3).setPreferredWidth(71);
		inventoryTCM.getColumn(4).setPreferredWidth(71);
		inventoryTCM.getColumn(5).setPreferredWidth(90);
		inventoryTCM.getColumn(6).setPreferredWidth(71);
		inventoryTCM.getColumn(7).setPreferredWidth(71);
		inventoryTCM.getColumn(8).setPreferredWidth(110);

		this.equipmentTable = new JTable(new DefaultTableModel(this.equipmentTableRowData, this.equipmentTableColumnNames));
		this.equipmentTable.setEnabled(false);
		this.equipmentTable.setRowHeight(20);
		this.equipmentTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumnModel equipmentTCM = this.equipmentTable.getColumnModel();
		equipmentTCM.getColumn(0).setPreferredWidth(50);
		equipmentTCM.getColumn(1).setPreferredWidth(150);
		equipmentTCM.getColumn(2).setPreferredWidth(71);
		equipmentTCM.getColumn(3).setPreferredWidth(71);
		equipmentTCM.getColumn(4).setPreferredWidth(71);
		equipmentTCM.getColumn(5).setPreferredWidth(90);
		equipmentTCM.getColumn(6).setPreferredWidth(71);
		equipmentTCM.getColumn(7).setPreferredWidth(71);
		equipmentTCM.getColumn(8).setPreferredWidth(110);
		
		JScrollPane inventoryScrollPane = new JScrollPane(this.inventoryTable);
		this.inventoryTable.setFillsViewportHeight(true);
		inventoryScrollPane.setBounds(10, 146, 779, 223);
		getContentPane().add(inventoryScrollPane);
		
		JScrollPane equipmentScrollPane = new JScrollPane(this.equipmentTable);
		this.equipmentTable.setFillsViewportHeight(true);
		equipmentScrollPane.setBounds(10, 460, 758, 123);
		getContentPane().add(equipmentScrollPane);
	}
	
	public void updateShowcaseView()
	{
		this.initiateShowcase();
		
		txtFArmorParts.setText(Integer.toString(this.armorPartsCount));
		txtFGold.setText(Integer.toString(this.goldCount));
		txtFName.setText(this.charName);
		txtFLevel.setText(Integer.toString(this.level));
		txtFExperience.setText(Integer.toString(this.experience));
		
		this.initiateJTables();
		
		globalInventoryJList.setSelectedIndex(0);
	}
	
	public ItemModel getSelectedItemFromInventory()
	{
		try
		{
			for(Enumeration<AbstractButton> buttons = this.inventoryRadioBtnGroup.getElements(); buttons.hasMoreElements();)
			{
				AbstractButton currentButton = buttons.nextElement();
				if(currentButton.isSelected())
						return this.inventoryContentList.get((Integer.parseInt(currentButton.getName())));
			}
		}
		catch(IndexOutOfBoundsException iOoBE)
		{}
		return null;
	}
	
	public EquipmentModel getSelectedItemFromEquipment()
	{
		for(Enumeration<AbstractButton> buttons = this.equipmentRadioBtnGroup.getElements(); buttons.hasMoreElements();)
		{
			AbstractButton currentButton = buttons.nextElement();
			if(currentButton.isSelected())
					return this.equipmentList[(Integer.parseInt(currentButton.getName()))];
		}
		return null;
	}
	
	public int getSelectedEquipmentToGenerate()
	{
		return this.combEquip.getSelectedIndex();
	}
	
	public void addItemToGlobalInventory(String paramItemName)
	{
		this.globalInventoryListModel.addElement(paramItemName);
	}
	
	public void removeItemFromGlobalInventory(int paramIndex)
	{
		this.globalInventoryListModel.remove(paramIndex);
	}
	
	public int getSelectedIDFromGlobalInventory()
	{
		return this.globalInventoryJList.getSelectedIndex();
	}
	
	public void displayErrorMessage(int paramErrorID)
	{
		String errorMessage = "";
		
		switch(paramErrorID)
		{
			case 1:
				errorMessage = "Dieses Item kann nicht ausgerüstet werden!";
				break;
			case 2:
				errorMessage = "Dieses Item kann nicht konsumiert werden!";
				break;
			case 3:
				errorMessage = "Dieses Item kann nicht zerlegt werden";
				break;
			case 4:
				errorMessage = "Ihr Inventar ist voll!";
				break;
		}
		JOptionPane.showMessageDialog(null, errorMessage, "Achtung!", JOptionPane.WARNING_MESSAGE);
	}
}
