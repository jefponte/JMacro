package br.jmacro.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Rotina {

	private Date hora;
	private String macroID;
	public Rotina(String hora, String macroID) {
		this.macroID = macroID;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
			this.hora = sdf.parse(hora);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Date getHora() {
		return hora;
	}
	public void setHora(Date hora) {
		this.hora = hora;
	}
	public String getMacroID() {
		return macroID;
	}
	public void setMacroID(String macroID) {
		this.macroID = macroID;
	}
	
}
