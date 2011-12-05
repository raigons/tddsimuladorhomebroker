package br.com.homebroker.service;

import br.com.caelum.vraptor.ioc.Component;
import br.com.homebroker.model.Active;
import br.com.homebroker.repository.RepositoryFactory;
import br.com.homebroker.util.SearchParams;
import br.com.homebroker.util.exceptions.NothingFoundException;

@Component
public class ActiveService extends Service<Active>{
	
	public ActiveService(RepositoryFactory factory){
		super(factory);
	}
	
	public Active search(SearchParams params) throws NothingFoundException{
		Active active = this.repository.search(params);
		if(active != null){
			return active;
		}
		throw new NothingFoundException();
	}

}
