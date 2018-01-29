package de.lvm.javaakademie.chat;

import java.util.List;

public class Spiel {

	private String name;
	
	private List<String> spieler;

	
	
	
	
	public Spiel(String name) {
		super();
		this.name = name;
	}


	public String getName() {
		return name;
	}

	
	public List<String> getTeilnehmer() {
		return spieler;
	}

	public void setTeilnehmer(List<String> teilnehmer) {
		this.spieler = teilnehmer;
	}
	
	
	
}
