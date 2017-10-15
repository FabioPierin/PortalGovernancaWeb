package br.com.pierin.pgw.controller.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.pierin.pgw.controller.AbstractController;

@Controller
public class ActivityController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 1850797942132492953L;

	static Logger LOG = Logger.getLogger(ActivityController.class);

	
	@RequestMapping(method = RequestMethod.GET, value = "/api/activities/item/{uuid}")
	public Object getCoOwnerCommunity(@PathVariable String uuid, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<String> coowners = new ArrayList<String>();
		coowners.add("element1");
		coowners.add("element2");
		coowners.add("element3");
		result.put("coowners", coowners);
		

	return jsonView.render(result, response);
	}


}
