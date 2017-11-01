package br.com.pierin.pgw.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class ApplicationBean implements Serializable{
	
	private static final long serialVersionUID = -4219940321184109530L;
	private Integer ID;
	private String name = "";
	private String uri = "";
	private Date inclusionDate;
	private ArrayList<StatusBean> status = new ArrayList<StatusBean>(); 
	private String console = ""; 
	private String description = "";
	private Integer port;
	private ServerBean server;
	private String currentState;
	
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Date getInclusionDate() {
		return inclusionDate;
	}
	public void setInclusionDate(Date inclusionDate) {
		this.inclusionDate = inclusionDate;
	}
	public ArrayList<StatusBean> getStatus() {
		return status;
	}
	public void setStatus(ArrayList<StatusBean> status) {
		this.status = status;
	}
	public String getConsole() {
		return console;
	}
	public void setConsole(String console) {
		this.console = console;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ServerBean getServer() {
		return server;
	}
	public void setServer(ServerBean server) {
		this.server = server;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}


	@Override
	  public boolean equals(Object v) {
	        boolean retVal = false;

	        if (v instanceof ApplicationBean){
	        	ApplicationBean ptr = (ApplicationBean) v;
	            retVal = ptr.name.equals(this.name);
	        }

	     return retVal;
	  }
	
	
	
	

}
