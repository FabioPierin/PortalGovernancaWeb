package br.com.pierin.pgw.util;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;

import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.axis.handlers.BasicHandler;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
public class RequestResponseHandler extends BasicHandler {

	static Logger LOG = Logger.getLogger(RequestResponseHandler.class);
  
	private static final long serialVersionUID = 5331536042913144899L;

	@Override
    public void init() {
        super.init();
        LOG.info("init called");
    }

    @Override
    public void cleanup() {
        super.cleanup();
        LOG.info("cleanup called");
    }

    public QName[] getHeaders() {
    	LOG.info("getHeaders");
        return new QName[1];
    }

	@Override
	public void invoke(MessageContext msgContext) throws AxisFault {
		try {
			ByteArrayOutputStream writeSOAPToLog = new ByteArrayOutputStream();
			SOAPMessage msg = msgContext.getMessage();
			msg.writeTo(writeSOAPToLog);
			LOG.info("request:["+writeSOAPToLog.toString()+"]");

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	
}