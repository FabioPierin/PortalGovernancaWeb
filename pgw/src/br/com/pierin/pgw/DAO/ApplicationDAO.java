package br.com.pierin.pgw.DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import br.com.pierin.pgw.bean.ApplicationBean;
import br.com.pierin.pgw.bean.ServerBean;
import br.com.pierin.pgw.bean.StatusBean;

public class ApplicationDAO implements Serializable {

	private static final long serialVersionUID = 5330716802332782484L;
	static Logger LOG = Logger.getLogger(ApplicationDAO.class);
	
	public boolean updateApplication(ApplicationBean app) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean updated = false;
		try {
			conn = ConnectionDB.getDbConnection();

			String sql = "UPDATE applications where id = ? set "
					+ "DESCRIPTION = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, app.getID());
			stmt.setString(2, app.getDescription());
			stmt.executeUpdate();
			updated = true;
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

		return updated;
	}
	
	public ArrayList<ApplicationBean> getAllApplications() {
		Connection conn = null;
		PreparedStatement stmt = null;
		PreparedStatement stmtSTATUS = null;
		ArrayList<ApplicationBean> appList = new ArrayList<ApplicationBean>();
		ResultSet rs = null;
		ResultSet rsSTATUS = null;
		try {
			conn = ConnectionDB.getDbConnection();
			
			String sql = "select "
					+ "A.ID as app_id, A.APP_NAME, A.URI, A.DESCRIPTION, A.INCLUSION_DATE, A.PORT, "
					+ "s.ID as serv_ID, s.SERVER_NAME, s.IP, s.PORT as adm_port, s.ADM_USER, s.ADM_PASSWORD  "
					+ "from APPLICATIONS a, SERVERS s where a.server_id = s.id";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			String sqlSTATUS = "select * "
					+ "from STATUS where APPLICATION_ID = ? order by CHANGED_AT";
			stmtSTATUS = conn.prepareStatement(sqlSTATUS);
			while (rs.next()){
				ApplicationBean app = new ApplicationBean();
				ServerBean server = new ServerBean();

				stmtSTATUS.setInt(1, rs.getInt("APP_ID"));
				rsSTATUS = stmtSTATUS.executeQuery();
				while (rsSTATUS.next()){
					StatusBean sb = new StatusBean();
					sb.setStatus(rsSTATUS.getString("status"));
					sb.setChanged_at(rsSTATUS.getDate("CHANGED_AT"));
					app.setCurrentState(rsSTATUS.getString("status"));
					app.getStatus().add(sb);
				}
				
				server.setId(rs.getInt("SERV_ID"));
				server.setServerName(rs.getString("SERVER_NAME"));
				server.setUrl(rs.getString("IP"));
				server.setPort(rs.getString("adm_port"));
				app.setID(rs.getInt("APP_ID"));
				app.setName(rs.getString("APP_NAME"));
				app.setUri(rs.getString("URI"));
				app.setPort(rs.getInt("PORT"));
				app.setInclusionDate(rs.getDate("INCLUSION_DATE"));
				app.setDescription(rs.getString("description"));
				app.setServer(server);
				appList.add(app);
			}
		} catch (ClassNotFoundException e) {
			LOG.error("Error loading data from DB", e);
			appList = null;
		} catch (SQLException e) {
			LOG.error("Error loading data from DB", e);
			appList = null;
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
		
		return appList;
	}

}
