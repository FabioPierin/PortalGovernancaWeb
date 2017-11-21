package br.com.pierin.pgw.bean;

import java.io.Serializable;

public class ServerBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7581582379162306548L;

	/**
	 * ID column 
	 */
	private Integer id;
	
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
	private Integer port;
	private Integer adminPort;
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
	public Integer getPort() {
		return port;
	}
	/**
	 *  Value of PORT column
	 * @param port
	 */
	public void setPort(Integer port) {
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
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAdminPort() {
		return adminPort;
	}
	public void setAdminPort(Integer adminPort) {
		this.adminPort = adminPort;
	} 
	
	

	
}
