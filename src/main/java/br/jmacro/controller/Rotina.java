package br.jmacro.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Rotina {

	private Calendar hora;
	private String macroID;
	public Rotina(String hora, String macroID) {
		this.macroID = macroID;
		try {
			System.out.println("Construtor do rotina hora:"+hora);
			SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
			Date data = sdf.parse(hora);
			
			this.hora = Calendar.getInstance();
			this.hora.setTime(data);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Calendar getHora() {
		return hora;
	}

	public void setHora(Calendar hora) {
		this.hora = hora;
	}

	public String getMacroID() {
		return macroID;
	}
	public void setMacroID(String macroID) {
		this.macroID = macroID;
	}
	
}
