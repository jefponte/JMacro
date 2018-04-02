package br.jmacro.view;

import java.awt.Color;
import java.awt.EventQueue;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JMacroView extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	

	/**
	 * Create the frame.
	 */
	public JMacroView() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(3, 3, 206, 212);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(Color.BLACK);
		setUndecorated(true);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(getClass().getResource("/images/jmacroboot.png")));
		label.setBounds(3, 3, 201, 206);
		contentPane.add(label);
	}

}
