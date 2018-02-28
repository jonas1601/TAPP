package de.tapp.application;

public class Nachricht {

	private String text;
	private String absender;
	private String datum;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getAbsender() {
		return absender;
	}

	public void setAbsender(String absender) {
		this.absender = absender;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Nachricht [text=").append(text).append(", absender=").append(absender).append("]");
		return builder.toString();
	}

}
