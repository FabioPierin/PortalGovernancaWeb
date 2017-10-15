package br.com.pierin.pgw.util;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JsonView  implements Serializable{
	
	private static final long serialVersionUID = -8610640090553705111L;
	private static final Logger LOG = Logger.getLogger(JsonView.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelAndView render(Object model, HttpServletResponse response, ObjectMapper objectMapper) {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		
        MediaType jsonMimeType = MediaType.APPLICATION_JSON;

        try {
        	this.noCache(response);
        	
        	if(model instanceof Map){
            	Map result = (Map)model;
            }
        	
        	
        	if(objectMapper != null) {
        		jsonConverter.setObjectMapper(objectMapper);
        	}
        	jsonConverter.write(model, jsonMimeType, new ServletServerHttpResponse(response));
        } catch (HttpMessageNotWritableException e) {
        	LOG.error(e);
        	e.printStackTrace();
        } catch (IOException e) {
        	LOG.error(e);
            e.printStackTrace();
        }
        if(model instanceof List){
        	List instance = (List)model;
        	instance.clear();
        }
        if(model instanceof Map){
        	Map instance = (Map)model;
        	instance.clear();
        }
        
        return null;
	}
	
	public void noCache(HttpServletResponse response){
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setHeader("Cache-Control", "no-cache, private, no-store, must-revalidate"); // HTTP 1.1 
		httpResponse.setHeader("Pragma", "no-cache"); 
		httpResponse.setDateHeader("Expires", 0);  
	}
	
    public ModelAndView render(Object model, HttpServletResponse response) {
        return render(model, response, null);
    }
    

}   