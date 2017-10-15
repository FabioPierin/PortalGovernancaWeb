package br.com.pierin.pgw.rpc.integration;

import java.io.Serializable;
import java.util.Locale;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.description.OperationDesc;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import br.com.pierin.pgw.util.RequestResponseHandler;

public abstract class AbstractRPCBluePagesInterceptor implements Serializable  {

	static Logger LOG = Logger.getLogger(AbstractRPCBluePagesInterceptor.class);

	private static final long serialVersionUID = -1152818181906608770L;
	
	private static String EMAIL_SIMPLE_URL = "w3c.bluepages.simple.url";

	@Autowired
	protected MessageSource configurationSource;

	protected Call serviceClient;

	public void setConfigurationSource(MessageSource configurationSource) {
		this.configurationSource = configurationSource;
	}

	protected void buildCallService() {
		String endpoint = configurationSource.getMessage(getResourceEndPoint(), null, Locale.getDefault());
		String simpleURL = configurationSource.getMessage(EMAIL_SIMPLE_URL, null, Locale.getDefault());
		String timeout = configurationSource.getMessage("bpemailservice.http.connectiontimeout", null, Locale.getDefault());

		if (serviceClient == null) {

			Service service = new Service();
			try {
				serviceClient = (Call) service.createCall();

				serviceClient.setTargetEndpointAddress(new java.net.URL(endpoint));
				
				OperationDesc oper = new OperationDesc();
			        oper.setName("send");
			        
			        oper.addParameter(new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "myTo"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false));
			        oper.addParameter(new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "myCC"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false));
			        oper.addParameter(new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "myBCC"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false));
			        oper.addParameter(new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "myFrom"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false));
			        oper.addParameter(new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "mySub"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false));
			        oper.addParameter(new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "myMsg"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false));
			        oper.addParameter(new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "html"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false));
			        oper.addParameter(new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "sendNow"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false));
			        oper.addParameter(new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "retryCount"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"), int.class, false, false));
			        
			        oper.setReturnType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
			        oper.setReturnClass(java.lang.String.class);
			        oper.setReturnQName(new javax.xml.namespace.QName("", "sendReturn"));
			        oper.setStyle(org.apache.axis.constants.Style.RPC);
			        oper.setUse(org.apache.axis.constants.Use.ENCODED);
			        
			        serviceClient.setOperation(oper);
			        
			        serviceClient.setUseSOAPAction(true);
			        serviceClient.setSOAPActionURI("");
			        serviceClient.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
			        serviceClient.setOperationName(new javax.xml.namespace.QName(simpleURL, "send"));
			        serviceClient.setTimeout(Integer.valueOf(timeout));
			        
			        if(LOG.isInfoEnabled()){
			        	serviceClient.setClientHandlers(new RequestResponseHandler(), null);
			        }
			        
			} catch (Exception e) {
				LOG.error(e);
			}

		}

	}

	protected abstract String getResourceEndPoint();

}
