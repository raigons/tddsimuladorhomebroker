package br.com.homebroker.repository.dao;

import org.hibernate.Session;

import br.com.caelum.vraptor.ioc.Component;
import br.com.homebroker.model.Diary;
import br.com.homebroker.model.Intraday;
import br.com.homebroker.repository.RepositoryFactory;
import br.com.homebroker.repository.RepositoryInterface;

@Component
public class DaoFactory implements RepositoryFactory {

	private Session session;

	public DaoFactory(Session session) {
		this.session = session;
	}

	@Override
	public RepositoryInterface<?> getRepository(String type) {
		System.out.println("Iniciando Dao "+type);
		if(type.equals("Active")){
			return new DaoActive(this.session);
		}
		if(type.equals("Intraday")){			
			return new DaoDataDays<Intraday>(this.session){};
		}
		if(type.equals("Diary")){
			return new DaoDataDays<Diary>(this.session){};
		}		
		if(type.equals("News")){
			return new DaoNews(this.session);
		}
		throw new RuntimeException("Este tipo não pode ser instanciado");
	}	
}
