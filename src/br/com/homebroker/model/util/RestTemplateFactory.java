package br.com.homebroker.model.util;

import javax.annotation.PostConstruct;

import org.springframework.web.client.RestTemplate;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.ComponentFactory;

@Component
@ApplicationScoped
public class RestTemplateFactory implements ComponentFactory<RestTemplate>{

	private RestTemplate restTemplate;
	
	@PostConstruct
	public void init(){
		this.restTemplate = new RestTemplate();
	}
	
	@Override
	public RestTemplate getInstance() {
		return this.restTemplate;
	}

}
