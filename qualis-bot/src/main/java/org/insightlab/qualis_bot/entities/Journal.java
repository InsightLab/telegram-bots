package org.insightlab.qualis_bot.entities;

import info.debatty.java.stringsimilarity.JaroWinkler;

public class Journal {

	private String issn;
	private String name;
	private String qualis;
	
	public Journal(String issn, String name, String qualis) {
		this.issn = issn;
		this.name = name;
		this.qualis = qualis;
	}
	
	public String getIssn() {
		return issn;
	}
	public void setIssn(String issn) {
		this.issn = issn;
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
		result = prime * result + ((issn == null) ? 0 : issn.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((qualis == null) ? 0 : qualis.hashCode());
		return result;
	}

	public boolean equals(String string) {
		
		JaroWinkler jw = new JaroWinkler();
		
		if(jw.similarity(this.issn.toLowerCase(), string.toLowerCase()) >=0.8 || this.issn.toLowerCase().contains(string.toLowerCase())) {
			
			return true;
		}
		
		if(jw.similarity(this.name.toLowerCase(), string.toLowerCase()) >=0.8 || this.name.toLowerCase().contains(string.toLowerCase())) {
			
			return true;
		}
		
		return false;
		
	}
	
	
	
}
