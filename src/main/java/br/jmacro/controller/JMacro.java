package br.jmacro.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import br.jmacro.view.JMacroView;


public class JMacro {
	public Robot robot;
	private JMacroView view;
	private ArrayList<String> comandos;
	private ArrayList<Rotina>rotinas;
	
	
	public JMacro(){
		rotinas = new ArrayList<>();
		comandos = new ArrayList<>();
		view = new JMacroView();
		view.setVisible(false);
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public void carregar(String caminho){
		File arquivoVerifica = new File(caminho);
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
				comandos.add(linhaArquivo.readLine());
			}
			linhaArquivo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void executarLista(){
		this.view.setVisible(true);
		for (String comando : comandos) {
			executa(comando);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.view.setVisible(false);
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
				
				try {
					int x = Integer.parseInt(parametros.substring(0, parametros.indexOf(',')));
					int y = Integer.parseInt(parametros.substring(parametros.indexOf(',') + 1));
					
					robot.mouseMove(x, y);
					Thread.sleep(200);
					robot.mousePress(InputEvent.BUTTON1_MASK);
					Thread.sleep(100);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);		
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else if(comando.equals("digitar")){
				for (int i=0; i<parametros.length(); i++) {
					   char c = parametros.charAt(i);
					   teclar(c);
				}
			}else if(comando.equals("enter")){
				
				try {
					robot.keyPress(KeyEvent.VK_ENTER); 
					Thread.sleep(200);
					robot.keyRelease(KeyEvent.VK_ENTER);
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			else if(comando.equals("maximizar")){
				try {
					Thread.sleep(200);
					robot.keyPress(KeyEvent.VK_WINDOWS);
					Thread.sleep(200);
					robot.keyPress(KeyEvent.VK_UP);
					Thread.sleep(100);
					robot.keyRelease(KeyEvent.VK_UP);
					Thread.sleep(100);
					robot.keyRelease(KeyEvent.VK_WINDOWS);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}

		}
		
	}
	public void teclar(char c){

		
		try {
			Thread.sleep(100);
			robot.keyPress(Character.toUpperCase(c)); 
			Thread.sleep(8);
			robot.keyRelease(Character.toUpperCase(c));
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void carregarRotina(){
		Properties config = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream("rotina.txt");
			config.load(file);
			for (Object string : config.keySet()) {
				String macroID = config.getProperty(string.toString());
				this.rotinas.add(new Rotina(string.toString(), macroID));
			}
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}
}
