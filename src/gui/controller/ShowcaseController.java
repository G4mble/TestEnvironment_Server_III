package gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import platform.ProgramController;
import gui.view.ShowcaseView;

public class ShowcaseController implements ActionListener
{
	private ShowcaseView showcaseView;
	private ProgramController programController;
	
	public ShowcaseController(ProgramController paramProgramController)
	{
		this.programController = paramProgramController;
		this.showcaseView = new ShowcaseView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case "exit":
				this.programController.exitProgram(0);
		}
	}

}
