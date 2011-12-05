package br.com.homebroker.repository;

import java.util.Date;
import java.util.List;

import br.com.homebroker.model.Active;
import br.com.homebroker.util.SearchParams;

public interface RepositoryInterface<E> {
	
	public List<E> search(Active active, Date begin, Date end);
	
	public boolean delete(E toDelete);
	
	public E seach(String code);
	
	public E search(Long id);
	
	public Class<E> getObjectClass();

	public String getObjectClassName();

	public E search(SearchParams params);
	
	public boolean save(E object);
	
	public List<E> listAll();

	public List<E> search(Active any);
}
