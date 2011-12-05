package br.com.homebroker.repository.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.homebroker.model.News;

public class DaoNews extends Dao<News> {

	public DaoNews(Session session) {
		super(session);
	}

	@Override
	public List<News> listAll() {
		throw new RuntimeException("Notícias não podem ser listas sem filtros");
	}

}
