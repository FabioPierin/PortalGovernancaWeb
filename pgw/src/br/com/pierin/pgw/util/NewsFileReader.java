package br.com.pierin.pgw.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class NewsFileReader implements Serializable {
	
	private static final long serialVersionUID = 5517662377801696510L;
	static Logger LOG = Logger.getLogger(NewsFileReader.class);
	private static final String NEWS_FILE_PATH = "news.file";
	
	
	@Autowired
	protected MessageSource configurationSource;
	
	public void setConfigurationSource(MessageSource configurationSource) {
		this.configurationSource = configurationSource;
	}
	
	public String getContent() {

		BufferedReader br = null;
		String sCurrentLine = "";
		String fileContent = "";
		String path = "";
		LOG.debug("path = " + path);
		try {
			path = configurationSource.getMessage(NEWS_FILE_PATH , null, Locale.getDefault());
		} catch (java.lang.NullPointerException e) {
			// TODO Auto-generated catch block
			path = "/gsa/bldgsa/projects/l/lcw3.admin/dst_beta/projects/w3beta/content/connections/static/snt/properties/news.html";
			LOG.info("Hard code news path in " + path);
			e.printStackTrace();
		}
		LOG.debug("path =" + path);

		try {
			br = new BufferedReader(new FileReader(path));
			while ((sCurrentLine = br.readLine()) != null) {
				fileContent = fileContent + sCurrentLine;
			}

		} catch (IOException e) {
			LOG.info("File not found in " + path);
			fileContent = "<p>News file not found in" + path + "</p>";
			LOG.debug(fileContent);
			LOG.info("News file not found in" + path);
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return fileContent;

	}
}
