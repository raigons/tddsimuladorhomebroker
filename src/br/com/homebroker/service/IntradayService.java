package br.com.homebroker.service;

import br.com.homebroker.model.Intraday;
import br.com.homebroker.repository.dao.Dao;
import br.com.homebroker.util.SearchParams;

public class IntradayService {
	
	private final Dao<Intraday> repository;
	
	public IntradayService(Dao<Intraday> repository){
		this.repository = repository;
	}

	public Intraday search(SearchParams searchParams) {
		return repository.search(searchParams);
	}
}
