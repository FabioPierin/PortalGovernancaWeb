package br.com.pierin.pgw.DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import br.com.pierin.pgw.bean.ServerBean;

public class ServerDAO implements Serializable {

	private static final long serialVersionUID = 5330716802332782484L;
	static Logger LOG = Logger.getLogger(ServerDAO.class);
	
	public boolean insertServer(ServerBean server) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean inserted = false;
		try {
			conn = ConnectionDB.getDbConnection();

			String sql = "INSERT INTO servers( SERVER_NAME, IP, PORT, ADM_USER, ADM_PASSWORD )"
					+ "VALUES ( ?, ?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, server.getServerName());
			stmt.setString(2, server.getUrl());
			stmt.setInt(3, server.getPort());
			stmt.setString(4, server.getUser());
			stmt.setString(5, server.getPassword());
			inserted = stmt.execute();
			inserted = true;
		} catch (ClassNotFoundException e) {
			LOG.error("Error saving data on DB", e);
			e.printStackTrace();
		} catch (SQLException e) {
			LOG.error("Error saving data on DB", e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.error("Unable to close the connection with DB.", e);
				e.printStackTrace();
			}
		}

		return inserted;
	}
	public boolean update(ServerBean server) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean inserted = false;
		try {
			conn = ConnectionDB.getDbConnection();
			
			String sql = "UPDATE servers set ADM_PORT=? where id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, server.getAdminPort());
			stmt.setInt(2, server.getId());
			inserted = stmt.execute();
			inserted = true;
		} catch (ClassNotFoundException e) {
			LOG.error("Error saving data on DB", e);
			e.printStackTrace();
		} catch (SQLException e) {
			LOG.error("Error saving data on DB", e);
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				LOG.error("Unable to close the connection with DB.", e);
				e.printStackTrace();
			}
		}
		
		return inserted;
	}
	public ArrayList<ServerBean> getAllServers() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ArrayList<ServerBean> serversList = new ArrayList<ServerBean>();
		ResultSet rs = null;
		try {
			conn = ConnectionDB.getDbConnection();
			
			String sql = "Select * from SERVERS";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()){
				ServerBean server = new ServerBean();
				server.setId(rs.getInt("ID"));
				server.setUrl(rs.getString("IP"));
				server.setServerName(rs.getString("SERVER_NAME"));
				server.setPort(rs.getInt("PORT"));
				server.setAdminPort(rs.getInt("ADM_PORT"));
				server.setUser(rs.getString("ADM_USER"));
				server.setPassword(rs.getString("ADM_PASSWORD"));
				serversList.add(server);
			}
		} catch (ClassNotFoundException e) {
			LOG.error("Error loading data from DB", e);
			serversList = null;
		} catch (SQLException e) {
			LOG.error("Error loading data from DB", e);
			serversList = null;
		} finally {
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (SQLException e) {
				LOG.error("Unable to close the connection with DB.", e);
				e.printStackTrace();
			}
		}
		
		return serversList;
	}

	public static void main(String[] args) {
		System.out.println("testing DB");
		ServerDAO ser = new ServerDAO();
		ServerBean servBean = new ServerBean();
		servBean.setPassword("pass123");
		servBean.setPort(666);
		servBean.setUrl("www.123");
		servBean.setUser("user");
		ser.insertServer(servBean);
	}
}
