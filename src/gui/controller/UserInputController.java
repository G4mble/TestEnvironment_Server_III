package gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import platform.ProgramController;
import gui.view.UserInputView;

/**
 * controller of UserInputView, processes ActionEvents triggered there
 * @author Staufenberg, Thomas, 5820359
 * */
public class UserInputController implements ActionListener
{
	private UserInputView userInputView;
	private ProgramController programController;

	/**
	 * instantiates UserInputView
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public UserInputController(ProgramController paramProgramController, String paramAction)
	{
		this.programController = paramProgramController;
		this.userInputView = new UserInputView(this, paramAction);
	}

	/**
	 * processes ActionEvents triggered in this.userInputView
	 * @author Staufenberg, Thomas, 5820359
	 * */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case "login":
				this.programController.initiateLoginProcess(this.userInputView.getUsername(), this.userInputView.getPassword());
				break;
			case "register":
				this.programController.initiateRegistrationProcess(this.userInputView.getUsername(), this.userInputView.getPassword(), this.userInputView.getSelectedCharacter());
				break;
			case "cancel":
				this.userInputView.dispose();
				this.programController.returnToMenu();
				break;
		}
	}
	
	/**
	 * @return current userInputView: JFrame
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public UserInputView getUserInputView()
	{
		return this.userInputView;
	}
}
