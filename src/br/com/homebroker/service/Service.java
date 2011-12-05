package br.com.homebroker.service;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

import br.com.homebroker.model.Active;
import br.com.homebroker.repository.RepositoryFactory;
import br.com.homebroker.repository.RepositoryInterface;
import br.com.homebroker.util.exceptions.NothingFoundException;

public abstract class Service<E> {

	protected RepositoryInterface<E> repository;
	
	private RepositoryFactory factory;

	protected Class<E> clazz;
	
	public Service(RepositoryFactory factory){
		this.factory = factory;
		getClassType();		
		initRepository();		
	}
	
	@SuppressWarnings("unchecked")
	public void getClassType(){
		clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public String getClassTypeName(){
		return this.clazz.getSimpleName();
	}
	
	@SuppressWarnings("unchecked")
	protected void initRepository(){
		System.out.println("type = "+clazz.getSimpleName());
		this.repository = (RepositoryInterface<E>) this.factory.getRepository(clazz.getSimpleName());
	}

	public E search(Long id) {
		System.out.println("herer, service");
		E result = (E) this.repository.search(id);
		return search(result);
	}

	public E search(String code){
		E result = (E) this.repository.seach(code);
		return search(result);
	}

	protected E search(E result){
		if(result != null){
			return result;
		}
		throw new NothingFoundException();		
	}
	
	protected List<E> search(List<E> results){
		if(!results.isEmpty())
			return results;
		throw new NothingFoundException();
	}

	public boolean delete(E toDelete) {
		return this.repository.delete(toDelete);
	}
	
	public List<E> search(Active active, Date begin, Date end) throws NothingFoundException{
		List<E> list = this.repository.search(active, begin, end);
		return search(list);
	}

	public boolean save(E object) {
		return this.repository.save(object);
	}

	public List<E> listAll() {
		return this.repository.listAll();
	}

	public List<E> searchByActive(Active active) {		
		return search(this.repository.search(active));
	}
}
