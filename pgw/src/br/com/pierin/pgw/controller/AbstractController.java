package br.com.pierin.pgw.controller;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.pierin.pgw.util.JsonView;

public class AbstractController implements Serializable {

	private static final long serialVersionUID = 6793689037907479051L;

	static Logger LOG = Logger.getLogger(AbstractController.class);

	@Autowired
	protected JsonView jsonView;

	public void setJsonView(JsonView jsonView) {
		this.jsonView = jsonView;
	}


}
