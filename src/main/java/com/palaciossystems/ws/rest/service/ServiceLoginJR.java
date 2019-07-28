package com.palaciossystems.ws.rest.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palaciossystems.ws.rest.vo.VOUsuario;

//@ApplicationPath(value="/palacios")
@Path("/palacios")
public class ServiceLoginJR {
	
	@POST
	@Path("/validaUsuario")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public VOUsuario validaUsuario(VOUsuario vo){
		vo.setUserValido(false);
		if(vo.getUsuario().equals("Java") && vo.getPasswdor().equals("palacios")){
			vo.setUserValido(true);
		}
		System.out.println(vo);
		vo.setPasswdor("***********");
		return vo;
	}
	
	@GET
	@Path("/saluda")
	@Produces({MediaType.APPLICATION_JSON})
	public String saluda(){
		return "Saludos desde Clase de Servicio....";
	}

}
