package br.com.homebroker.repository;

public interface RepositoryFactory {
	
	public RepositoryInterface<?> getRepository(String type);

}
