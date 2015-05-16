package gui.view;

import gui.controller.ShowcaseController;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JButton;

public class ShowcaseView extends JFrame
{
	private static final long serialVersionUID = -1847497075140092998L;

	public ShowcaseView(ShowcaseController paramShowcaseController)
	{
		getContentPane().setLayout(null);
		
		JButton btnExit = new JButton("EXIT");
		btnExit.setBounds(10, 11, 257, 266);
		btnExit.addActionListener(paramShowcaseController);
		btnExit.setActionCommand("exit");
		getContentPane().add(btnExit);
		
		
		this.setSize(new Dimension(283, 316));
		this.setResizable(false);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setTitle("Showcase");
		this.setVisible(true);
		
	}

}
