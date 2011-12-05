package br.com.homebroker.controller;

import org.springframework.web.client.RestTemplate;

import br.com.caelum.vraptor.Resource;

@Resource
public class Mundo {

	private final RestTemplate template;
	
	public Mundo(RestTemplate template){
		this.template = template;
	}
	
	public String boasVindas(){
		//String response = this.template.getForObject("http://www.agrocim.com.br/milho", String.class);
		//System.out.println(response);
		return "boas vindas Ramonox";
	}
}
