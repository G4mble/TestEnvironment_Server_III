package gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import platform.ProgramController;
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
	public ShowcaseController(ProgramController paramProgramController)
	{
		this.programController = paramProgramController;
		this.showcaseView = new ShowcaseView(this);
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
