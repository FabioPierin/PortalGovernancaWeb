package br.com.pierin.pgw.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PGWController extends AbstractController  implements Serializable{

	private static final long serialVersionUID = 1759784568515280418L;

	static Logger LOG = Logger.getLogger(PGWController.class);
	private static final String HTTP_BASE = "w3c.hostname";
	protected static final String HTTP_BUSINESSCARD_API = "w3c.businesscard.integration.url";
	protected static final String HTTPS = "http://";


	@Autowired
	protected MessageSource configurationSource;

	
	public void setConfigurationSource(MessageSource configurationSource) {
		this.configurationSource = configurationSource;
	}

	
}
