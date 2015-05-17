package gui.view;

import gui.controller.UserInputController;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.Dimension;

import javax.swing.JPasswordField;
import javax.swing.JRadioButton;

/**
 * displays the userInputView</br>can either be used for login or register
 * @author Staufenberg, Thomas, 5820359
 * */
public class UserInputView extends JFrame
{
	private static final long serialVersionUID = 5652433001281699604L;
	private JTextField txtFUsername;	
	private JPasswordField txtFPassword;
	private JRadioButton rdbtnWarrior, rdbtnMage;

	/**
	 * creates all window components
	 * @param paramAction spefify the usage of the userInputView {"Einloggen", "Registrieren"}
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public UserInputView(UserInputController paramLoginController, String paramAction)
	{
		String actionCommand;
		if(paramAction.equals("Einloggen"))				//modify components for login usage
		{
			actionCommand = "login";
			this.setSize(new Dimension(350, 105));
			this.setTitle("Login");
		}
		else											//modify componenty for register usage
		{
			actionCommand = "register";
			this.setSize(new Dimension(350, 145));
			this.setTitle("Registrieren");
			
			rdbtnWarrior = new JRadioButton("Krieger");
			rdbtnWarrior.setBounds(104, 80, 84, 23);
			rdbtnWarrior.setSelected(true);
			getContentPane().add(rdbtnWarrior);
			
			rdbtnMage = new JRadioButton("Magier");
			rdbtnMage.setBounds(220, 80, 84, 23);
			getContentPane().add(rdbtnMage);
			
			ButtonGroup radioBtnGroup = new ButtonGroup();
			radioBtnGroup.add(rdbtnWarrior);
			radioBtnGroup.add(rdbtnMage);
			
			JLabel lblCharacter = new JLabel("Charakter:");
			lblCharacter.setBounds(10, 81, 84, 21);
			getContentPane().add(lblCharacter);
		}
		
		//create general components used in all cases
		
		getContentPane().setLayout(null);
		
		txtFUsername = new JTextField();
		txtFUsername.setBounds(99, 11, 119, 22);
		getContentPane().add(txtFUsername);
		txtFUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Benutzername:");
		lblUsername.setBounds(10, 11, 94, 22);
		getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Passwort:");
		lblPassword.setBounds(10, 44, 84, 22);
		getContentPane().add(lblPassword);
		
		JButton btnDynamicAction = new JButton(paramAction);
		btnDynamicAction.setBounds(228, 11, 106, 23);
		btnDynamicAction.addActionListener(paramLoginController);
		btnDynamicAction.setActionCommand(actionCommand);
		getContentPane().add(btnDynamicAction);
		
		JButton btnCancel = new JButton("Abbrechen");
		btnCancel.setBounds(228, 44, 106, 23);
		btnCancel.addActionListener(paramLoginController);
		btnCancel.setActionCommand("cancel");
		getContentPane().add(btnCancel);
		
		txtFPassword = new JPasswordField();
		txtFPassword.setBounds(99, 44, 119, 21);
		getContentPane().add(txtFPassword);
		
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	/**
	 * @return username entered in the textField
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public String getUsername()
	{
		return this.txtFUsername.getText();
	}
	
	/**
	 * @return password entered in the textField
	 * @author Staufenberg, Thomas, 5820359
	 * */
	@SuppressWarnings("deprecation")
	public String getPassword()
	{
		return this.txtFPassword.getText();
	}
	
	/**
	 * @return ID of the character selected via radioButtons
	 * @author Staufenberg, Thomas, 5820359
	 * */
	public int getSelectedCharacter()
	{
		if(this.rdbtnWarrior.isSelected())
			return 1;
		else 
			return 2;
	}
}
