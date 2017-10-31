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
//	private String workSpaceID = "";
//	private ArrayList<String> serversID = new ArrayList<String>();
	private HashMap<String, Integer> ports = new HashMap<String, Integer>();
	Session session = new Session();
	ConfigService configService;

	public void loadInfo(String address, String port, String user,
			String password) throws Exception {
		this.createAdminClient(address, port, user, password);

		// Create a config service proxy object.
		configService = new ConfigServiceProxy(adminClient);
		

		
			Hashtable prefs = new Hashtable();

			adminClient.getDefaultDomain();
//			ObjectName server = adminClient.getServerMBean();
			
			getPorts();
			
			AppManagement proxy = AppManagementProxy.getJMXProxyForClient(adminClient);
			Vector applications = proxy.listApplications(prefs, null);
			
			for (Object app : applications) {
				if (!app.toString().equals("query") && !app.toString().equals("ivtApp") && !app.toString().equals("DefaultApplication") && !app.toString().equals("IBMUTC")){
					ObjectName on = new ObjectName("WebSphere:*,name="+app.toString()+"*,type=SessionManager");// ("WebSphere:type=AppManagement,*");
					Iterator iter = adminClient.queryNames (on, null).iterator();
					while (iter.hasNext()){
						ObjectName appmgmtON = (ObjectName)iter.next();
						System.out.println("==================================== ");
//						System.out.println(appmgmtON.getKeyPropertyListString());
//						System.out.println(appmgmtON.getCanonicalName());
						String uri = appmgmtON.getKeyProperty("mbeanIdentifier");
						Set<String> keys = ports.keySet();
						for (String key : keys) {
							if (uri.contains(key)) {
								uri = uri.replace(key, "");
								if (!uri.startsWith("/")) {
									uri = "/" + uri;
								}
								uri = address + ":" + ports.get(key) + uri;
							}
						}
						System.out.println(uri);
//						System.out.println(proxy.getApplicationInfo(app.toString(), prefs, null));
					}
					
					
//					System.out.println(proxy.listURIs(app.toString(), null, prefs, session.getSessionId()));
//					System.out.println(proxy.listModules(app.toString(), prefs, null));
//					byte[] content = proxy.getApplicationContents(app.toString(), ".settings/org.eclipse.wst.common.component", prefs, session.getSessionId());
//					String cont = new String(content, "UTF-8");
//					System.out.println(cont);
//					content = proxy.getApplicationContents(app.toString(), ".settings/org.eclipse.wst.common.project.facet.core.xml", prefs, session.getSessionId());
//					cont = new String(content, "UTF-8");
//					System.out.println(cont);
				}
				
				
			}
			
//			AppManagement appMgmt = AppManagementProxy.getLocalProxy();
////			Hashtable prefs = new Hashtable(); 
//			prefs.put(AppConstants.APPDEPL_LOCALE, Locale.getDefault());
//			appMgmt.listSystemApplications(prefs, "");
//			appMgmt.listApplications(prefs, serv);
//			appMgmt.listURIs("", "", null, "");
			
			
//			AppManagementProxy appMng;
//			appMng = new AppManagementProxy(adminClient, null);
			
//		}
	}
	
	private void getPorts() throws ConfigServiceException, ConnectorException, NumberFormatException, AttributeNotFoundException, NullPointerException {
		ObjectName oQueryPattern=ConfigServiceHelper.createObjectName(null, "VirtualHost", "");
		ObjectName[] results = configService.queryConfigObjects(session, null, oQueryPattern, null);
		for (ObjectName vHost : results) {
			List<AttributeList> hostAliases = (List<AttributeList>)configService.getAttribute(session, vHost, 
					"aliases");
			ports.put( vHost.getKeyProperty("_Websphere_Config_Data_Display_Name"), 
					Integer.parseInt((String) ConfigServiceHelper.getAttributeValue(hostAliases.get(0), "port")));
		}

	}
//	private String getHostNameforNode(String nodeName, Document DOM) {
//		String xPathQuery = 
//				String.format("//node[@name='%1$s']/property[@name='hostName']/@value", nodeName);
//		String hostNameforNode = JMXClientClass.executeXPATHQuery(DOM, xPathQuery);
//		
//		return hostNameforNode;
//	}
	
//public Document getTargetTreeMBean() {
//		
//		Document DOM = null;
//		try {
//			ObjectName endPointMgr = new ObjectName("WebSphere:*,type=TargetTreeMbean,process=dmgr");
//			Set<ObjectName> endPointMgrSet = adminClient.queryNames(endPointMgr, null);
//			int i = 0;
//			for (ObjectName objName : endPointMgrSet) {
//				System.out.println(objName.getCanonicalName());
//				
//				DOM = JMXClientClass.getDOMFromString(
//					(String)adminClient.invoke(objName, "getTargetTree", null, null));
//				break;
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return DOM;
//	}

	private void createAdminClient(String address, String port, String user,
			String password) {
		// Set up a Properties object for the JMX connector attributes
		Properties connectProps = new Properties();
		connectProps.setProperty(AdminClient.CONNECTOR_TYPE,
				AdminClient.CONNECTOR_TYPE_SOAP);
		connectProps.setProperty(AdminClient.CONNECTOR_HOST, address);
		connectProps.setProperty(AdminClient.CONNECTOR_PORT, port);
		connectProps.setProperty(AdminClient.USERNAME, user);
		connectProps.setProperty(AdminClient.PASSWORD, password);
		// System.setProperty("com.ibm.SSL.ConfigURL",
		// "file:C:/MyThinClient/70properties/ssl.client.props");
		// System.setProperty("com.ibm.SOAP.ConfigURL",
		// "file:C:/MyThinClient/70properties/soap.client.props");

		// Get an AdminClient based on the connector properties
		try {
			adminClient = AdminClientFactory.createAdminClient(connectProps);
		} catch (ConnectorException e) {
			System.out.println("Exception creating admin client: " + e);
			System.exit(-1);
		}

		System.out.println("Connected to DeploymentManager");
	}
}
