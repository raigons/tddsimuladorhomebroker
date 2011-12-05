package br.com.homebroker.service;

import java.util.HashMap;
import java.util.Map;

import br.com.caelum.vraptor.ioc.Component;
import br.com.homebroker.model.Diary;
import br.com.homebroker.model.Intraday;
import br.com.homebroker.repository.RepositoryFactory;

@Component
public class ServiceFactory {

	private RepositoryFactory factory;
	
	@SuppressWarnings("rawtypes")
	private Map<String, Service> servicesList;
	
	public ServiceFactory(RepositoryFactory factory){
		this.factory = factory;
		initServices();
	}
	
	@SuppressWarnings("rawtypes")
	private void initServices(){
		servicesList = new HashMap<String, Service>();
		servicesList.put("intraday", new DatayDaysService<Intraday>(factory){});
		servicesList.put("diary", new DatayDaysService<Diary>(factory){});
		servicesList.put("ativo", new ActiveService(factory));
		servicesList.put("news", new NewsService(factory));
	}
	
	@SuppressWarnings("rawtypes")
	public Service getService(String serviceClass) {
		serviceClass = serviceClass.toLowerCase();
		if(this.servicesList.containsKey(serviceClass))
			return this.servicesList.get(serviceClass);
		throw new RuntimeException("O serviço solicitado não existe");
	}
}
