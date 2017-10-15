package br.com.pierin.pgw.controller.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.pierin.pgw.bean.ApplicationBean;
import br.com.pierin.pgw.controller.AbstractController;

@Controller
public class ApplicationsController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 1850797942132492953L;

	static Logger LOG = Logger.getLogger(ApplicationsController.class);

	
	@RequestMapping(method = RequestMethod.GET, value = "/applications/all")
	public Object getAllApplications(HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		ArrayList<ApplicationBean> apps = new ArrayList<ApplicationBean>();
		for (int i = 0; i < 10; i++){
			ApplicationBean a = new ApplicationBean();
			a.setName("app"+i);
			a.setUrl("url"+i);
			a.setDate("2017-10-"+i);
			a.setStatus("status"+i);
			a.setConsole("console"+i);
			a.setDescription("description"+i);
			apps.add(a);
		}
		result.put("apps", apps);
		

	return jsonView.render(result, response);
	}

}
