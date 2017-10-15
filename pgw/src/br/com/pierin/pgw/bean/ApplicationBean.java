package br.com.pierin.pgw.bean;

import java.io.Serializable;

public class ApplicationBean implements Serializable{
	private static final long serialVersionUID = 5638984652255512891L;
	
//	nome : 'aplicação 1',
//	url : '10.9.8.7/app1',
//	data : '20/08/2017',
//	status : 'ativo',
//	console : '10.9.8.7:6070/console',
//	descricao : 'Descrição da aplicação 1'}
	
	
	private String name = "";
	private String url = "";
	private String date = "";
	private String status = ""; 
	private String console = ""; 
	private String description = "";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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



	

}
