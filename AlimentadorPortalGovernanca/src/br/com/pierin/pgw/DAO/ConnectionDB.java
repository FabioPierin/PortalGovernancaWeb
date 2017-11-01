package br.com.pierin.pgw.DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import oracle.jdbc.OracleDriver;

import org.apache.log4j.Logger;



public class ConnectionDB implements Serializable {
	static Logger LOG = Logger.getLogger(ConnectionDB.class);
	
	private static final long serialVersionUID = 2966057251352024275L;
	
	private static final String driver = "jdbc:oracle:thin:@//";
	private static final String port = "1521";
	private static ConnectionDB connection = new ConnectionDB();
	private static final String host = "localhost";
	private static final String userName = "ROOT";
	private static final String password = "root";

	public ConnectionDB() {
		try {
			DriverManager.registerDriver (new OracleDriver());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static java.sql.Connection getDbConnection() throws SQLException, ClassNotFoundException {
		
		Connection dbConnection = null;
		try {
			String dbURL = driver + host +":"+ port + "/xe";
			LOG.info("Getting db connection to " + dbURL);
			dbConnection = DriverManager.getConnection(dbURL, userName, password);
			LOG.info("db connection established!");
		} catch (SQLException e) {
			LOG.error("Error connecting to DB", e);
			throw e;
		}
		return dbConnection;
	}
	
	
}
