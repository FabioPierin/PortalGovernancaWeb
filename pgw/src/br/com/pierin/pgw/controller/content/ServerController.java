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

import br.com.pierin.pgw.DAO.ServerDAO;
import br.com.pierin.pgw.bean.ServerBean;
import br.com.pierin.pgw.controller.AbstractController;

@Controller
public class ServerController extends AbstractController implements Serializable {

	private static final long serialVersionUID = 1850797942132492953L;

	static Logger LOG = Logger.getLogger(ServerController.class);

	
	@RequestMapping(method = RequestMethod.POST, value = "/servers/add", consumes = "application/json;charset=UTF-8")
	public @ResponseBody Object addNewServer(@RequestBody ServerBean input,
			HttpServletResponse response) {

		Map<String, Object> result = new HashMap<String, Object>();

		LOG.info("Including new server. URL:" + input.getUrl()+":"+input.getPort());

		boolean included = new ServerDAO().insertServer(input);
		result.put("included", included);

		return jsonView.render(result, response);

	}
	@RequestMapping(method = RequestMethod.GET, value = "/servers/getAll")
	public @ResponseBody Object getAllServers(HttpServletResponse response) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		LOG.info("Getting a list of servers");
		
		ArrayList<ServerBean> list = new ServerDAO().getAllServers();
		if (list == null){
			result.put("error", "Falha ao conectar com o banco de dados."); 
		} else if (list.isEmpty()) {
			result.put("error", "Não há servidores cadastrados!");
		} else {
			result.put("serversList", list);
		}
		
		return jsonView.render(result, response);
		
	}
	@RequestMapping(value = "/servidores", method = RequestMethod.GET)
	public ModelAndView servidores(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		this.jsonView.noCache(response);

		return new ModelAndView("servidores", model);

	}

}
