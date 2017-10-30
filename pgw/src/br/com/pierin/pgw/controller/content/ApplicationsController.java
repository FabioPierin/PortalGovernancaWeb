package br.com.pierin.pgw.controller.content;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import br.com.pierin.pgw.DAO.ApplicationDAO;
import br.com.pierin.pgw.DAO.ServerDAO;
import br.com.pierin.pgw.bean.ApplicationBean;
import br.com.pierin.pgw.bean.ServerBean;
import br.com.pierin.pgw.controller.AbstractController;

@Controller
public class ApplicationsController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 1850797942132492953L;

	static Logger LOG = Logger.getLogger(ApplicationsController.class);

	@RequestMapping(value = "/aplicacoes", method = RequestMethod.GET)
	public ModelAndView aplicacoes(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		this.jsonView.noCache(response);

		return new ModelAndView("aplicacoes", model);

	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/applications/all")
	public Object getAllApplications(HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		ArrayList<ApplicationBean> list = new ApplicationDAO().getAllApplications();
		if (list == null){
			result.put("error", "Falha ao conectar com o banco de dados."); 
		} else if (list.isEmpty()) {
			result.put("error", "Não há aplicações cadastradas!");
		} else {
			result.put("appList", list);
		}

	return jsonView.render(result, response);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/applications/update", consumes = "application/json;charset=UTF-8")
	public @ResponseBody Object addNewServer(@RequestBody ApplicationBean input,
			HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();

		LOG.info("Updating application description. AppID:" + input.getID()+" - " + input.getDescription());

		boolean updated = new ApplicationDAO().updateApplication(input);
		result.put("updated", updated);

		return jsonView.render(result, response);

	}
}
