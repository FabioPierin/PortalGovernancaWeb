package br.com.pierin.pgw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;

import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.ObjectName;

import br.com.pierin.pgw.DAO.ApplicationDAO;
import br.com.pierin.pgw.DAO.ServerDAO;
import br.com.pierin.pgw.bean.ApplicationBean;
import br.com.pierin.pgw.bean.ServerBean;

import com.ibm.websphere.management.AdminClient;
import com.ibm.websphere.management.AdminClientFactory;
import com.ibm.websphere.management.Session;
import com.ibm.websphere.management.application.AppManagement;
import com.ibm.websphere.management.application.AppManagementProxy;
import com.ibm.websphere.management.configservice.ConfigService;
import com.ibm.websphere.management.configservice.ConfigServiceHelper;
import com.ibm.websphere.management.configservice.ConfigServiceProxy;
import com.ibm.websphere.management.exception.ConfigServiceException;
import com.ibm.websphere.management.exception.ConnectorException;

public class ServerInfoLoader {
	private static AdminClient adminClient;
	private HashMap<String, Integer> ports = new HashMap<String, Integer>();
	Session session = new Session();
	ConfigService configService;

	public void loadInfo(ServerBean server) throws Exception {
		this.createAdminClient(server.getUrl(), server.getPort(),
				server.getUser(), server.getPassword());

		// Create a config service proxy object.
		configService = new ConfigServiceProxy(adminClient);

		Hashtable<?, ?> prefs = new Hashtable<Object, Object>();

		adminClient.getDefaultDomain();

		getPorts();

		if ( server.getAdminPort() == null || server.getAdminPort() != ports.get("admin_host")){
			server.setAdminPort(ports.get("admin_host"));
			new ServerDAO().update(server);
		}
		
		AppManagement proxy = AppManagementProxy
				.getJMXProxyForClient(adminClient);
		Vector<?> applications = proxy.listApplications(prefs, null);
		ApplicationDAO appDAO = new ApplicationDAO();
		ArrayList<ApplicationBean> appBeanList = appDAO.getAllApplications(server.getId());
		for (Object app : applications) {
			String appName = app.toString();
			if (!appName.equals("query")
					&& !appName.equals("ivtApp")
					&& !appName.equals("DefaultApplication")
					&& !appName.equals("IBMUTC")) {
				
				ObjectName on = new ObjectName("WebSphere:*,name="
						+ appName + "*,type=SessionManager");
				Iterator<?> iter = adminClient.queryNames(on, null).iterator();
				while (iter.hasNext()) {
					ObjectName appmgmtON = (ObjectName) iter.next();
					String uri = appmgmtON.getKeyProperty("mbeanIdentifier");
					int port = 0;
					Set<String> keys = ports.keySet();
					for (String key : keys) {
						if (uri.contains(key)) {
							uri = uri.replace(key, "");
							if (!uri.startsWith("/")) {
								uri = "/" + uri;
							}
							port = ports.get(key);
						}
					}
					ApplicationBean appBean = new ApplicationBean();
					appBean.setName(appName);
					int i = appBeanList.indexOf(appBean);
					if (i > -1){
						//UPDATE
						ApplicationBean updateAppBean = appBeanList.remove(i);
						updateAppBean.setPort(port);
						updateAppBean.setUri(uri);
						appDAO.updateApplication(updateAppBean);
						if ("I".equals(updateAppBean.getCurrentState())){
							appDAO.setActive(updateAppBean);
						}
					} else {
						//ADD
						appBean.setServer(server);
						appBean.setUri(uri);
						appBean.setPort(port);
						appDAO.addApp(appBean);
					}
					
				}

			}

		}
		if (!appBeanList.isEmpty()){
			for (ApplicationBean appB : appBeanList){
				if (appB.getCurrentState().equals("A")){
					appDAO.setInactive(appB);
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void getPorts() throws ConfigServiceException, ConnectorException,
			NumberFormatException, AttributeNotFoundException,
			NullPointerException {
		ObjectName oQueryPattern = ConfigServiceHelper.createObjectName(null, "VirtualHost", "");
		ObjectName[] results = configService.queryConfigObjects(session, null, oQueryPattern, null);
		for (ObjectName vHost : results) {
			List<AttributeList> hostAliases = (List<AttributeList>) configService
					.getAttribute(session, vHost, "aliases");
			ports.put(vHost
					.getKeyProperty("_Websphere_Config_Data_Display_Name"),
					Integer.parseInt((String) ConfigServiceHelper
							.getAttributeValue(hostAliases.get(0), "port")));
		}

	}


	private void createAdminClient(String address, Integer port, String user,
			String password) throws Exception {
		// Set up a Properties object for the JMX connector attributes
		Properties connectProps = new Properties();
		connectProps.setProperty(AdminClient.CONNECTOR_TYPE,
				AdminClient.CONNECTOR_TYPE_SOAP);
		connectProps.setProperty(AdminClient.CONNECTOR_SECURITY_ENABLED, address.startsWith("https")? "true":"false");
		connectProps.setProperty(AdminClient.CONNECTOR_HOST, address.replace("https://", ""));
		connectProps.setProperty(AdminClient.CONNECTOR_PORT, port.toString());
		connectProps.setProperty(AdminClient.USERNAME, user);
		connectProps.setProperty(AdminClient.PASSWORD, password);
		try {
			adminClient = AdminClientFactory.createAdminClient(connectProps);
		} catch (ConnectorException e) {
			System.out.println("Exception creating admin client: " + e);
			throw new Exception(e);
		}

		System.out.println("Connected to DeploymentManager");
	}
}
