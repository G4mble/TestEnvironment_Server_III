package gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import platform.ProgramController;
import gui.view.MenuView;

public class MenuController implements ActionListener
{
	private MenuView menuView;
	private ProgramController programController;
	
	public MenuController(ProgramController paramProgramController)
	{
		this.programController = paramProgramController;
		this.menuView = new MenuView(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case "login":
				this.menuView.dispose();
				this.programController.initiateAuthentication("Einloggen");
				break;
			case "register":
				this.menuView.dispose();
				this.programController.initiateAuthentication("Registrieren");
				break;
			case "exit":
				this.programController.exitProgram(0);
		}
	}

}
