package br.jmacro.controller;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import br.jmacro.view.JMacroView;

public class JMacro {
	public Robot robot;
	private JMacroView view;
	private ArrayList<String> comandos;
	private ArrayList<Rotina> rotinas;

	public JMacro() {

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

	public void carregar(String caminho) {
		File arquivoVerifica = new File(caminho);
		if (!arquivoVerifica.exists()) {
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

	public void executarLista() {
		this.view.setVisible(true);
		for (String comando : comandos) {
			System.out.println("Executar " + comando);
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
	 * 
	 * @param comando
	 */
	public void executa(String mensagem) {

		if (mensagem == null)
			return;
		if (mensagem.length() < 2) {
			return;
		}
		if (mensagem.charAt(0) == '#') {
			return;
		}
		if (mensagem.indexOf('(') != -1 && mensagem.indexOf(')') != -1) {
			String comando = mensagem.substring(0, mensagem.indexOf('('));
			String parametros = mensagem.substring((mensagem.indexOf('(') + 1), mensagem.indexOf(')'));
			if(comando.equals("espera")){
				try {
					Thread.sleep(Integer.parseInt(parametros.trim()));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
			if (comando.equals("click")) {
				
				try {
					
					int x = Integer.parseInt(parametros.substring(0, parametros.indexOf(',')).trim());
					int y = Integer.parseInt(parametros.substring(parametros.indexOf(',') + 1).trim());
					if(x < 220 || y < 230){
						view.setLocation(300, 300);
					}
					robot.mouseMove(x, y);
					Thread.sleep(200);
					robot.mousePress(InputEvent.BUTTON1_MASK);
					Thread.sleep(100);
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
					Thread.sleep(500);
					if(x < 220 || y < 230){
						view.setLocation(3,3);
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (StringIndexOutOfBoundsException e) {
					System.out.println("click exige dois parametros numéricos. ");
				}
				return;
			} else if (comando.equals("digitar")) {
				for (int i = 0; i < parametros.length(); i++) {
					char c = parametros.charAt(i);
					teclar(c);
				}
				return;
			} else if (comando.equals("enter")) {

				try {
					robot.keyPress(KeyEvent.VK_ENTER);
					Thread.sleep(200);
					robot.keyRelease(KeyEvent.VK_ENTER);
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			} else if (comando.equals("maximizar")) {
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
				
				return;
			}else if(comando.equals("capturar")){
				robot.keyPress(KeyEvent.VK_PRINTSCREEN);
				robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
				return;
			} else if(comando.equals("colar")){
				robot.keyPress(KeyEvent.VK_CONTROL);
				robot.keyPress(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_V);
				robot.keyRelease(KeyEvent.VK_CONTROL);
				return;
			}else if(comando.equals("altf2")){
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_F2);
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_F2);
				return;
			}else if(comando.equals("altf4")){
				robot.keyPress(KeyEvent.VK_ALT);
				robot.keyPress(KeyEvent.VK_F4);
				robot.keyRelease(KeyEvent.VK_ALT);
				robot.keyRelease(KeyEvent.VK_F4);
				return;
			}
			else if(comando.equals("esc")) {
				robot.keyPress(KeyEvent.VK_ESCAPE);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				robot.keyRelease(KeyEvent.VK_ESCAPE);
				return;
			}else if(comando.equals("tab")) {
				robot.keyPress(KeyEvent.VK_TAB);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				robot.keyRelease(KeyEvent.VK_ESCAPE);
				return;
			}

		}

	}

	public void teclar(char c) {
		boolean capsLigado = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
		if (capsLigado) {
			Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false);
		}

		try {
			
			Thread.sleep(100);
			if(c == '@') {
				robot.keyPress(KeyEvent.VK_SHIFT);
				robot.keyPress(KeyEvent.VK_2);
				Thread.sleep(8);
				robot.keyRelease(KeyEvent.VK_SHIFT);
				Thread.sleep(100);	
			}else {
				if(Character.isUpperCase(c)) {
					robot.keyPress(KeyEvent.VK_SHIFT);
				}
				robot.keyPress(Character.toUpperCase(c));
				Thread.sleep(8);
				robot.keyRelease(Character.toUpperCase(c));
				Thread.sleep(100);	
			
				if(Character.isUpperCase(c)) {
					robot.keyRelease(KeyEvent.VK_SHIFT);
				}
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void carregarRotina() {

		Properties config = new Properties();
		FileInputStream file;
		try {
			file = new FileInputStream(ARQUIVO_ROTINA);
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

	public void verificandoRotina() {
		Thread verificando = new Thread(new Runnable() {
			@Override
			public void run() {
				do {
					for (Rotina rotina : rotinas) {
						System.out.println("Verificando rotina " + rotina.getMacroID());

						Calendar calAtual = Calendar.getInstance();
						calAtual.setTime(new Date());
						Calendar cal = rotina.getHora();

						int horaAtual = calAtual.get(Calendar.HOUR_OF_DAY);
						int minutoAtual = calAtual.get(Calendar.MINUTE);
						int mesAtual = calAtual.get(Calendar.MONTH);
						int diaAtual = calAtual.get(Calendar.DAY_OF_MONTH);
						System.out.println("Minuto Atual:" + minutoAtual);
						System.out.println("Minuto da Rotina:" + cal.get(Calendar.MINUTE));

						if (horaAtual == cal.get(Calendar.HOUR_OF_DAY) && minutoAtual == cal.get(Calendar.MINUTE)) {
							System.out.println("Hora minuto igual");
							if (!(mesAtual == cal.get(Calendar.MONTH) && diaAtual == cal.get(Calendar.DAY_OF_MONTH))) {
								System.out.println("Data diferente, executar comandos");
								cal.set(Calendar.MONTH, mesAtual);
								cal.set(Calendar.DAY_OF_MONTH, diaAtual);
								cal.set(Calendar.YEAR, calAtual.get(Calendar.YEAR));
								carregar(rotina.getMacroID());
								executarLista();

								SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
								Date now = Calendar.getInstance().getTime();
								String dataFormatada = sdf.format(now);

								log("Execução de macro " + rotina.getMacroID() + " em " + dataFormatada, "log.txt");

							} else {
								System.out.println("Data igual, comando ja foi executado.");
								System.out.println("Mes da Rotina:" + rotina.getHora().get(Calendar.MONTH));

							}
						} else {
							System.out.println("Hora minuto diferente");

						}
						System.out.println("-------------");
					}
					try {
						Thread.sleep(12000);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} while (true);

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

	public static final String ARQUIVO_ROTINA = "rotina.txt";
}
