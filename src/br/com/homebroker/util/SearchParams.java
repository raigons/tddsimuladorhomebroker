package br.com.homebroker.util;

import java.util.List;


public interface SearchParams {
	
	public Integer size();
	
	public void setParams(Object params) throws RuntimeException;

	public boolean validParams();

	public List<Param> getParams();

}
