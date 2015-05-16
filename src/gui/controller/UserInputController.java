package gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import platform.ProgramController;
import gui.view.UserInputView;

public class UserInputController implements ActionListener
{
	private UserInputView userInputView;
	private ProgramController programController;
	
	public UserInputController(ProgramController paramProgramController, String paramAction)
	{
		this.programController = paramProgramController;
		this.userInputView = new UserInputView(this, paramAction);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case "login":
				this.programController.beginLoginProcess(this.userInputView.getUsername(), this.userInputView.getPassword());
				break;
			case "register":
				this.programController.invokeUserRegistration(this.userInputView.getUsername(), this.userInputView.getPassword(), this.userInputView.getSelectedCharacter());
				break;
			case "cancel":
				this.userInputView.dispose();
				this.programController.returnToMenu();
				break;
		}
	}
	
	public UserInputView getUserInputView()
	{
		return this.userInputView;
	}
}
