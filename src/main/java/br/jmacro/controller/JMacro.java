package br.jmacro.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

			comandos.clear();
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
			e.printStackTrace();
		}
	}
	public void verificandoRotina(){
		Thread verificando = new Thread(new Runnable() {
			@Override
			public void run() {
				do{
					
					System.out.println(new Date().toString());
					for (Rotina rotina : rotinas) {
						System.out.println("Verificando rotina "+rotina.getMacroID());
						
						Calendar calAtual = Calendar.getInstance();
						calAtual.setTime(new Date());
						Calendar cal = rotina.getHora();
						
						int horaAtual =  calAtual.get(Calendar.HOUR_OF_DAY);
						int minutoAtual = calAtual.get(Calendar.MINUTE);
						int mesAtual = calAtual.get(Calendar.MONTH);
						int diaAtual = calAtual.get(Calendar.DAY_OF_MONTH);
						System.out.println("Minuto Atual: "+minutoAtual);
						System.out.println("Minuto da Rotina: "+ cal.get(Calendar.MINUTE));
						
						if(horaAtual == cal.get(Calendar.HOUR_OF_DAY) && minutoAtual == cal.get(Calendar.MINUTE)){
							System.out.println("Hora minuto igual");
							if (!(mesAtual == cal.get(Calendar.MONTH) && diaAtual == cal.get(Calendar.DAY_OF_MONTH))){
								System.out.println("Data diferente, executar comandos");
								cal.set(Calendar.MONTH, mesAtual);
								cal.set(Calendar.DAY_OF_MONTH, diaAtual);
								cal.set(Calendar.YEAR, calAtual.get(Calendar.YEAR));
								carregar(rotina.getMacroID());
								executarLista();
								log("Execução de macro "+rotina.getMacroID()+" em "+cal.getTime().toString(), "log.txt");
								
							}else{
								System.out.println("Data igual, comando ja foi executado.");
								System.out.println("Mes da Rotina:"+rotina.getHora().get(Calendar.MONTH));
								
								
							}
						}else{
							System.out.println("Hora minuto difetrente");
							
						}
						System.out.println("-------------");
					}
					try {
						Thread.sleep(20000);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}while(true);
				
			}
		});
		verificando.start();
	}
	public void log(String mensagem, String nomeArquivo) {
		File arquivo = new File(nomeArquivo);
		if (!arquivo.exists()) {
			try {
				arquivo.createNewFile();
				FileWriter fw = new FileWriter(arquivo, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(mensagem);
				bw.newLine();
				bw.close();
				fw.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		else

		{
			try {

				FileWriter fw = new FileWriter(arquivo, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(mensagem);
				bw.newLine();
				bw.close();
				fw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
}
