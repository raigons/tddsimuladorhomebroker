package br.com.homebroker.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.homebroker.model.Active;
import br.com.homebroker.model.DataDays;
import br.com.homebroker.model.util.DateConvertor;
import br.com.homebroker.service.Service;
import br.com.homebroker.service.ServiceFactory;
import br.com.homebroker.util.exceptions.NothingFoundException;

@Resource
@Path("/datadays")
public class DatadaysController {
	
	private final Result result;

	private final ServiceFactory serviceFactory;
	
	private final DateConvertor convertor;
	
	public DatadaysController(ServiceFactory serviceFactory, DateConvertor convertor, Result result) {
		this.serviceFactory = serviceFactory;
		this.convertor = convertor;
		this.result = result;
	}
	
	@Path("/path/test")
	public void teste(){
		System.out.print("testa");
		this.result.include("test", "try this way");
	}
	
	@Get 
	@Path("/{type}/{active.code}/{beginDate}/{endDate}.json")
	public void search(Active active, String type, String beginDate, String endDate) {
		if(typeIsNotValid(type)){
			typeLinkErrorMessage();
		}else{
			Service<DataDays> service = getService(type);
			Date begin = this.convertor.convertStringToDate(beginDate);
			Date end = this.convertor.convertStringToDate(endDate);
			if(begin != null && end != null){
				try{
					List<DataDays> datadays = service.search(active, begin, end);
					this.result.use(json()).withoutRoot().from(datadays).include("active").exclude("date").serialize();
				}catch(NothingFoundException exception){
					this.result.use(json()).from(exception.getMessage(),"message").serialize();
				}
			}else{
				this.result.use(json()).from("As datas não estão no formato correto (dd-MM-yyyy HH:mm) ou (dd-MM-yyyy)", "errorDate").serialize(); 
			}
		}
	}
	
	private void typeLinkErrorMessage(){
		this.result.use(json()).from("O link deve ser no formato /diary/ ou /intraday/","typeError").serialize();
	}
	
	@Post
	@Path("/{type}")
	public void save(String type, DataDays dataday) {
		if(typeIsNotValid(type)){
			typeLinkErrorMessage();
		}else{
			Service<DataDays> service = getService(type);
			if(service.save(dataday))
				this.result.use(json()).from("Dados armazenados com sucesso", "message").serialize();
			else
				this.result.use(json()).from("Falha na inserção dos dados", "message").serialize();
		}		
	}

	@SuppressWarnings("unchecked")
	private Service<DataDays> getService(String type) {
		return this.serviceFactory.getService(type);
	}
	
	private boolean typeIsNotValid(String type){
		return !type.equals("diary") && !type.equals("intraday");
	}
}
