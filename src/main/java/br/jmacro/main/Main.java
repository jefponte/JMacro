package br.jmacro.main;





import javax.swing.JFrame;

import br.jmacro.controller.JMacro;



public class Main {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 300);
		frame.setVisible(true);
		frame.setBounds(0, 0, 300, 300);
		
		JMacro macro = new JMacro();
		macro.carregar("macro.txt");
		macro.executarLista();
		
//		
//		JMacro macro = new JMacro();
//		macro.carregarRotina();
//		macro.verificandoRotina();

	}

}
