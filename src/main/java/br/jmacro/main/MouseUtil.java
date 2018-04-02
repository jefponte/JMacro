package br.jmacro.main;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JLabel;

import br.jmacro.view.JMacroView;

public class MouseUtil {
	public static void main(String[] args) {
		JMacroView view = new JMacroView();
		view.setSize(300, 300);
		view.setUndecorated(false);
		view.setVisible(true);
		view.getContentPane().setBackground(Color.cyan);
		
		
		JLabel teste = new JLabel("PosicaoMouse(x,y)");
		
		view.getContentPane().add(teste);
		teste.setBounds(100, 220, 200, 30);
		do{
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Point p = java.awt.MouseInfo.getPointerInfo().getLocation(); 
			teste.setText("PosicaoMouse("+(int)p.getX()+","+(int)p.getY()+")");
		}while(true);
	}

}
