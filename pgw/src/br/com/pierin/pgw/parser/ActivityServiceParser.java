package br.com.pierin.pgw.parser;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import br.com.pierin.pgw.bean.ActivityBean;
import br.com.pierin.pgw.controller.AbstractController;

@Service
public class ActivityServiceParser  implements Serializable{
	private static final long serialVersionUID = 451092731993800544L;
	
	static Logger LOG = Logger.getLogger(ActivityServiceParser.class);
	private static final String ENTRY = "entry";
	private static final String CONTRIBUTOR = "contributor";
	private static final String EMAIL = "email";
	private static final String TITLE = "title";
	private static final String UUID = "id";
	private static final String LINK = "link";
	private static final String HREF = "href";
	private static final String PUBLISHED = "published";
	private static final String PERMISSIONS = "snx:permissions";
	private static final String ACTIVITY_OWNER = "activity_owner";
	private static final String LAST_UPDATE = "updated";
			
	public List<String> doParserCoOwner(InputStream response) {
		List<String> coowners = new ArrayList<String>();
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(response);
			NodeList serviceList = doc.getChildNodes();
			NodeList nodeList = serviceList.item(0).getChildNodes();
			for (int j = 0; j < nodeList.getLength(); j++) {
				Node subItem = nodeList.item(j);
				if (ENTRY.equals(subItem.getNodeName())) {
					for (int i = 0; i < subItem.getChildNodes().getLength(); i++) {
						Node child = subItem.getChildNodes().item(i);
						if (CONTRIBUTOR.equals(child.getNodeName())) {
							for (int z = 0; z < child.getChildNodes().getLength(); z++) {
								Node childItem = child.getChildNodes().item(z);
								if (EMAIL.equals(childItem.getNodeName())) {
									coowners.add(childItem.getChildNodes().item(0).getNodeValue());
								}
							}
						}	
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}

		return coowners;

	}


	public List<ActivityBean> doParser(InputStream response) {
		List<ActivityBean> list = new ArrayList<ActivityBean>();
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(response);
			NodeList serviceList = doc.getChildNodes();
			NodeList nodeList = serviceList.item(0).getChildNodes();
			for (int j = 0; j < nodeList.getLength(); j++) {
				Node subItem = nodeList.item(j);

				if (ENTRY.equals(subItem.getNodeName())) {
					ActivityBean bean = new ActivityBean();
					for (int i = 0; i < subItem.getChildNodes().getLength(); i++) {
						Node child = subItem.getChildNodes().item(i);
						if (LAST_UPDATE.equals(child.getNodeName())) {

						} else
						if (UUID.equals(child.getNodeName())) {
							String id = child.getChildNodes().item(0).getNodeValue();
							list.add(bean);
						} else if (TITLE.equals(child.getNodeName())) {
							bean.setTitle(child.getChildNodes().item(0).getNodeValue());
						} else if (LINK.equals(child.getNodeName())) {
							if(child.getAttributes().getNamedItem("rel").getNodeValue().equals("alternate")){
								String value = child.getAttributes().getNamedItem(HREF).getNodeValue();
								bean.setUrl(value);
							}
						} else if (PUBLISHED.equals(child.getNodeName())){
							bean.setCreationDate(child.getChildNodes().item(0).getNodeValue());
							
						} 
						else if (PERMISSIONS.equals(child.getNodeName())){
							if (child.getChildNodes().item(0).getNodeValue() != null 
									&& !child.getChildNodes().item(0).getNodeValue().contains(ACTIVITY_OWNER)){
								list.remove(bean);
							}
							
						}
						
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}

		return list;

	}
	public List<ActivityBean> doParserWithoutDate(InputStream response) {
		List<ActivityBean> list = new ArrayList<ActivityBean>();
		try {
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(response);
			NodeList serviceList = doc.getChildNodes();
			NodeList nodeList = serviceList.item(0).getChildNodes();
			for (int j = 0; j < nodeList.getLength(); j++) {
				Node subItem = nodeList.item(j);
				
				if (ENTRY.equals(subItem.getNodeName())) {
					ActivityBean bean = new ActivityBean();
					for (int i = 0; i < subItem.getChildNodes().getLength(); i++) {
						Node child = subItem.getChildNodes().item(i);
						if (TITLE.equals(child.getNodeName())) {
							bean.setTitle(child.getChildNodes().item(0).getNodeValue());
							list.add(bean);
						} else if (LINK.equals(child.getNodeName())) {
							if(child.getAttributes().getNamedItem("rel").getNodeValue().equals("alternate")){
								String value = child.getAttributes().getNamedItem(HREF).getNodeValue();
								bean.setUrl(value);
							}
						} 
						else if (PERMISSIONS.equals(child.getNodeName())){
							if (child.getChildNodes().item(0).getNodeValue() != null 
									&& !child.getChildNodes().item(0).getNodeValue().contains(ACTIVITY_OWNER)){
								list.remove(bean);
							}
							
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		
		return list;
		
	}

}