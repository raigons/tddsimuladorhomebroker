package br.com.homebroker.util;

import java.util.ArrayList;
import java.util.List;

public class ActiveSearchParams implements SearchParams {

	private Param param;
	
	@Override
	public Integer size() {
		return 1;
	}

	@Override
	public void setParams(Object params) throws RuntimeException{
		this.param = (Param<?>) params;	
		if(!validParams()){
			this.param = null;
			throw new RuntimeException("O parâmetro para Ativos deve ser uma String");
		}
	}

	@Override
	public boolean validParams() {		
		return param.getParam() instanceof String;
	}

	@Override
	public List<Param> getParams() {
		List<Param> params = new ArrayList<Param>(1);
		params.add(this.param);
		return params;
	}

}
