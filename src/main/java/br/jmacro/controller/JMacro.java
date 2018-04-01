package br.jmacro.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import br.jmacro.view.JMacroView;


public class JMacro {
	private JMacroView view;
	public JMacro(){
		view = new JMacroView();
		view.setVisible(false);

	}


	public void configura(){
		File arquivoVerifica = new File("macro.txt");
		if(!arquivoVerifica.exists()){
			FileWriter fw;
			try {
				fw = new FileWriter(arquivoVerifica, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write("[Adicione comandos da Macro Aqui] ");
				bw.newLine();
				bw.close();
				fw.close();
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {

			FileInputStream arquivo = new FileInputStream(arquivoVerifica);
			BufferedReader linhaArquivo = new BufferedReader(new InputStreamReader(arquivo));
			linhaArquivo.ready();
			while (linhaArquivo.ready()) {
				String linha = linhaArquivo.readLine();
				executa(linha);
			}
			linhaArquivo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * Processa um comando e executa. 
	 * @param comando
	 */
	public void executa(String mensagem){
		if (mensagem.indexOf('(') != -1 && mensagem.indexOf(')') != -1) {
			String comando = mensagem.substring(0, mensagem.indexOf('('));
			String parametros = mensagem.substring((mensagem.indexOf('(') + 1),mensagem.indexOf(')'));
			
			if(comando.equals("click")){
				Robot bot;
				try {
					bot = new Robot();
					int x = Integer.parseInt(parametros.substring(0, parametros.indexOf(',')));
					int y = Integer.parseInt(parametros.substring(parametros.indexOf(',') + 1));
					
					bot.mouseMove(x, y);
					Thread.sleep(200);
					bot.mousePress(InputEvent.BUTTON1_MASK);
					Thread.sleep(200);
					bot.mouseRelease(InputEvent.BUTTON1_MASK);		
					Thread.sleep(200);
				} catch (AWTException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else if(comando.equals("digitar")){
				
				for (int i=0; i<parametros.length(); i++) {
					   char c = parametros.charAt(i);
					   teclar(c);
				}
			}

		}
		
	}
	public void teclar(char c){
		Robot robot;
		
		try {
			robot = new Robot();
			robot.keyPress(Character.toUpperCase(c)); 
			Thread.sleep(200);
			robot.keyRelease(Character.toUpperCase(c));
			Thread.sleep(200);
		} catch (AWTException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
