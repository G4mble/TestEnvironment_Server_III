package gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import platform.ProgramController;
import gui.view.HighscoreView;

/**
 * controller of HighscoreView, processes ActionEvents triggered there
 * @author Staufenberg, Thomas, 5820359
 * */
public class HighscoreController implements ActionListener
{
	private HighscoreView highscoreView;
	private ProgramController programController;
	
	/**
	 * instantiates HighscoreView
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public HighscoreController(Object[][] paramHighscoreData, ProgramController paramProgramController)
	{
		this.programController = paramProgramController;
		this.highscoreView = new HighscoreView(this, paramHighscoreData);
	}

	/**
	 * processes ActionEvents triggered in this.highscoreView
	 * @author Staufenberg, Thomas, 5820359
	 * */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		String actionCommand = e.getActionCommand();
		if(actionCommand.startsWith("sort"))
		{
			String filterAttribute = "";
			switch(actionCommand.substring(5))
			{
				case "name":
					filterAttribute = "username";
					break;
				case "kills":
					filterAttribute = "monsterKillCount";
					break;
				case "deaths":
					filterAttribute = "numberOfDeaths";
					break;
				case "gold":
					filterAttribute = "goldEarned";
					break;
				case "time":
					filterAttribute = "timePlayed";
					break;
			}
			this.programController.updateHighscore(filterAttribute);
		}
		else if(actionCommand.equals("exit"))
			this.programController.closeHighscore();
	}
	
	/**
	 * forwards updated highscoreData to this.highscoreView
	 * @param paramHighscoreData Object[][] containing the updated highscoreData
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public void updateHighscore(Object[][] paramHighscoreData)
	{
		if(paramHighscoreData != null)
			this.highscoreView.updateJTables(paramHighscoreData);
		else
			JOptionPane.showMessageDialog(null, "Highscore konnte nicht geladen werden!", "Fehler!", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * @return the current higscoreView
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public HighscoreView getHighscoreView()
	{
		return this.highscoreView;
	}
}
