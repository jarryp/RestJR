package com.palaciossystems.ws.rest.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.palaciossystems.ws.rest.vo.VOUsuario;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class RestClient {

	public static void main(String[] args) {
		Date xfecha = new Date();
		System.out.println(RestClient.cifradoSha256("palabrasecreta"));
		System.out.println(RestClient.formatoMilFecha(xfecha));
		System.out.println(RestClient.formatoHoraUtc(xfecha));
		
		String xcadena = RestClient.formatoMilFecha(xfecha)+RestClient.formatoHoraUtc(xfecha);
		System.out.println(RestClient.cifradoSha256(xcadena));
		
		VOUsuario vo = new VOUsuario();
		vo.setUsuario("Java");
		vo.setPasswdor("palacios");
		vo.setUserValido(false);
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client client = Client.create(clientConfig);
		WebResource webResource = client.resource("http://localhost:8080/RestJR/services/palacios/validaUsuario");
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, vo);
		vo = response.getEntity(VOUsuario.class);
		
		System.out.println("###### Response: [Usuario   : "+vo.getUsuario()+"]");
		System.out.println("###### Response: [Validación: "+vo.isUserValido()+"]");
		

	
	}
	

	public static String cifradoSha256(String clave){
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
			try {
				sha256.update(clave.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException e) {
				return "Vacio";
			}
			byte[] digest = sha256.digest();
			StringBuffer sb=new StringBuffer();
			for(int i=0;i<digest.length;i++){
			    sb.append(String.format("%02x", digest[i]));
			}
			String hash=sb.toString();
			return hash;
		} catch (NoSuchAlgorithmException e) {
			return "Vacio";
		}
	}
	
	public static String formatoMilFecha(Date xfecha){
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
	    return f.format(xfecha);
	}
	
	public static String formatoHoraUtc(Date date){
		String ISO_FORMAT = "HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);
		TimeZone utc = TimeZone.getTimeZone("UTC");
		sdf.setTimeZone(utc);
		return sdf.format(date);
	}

}
