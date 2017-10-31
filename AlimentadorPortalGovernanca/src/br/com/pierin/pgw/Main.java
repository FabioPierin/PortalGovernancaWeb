package br.com.pierin.pgw;

import java.util.ArrayList;

import javax.management.InstanceNotFoundException;

import br.com.pierin.pgw.DAO.ServerDAO;
import br.com.pierin.pgw.bean.ServerBean;

import com.ibm.websphere.management.exception.AdminException;
import com.ibm.websphere.management.exception.ConnectorException;

public class Main {

	public static void main(String[] args) {

		ServerInfoLoader serverInfoLoader = new ServerInfoLoader();
		// Establish an AdminClient connected to a server.
		ServerDAO srvDAO = new ServerDAO();
		ArrayList<ServerBean> serverList = srvDAO.getAllServers();

		for (ServerBean server : serverList) {
			try {
				serverInfoLoader.loadInfo(server.getUrl(), server.getPort(),
						server.getUser(), server.getPassword());
			} catch (Exception e) {
				System.out.println("Não foi possível conectar ao servidor:"
						+ server.getUrl());
				e.printStackTrace();
			}
		}

	}

}
