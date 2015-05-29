package gui.view;

import gui.controller.HighscoreController;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JSeparator;

/**
 * displays the highscoreView
 * @author Staufenberg, Thomas, 5820359
 * */
public class HighscoreView extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Object[] highscoreTableColumnNames = {"Name", "Kills", "Tode", "Gold", "Spielzeit"};
	private JTable highscoreTable;
	private JScrollPane highscoreScrollPane;
	
	/**
	 * creates all window components
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public HighscoreView(HighscoreController paramHighscoreController, Object[][] paramHighscoreData) 
	{
		setSize(new Dimension(400, 280));
		getContentPane().setLayout(null);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(299, 195, 89, 52);
		btnExit.addActionListener(paramHighscoreController);
		btnExit.setActionCommand("exit");
		getContentPane().add(btnExit);
		
		JButton btnName = new JButton("Name");
		btnName.setBounds(10, 195, 89, 23);
		btnName.addActionListener(paramHighscoreController);
		btnName.setActionCommand("sort_name");
		getContentPane().add(btnName);
		
		JButton btnKills = new JButton("Kills");
		btnKills.setBounds(10, 224, 89, 23);
		btnKills.addActionListener(paramHighscoreController);
		btnKills.setActionCommand("sort_kills");
		getContentPane().add(btnKills);
		
		JButton btnDeaths = new JButton("Tode");
		btnDeaths.setBounds(101, 195, 89, 23);
		btnDeaths.addActionListener(paramHighscoreController);
		btnDeaths.setActionCommand("sort_deaths");
		getContentPane().add(btnDeaths);
		
		JButton btnGold = new JButton("Gold ");
		btnGold.setBounds(101, 224, 89, 23);
		btnGold.addActionListener(paramHighscoreController);
		btnGold.setActionCommand("sort_gold");
		getContentPane().add(btnGold);
		
		JButton btnTimePlayed = new JButton("Spielzeit");
		btnTimePlayed.setBounds(192, 195, 89, 23);
		btnTimePlayed.addActionListener(paramHighscoreController);
		btnTimePlayed.setActionCommand("sort_time");
		getContentPane().add(btnTimePlayed);
		
		this.initiateJTables(paramHighscoreData);
		
		JLabel lblSortieren = new JLabel("Sortieren:");
		lblSortieren.setBounds(20, 168, 63, 14);
		getContentPane().add(lblSortieren);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 184, 272, 23);
		getContentPane().add(separator);
		setResizable(false);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setTitle("Highscore");
		this.setVisible(true);
	}
	
	/**
	 * creates all required JTables with their corresponding data model</br>
	 * embeds the table into a JScrollPane to allow scrollable tables
	 * @param paramHighscoreData data to be displayed in the JTable
	 * @author Staufenberg, Thomas, 5820359
	 * */
	private void initiateJTables(Object[][] paramHighscoreData)
	{
		this.highscoreTable = new JTable(paramHighscoreData, this.highscoreTableColumnNames);
		this.highscoreTable.setEnabled(false);
		this.highscoreTable.setRowHeight(20);
		this.highscoreTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableColumnModel highscoreTCM = this.highscoreTable.getColumnModel();
		highscoreTCM.getColumn(0).setPreferredWidth(100);
		highscoreTCM.getColumn(1).setPreferredWidth(65);
		highscoreTCM.getColumn(2).setPreferredWidth(65);
		highscoreTCM.getColumn(3).setPreferredWidth(65);
		highscoreTCM.getColumn(4).setPreferredWidth(65);
		
		this.highscoreScrollPane = new JScrollPane(this.highscoreTable);
		this.highscoreTable.setFillsViewportHeight(true);
		this.highscoreScrollPane.setBounds(10, 11, 378, 150);
		getContentPane().add(this.highscoreScrollPane);
	}
	
	/**
	 * invokes new initiation of the JTables when the data model has changed
	 * @param paramHighscoreData changed data
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void updateJTables(Object[][] paramHighscoreData)
	{
		this.getContentPane().remove(this.highscoreScrollPane);
		this.initiateJTables(paramHighscoreData);
	}
}
