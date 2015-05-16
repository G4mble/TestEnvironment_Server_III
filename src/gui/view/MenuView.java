package gui.view;

import gui.controller.MenuController;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Dimension;

public class MenuView extends JFrame
{
	public MenuView(MenuController paramMenuController) 
	{
		getContentPane().setLayout(null);
		
		JButton btnLogin = new JButton("Einloggen");
		btnLogin.setBounds(10, 11, 106, 42);
		btnLogin.addActionListener(paramMenuController);
		btnLogin.setActionCommand("login");
		getContentPane().add(btnLogin);
		
		JButton btnRegister = new JButton("Registrieren");
		btnRegister.setBounds(10, 64, 106, 42);
		btnRegister.addActionListener(paramMenuController);
		btnRegister.setActionCommand("register");
		getContentPane().add(btnRegister);
		
		JButton btnClose = new JButton("Beenden");
		btnClose.setBounds(10, 117, 106, 42);
		btnClose.addActionListener(paramMenuController);
		btnClose.setActionCommand("exit");
		getContentPane().add(btnClose);
		
		this.setSize(new Dimension(132, 195));
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
}
