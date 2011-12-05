package br.com.homebroker.service;

import br.com.caelum.vraptor.ioc.Component;
import br.com.homebroker.model.News;
import br.com.homebroker.repository.RepositoryFactory;

@Component
public class NewsService extends Service<News> {

	public NewsService(RepositoryFactory factory) {
		super(factory);
	}

}
