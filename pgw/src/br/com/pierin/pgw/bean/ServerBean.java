package br.com.pierin.pgw.bean;

import java.io.Serializable;

public class ServerBean implements Serializable{
	private static final long serialVersionUID = -1832877155170665768L;

	/**
	 * IP column 
	 */
	private String url;
	/**
	 * ADM_USER column 
	 */
	private String user = "";
	/**
	 * ADM_PASSWORD column 
	 */
	private String password;
	/**
	 * PORT column 
	 */
	private String port;
	/**
	 * SERVER_NAME column 
	 */
	private String serverName;
	
	
	public String getUrl() {
		return url;
	}
	/**
	 * Value of IP column 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getUser() {
		return user;
	}
	/**
	 *  Value of ADM_USER column
	 * @param user
	 */
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	/**
	 *  Value of ADM_PASSWORD column
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPort() {
		return port;
	}
	/**
	 *  Value of PORT column
	 * @param port
	 */
	public void setPort(String port) {
		this.port = port;
	}
	public String getServerName() {
		return serverName;
	}
	/**
	 *  Value of SERVER_NAME column
	 * @param serverName
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	} 
	
	

	
}
