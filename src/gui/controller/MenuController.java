package gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import platform.ProgramController;
import gui.view.MenuView;

/**
 * controller of MenuView JFrame, processes ActionEvents triggered there
 * @author Staufenberg, Thomas, 5820359
 * */
public class MenuController implements ActionListener
{
	private MenuView menuView;
	private ProgramController programController;
	
	/**
	 * instantiates MenuView
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public MenuController(ProgramController paramProgramController)
	{
		this.programController = paramProgramController;
		this.menuView = new MenuView(this);
	}

	/**
	 * processes ActionEvents triggered in this.menuView
	 * @author Staufenberg, Thomas, 5820359
	 * */
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
				System.exit(0);
		}
	}

}
