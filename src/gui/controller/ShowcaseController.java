package gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import platform.ProgramController;
import shared.InventoryModel;
import shared.character.PlayerCharacter;
import shared.item.EquipmentModel;
import shared.item.ItemModel;
import shared.statistics.StatisticsModel;
import gui.view.ShowcaseView;

/**
 * controller of ShowcaseView, processes ActionEvents triggered there
 * @author Staufenberg, Thomas, 5820359
 * */
public class ShowcaseController implements ActionListener
{
	private ShowcaseView showcaseView;
	private ProgramController programController;
	
	/**
	 * instantiates ShowcaseView
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public ShowcaseController(ProgramController paramProgramController, String paramUsername, PlayerCharacter paramPlayer)
	{
		this.programController = paramProgramController;
		this.showcaseView = new ShowcaseView(this, paramUsername, paramPlayer);
	}

	/**
	 * processes ActionEvents triggered in this.showcaseView
	 * @author Staufenberg, Thomas, 5820359
	 * */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case "exit":
				System.exit(0);;
		}
	}
}
