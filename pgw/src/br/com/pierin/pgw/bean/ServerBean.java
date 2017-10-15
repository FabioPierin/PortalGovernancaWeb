package br.com.pierin.pgw.bean;

import java.io.Serializable;

public class ServerBean implements Serializable{
	private static final long serialVersionUID = -1832877155170665768L;

	private String url;
	private String user = "";
	private String password; 
	private String port;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	} 

	
}
