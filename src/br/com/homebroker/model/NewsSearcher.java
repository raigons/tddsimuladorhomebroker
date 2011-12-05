package br.com.homebroker.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.client.RestTemplate;

import br.com.homebroker.model.util.DateConvertor;

public abstract class NewsSearcher {

	protected List<News> list;
	
	protected DateConvertor dateConvertor;
	
	private RestTemplate restTemplate;
	
	public NewsSearcher(RestTemplate restTemplate, DateConvertor dateConvertor) {
		this.restTemplate = restTemplate;
		this.dateConvertor = dateConvertor;
	}

	private void listParams(){
		System.out.println("Listando parâmetros");
		Set<String> paramsKey = getParams().keySet();
		for (String string : paramsKey) {
			System.out.println(string + " = "+ getParams().get(string));
		}
	}
	
	public List<News> getNews() {
		System.out.println("Procurando em ..." + getUrl());
		listParams();
		try{
			this.list = new ArrayList<News>();
			String response = restTemplate.getForObject(getUrl(), String.class, getParams());
			return list(response);
		}catch(Exception e){
			System.out.print(e.getMessage());
			//e.printStackTrace();
			return new ArrayList<News>();
		}
	}
	
	protected abstract List<News> list(String news);
	
	protected abstract String getUrl();
	
	protected abstract Map<String, String> getParams();
}
