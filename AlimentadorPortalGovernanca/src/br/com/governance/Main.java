package br.com.governance;

import javax.management.InstanceNotFoundException;

import com.ibm.websphere.management.exception.AdminException;
import com.ibm.websphere.management.exception.ConnectorException;

public class Main {
	


	public static void main(String[] args) {
		
		ServerInfoLoader serverInfoLoader = new ServerInfoLoader();		
		// Establish an AdminClient connected to a server.
		try {
			serverInfoLoader.loadInfo("localhost", "8881", "admin", "");
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (ConnectorException e) {
			e.printStackTrace();
		} catch (AdminException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	

}
