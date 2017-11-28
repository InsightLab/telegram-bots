package org.insightlab.qualis_bot.entities;

import info.debatty.java.stringsimilarity.JaroWinkler;

public class Conference {

	private String abbreviation;
	private String name;
	private String qualis;
	
	public Conference(String abbreviation, String name, String qualis) {

		this.abbreviation = abbreviation;
		this.name = name;
		this.qualis = qualis;
	
	}
	
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getQualis() {
		return qualis;
	}
	public void setQualis(String qualis) {
		this.qualis = qualis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((abbreviation == null) ? 0 : abbreviation.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}


	public boolean equals(String string) {
		
		JaroWinkler jw = new JaroWinkler();
		
		if(jw.similarity(this.abbreviation.toLowerCase(), string.toLowerCase()) >=0.8 || this.abbreviation.toLowerCase().contains(string.toLowerCase())) {
			
			return true;
		}
		
		if(jw.similarity(this.name.toLowerCase(), string.toLowerCase()) >=0.8 || this.name.toLowerCase().contains(string.toLowerCase())) {
			
			return true;
		}
		
		return false;
		
	}
	
}
