package br.com.pierin.pgw.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

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
	private Date lastStatusChange;
	private Double percentOnline = 0D;
	
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
	public Date getLastStatusChange() {
		return lastStatusChange;
	}
	public void setLastStatusChange(Date lastStatusChange) {
		this.lastStatusChange = lastStatusChange;
	}
	public Double getPercentOnline() {
		Long now = new Date().getTime();
		Long started = inclusionDate.getTime();
		Long timeOff = 0L;
		Boolean isInative = false;
		Long turnedOFF = 0L;
		for ( StatusBean st : this.status){
			if ("I".equals(st.getStatus())) {
				isInative = true;
				turnedOFF = st.getChanged_at().getTime();
			} else if (isInative) {
				timeOff =+ (st.getChanged_at().getTime() - turnedOFF);
				isInative = false;
			}
		}
		Long totalTime = now - started;
		if (isInative){
			totalTime = turnedOFF - started;
		} 
		Long timeOn = totalTime - timeOff;
		return (100D * timeOn / totalTime);
	}
	
	public static void main(String[] args) {
		ApplicationBean app = new ApplicationBean();
		app.setInclusionDate(new GregorianCalendar(2017, 9, 10).getGregorianChange());
		ArrayList<StatusBean> arrSt = new ArrayList<StatusBean>(); 
		
		StatusBean sb1 = new StatusBean();
		sb1.setChanged_at(new GregorianCalendar(2017, 9, 10).getGregorianChange());
		sb1.setStatus("A");
		arrSt.add(sb1);
		
		StatusBean sb2 = new StatusBean();
		sb2.setChanged_at(new GregorianCalendar(2017, 10, 10).getGregorianChange());
		sb2.setStatus("I");
		arrSt.add(sb2);
		
		app.setStatus(arrSt);

		System.out.println(app.getPercentOnline());
	}

}
