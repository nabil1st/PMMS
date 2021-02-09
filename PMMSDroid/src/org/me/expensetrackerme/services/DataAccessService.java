package org.me.expensetrackerme.services;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import trackit.businessobjects.Payee;
import org.me.expensetrackerme.xml.parser.PayeesHandler;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParserException;

public class DataAccessService {
	
	private static String GET_ALL_PAYEES_METHOD_NAME = "getPayeesForAccount";	
	private static String GET_ALL_PAYEES_SOAP_ACTION = "http://webservices.trackit" + 
		GET_ALL_PAYEES_METHOD_NAME; 

	private String NAMESPACE = "http://webservices.trackit"; 
	
	// you must use ipaddress here, don't user Hostname or localhost
	private static final String URL = "http://10.15.14.48:8080/pmms/services/expensereports?wsdl"; 
	
	
	private static DataAccessService instance = new DataAccessService();
	
	private DataAccessService() {}
	
	public static DataAccessService getInstance() {
		return instance;
	}
	
	public List<Payee> getAllPayees() throws IOException, XmlPullParserException{
		
//		SoapObject request = new SoapObject(NAMESPACE, GET_ALL_PAYEES_METHOD_NAME);
//		request.addProperty("accountId", "39");
//		
//		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//		envelope.setOutputSoapObject(request);
//		
//		AndroidHttpTransport transport = new AndroidHttpTransport(URL);
//		transport.call(GET_ALL_PAYEES_SOAP_ACTION, envelope);
//		
//		
//		SoapObject bodyIn = (SoapObject) envelope.bodyIn;
//		
//		// Loop thru main collection
//		for (int i=0; i<bodyIn.getPropertyCount(); i++) {
//			// Get item from main collection
//			PropertyInfo pi = new PropertyInfo();
//			bodyIn.getPropertyInfo(i, pi);
//			
//			SoapObject value = (SoapObject) pi.getValue();
//			
//			// Get item's attributes
//			PropertyInfo descPi = new PropertyInfo();
//			value.getPropertyInfo(0, descPi);
//			String description = descPi.getValue().toString();
//			
//			PropertyInfo idPi = new PropertyInfo();
//			value.getPropertyInfo(1, idPi);
//			String id = idPi.getValue().toString();
//			
//			
//		}
//		
////		SoapObject result = (SoapObject) envelope.getResponse();
//		
////		KvmSerializable ks = (KvmSerializable) envelope.bodyIn;
////		
////		KvmSerializable prop = (KvmSerializable) ks.getProperty(0);
////		
////		for (int i=0; i<result.getPropertyCount(); i++) {
////			SoapObject obj = (SoapObject) result.getProperty(i);
////			String name = obj.getProperty(0).toString();
////			String id = obj.getProperty(1).toString();
////			System.out.println(name + "," + id);
////		}
//		
//		
//		
//		
//		return new ArrayList<Payee>();
		
		
//		if (payees != null) {
//			return payees;
//		}
		
		String url = "http://10.15.14.48:8080/pmms/rest" + "/" + "payees" + "/" + "39";
//			AccountInfo.getInstance().getAccountId();
		String xml = RestClient.callRESTService(url);
		
		// parse xml String
		SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            PayeesHandler handler = new PayeesHandler();
            parser.parse(new InputSource(new StringReader(xml)), handler);
            return handler.getPayees();            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
		
	}
	
	

}
