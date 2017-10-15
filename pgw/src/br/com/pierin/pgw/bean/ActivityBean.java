package br.com.pierin.pgw.bean;

import java.io.Serializable;

public class ActivityBean implements Serializable{
	
	private static final long serialVersionUID = 3783406547867200538L;
	private String title;
	private String url;
	private String lastUpdate = "";
	private String creationDate; // YYYY-MM-DD

	public String getCreationDate() {
		if(creationDate == null){
			return "9999";
		}
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	

}
