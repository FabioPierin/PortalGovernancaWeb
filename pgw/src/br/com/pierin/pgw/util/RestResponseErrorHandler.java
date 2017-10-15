package br.com.pierin.pgw.util;

import java.io.IOException;
import java.io.Serializable;

import javax.security.sasl.AuthenticationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestResponseErrorHandler implements ResponseErrorHandler, Serializable {

	private static final long serialVersionUID = 6204528839567040145L;
	private static final Log LOG = LogFactory.getLog(RestResponseErrorHandler.class);

	@Override
	public void handleError(ClientHttpResponse clienthttpresponse) throws IOException {

		if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
			LOG.debug(HttpStatus.FORBIDDEN + " response. Throwing authentication exception");
			throw new AuthenticationException();
		}
	}

	@Override
	public boolean hasError(ClientHttpResponse clienthttpresponse) throws IOException {

		if (clienthttpresponse.getStatusCode() != HttpStatus.OK) {
			LOG.debug("Status code: " + clienthttpresponse.getStatusCode());
			LOG.debug("Response" + clienthttpresponse.getStatusText());
			LOG.debug(clienthttpresponse.getBody());

			if (clienthttpresponse.getStatusCode() == HttpStatus.FORBIDDEN) {
				LOG.debug("Call returned a error 403 forbidden resposne ");
				return true;
			}
		}
		return false;
	}
}