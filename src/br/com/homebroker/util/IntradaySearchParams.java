package br.com.homebroker.util;

import java.util.Calendar;
import java.util.List;

import br.com.homebroker.model.Active;

public class IntradaySearchParams implements SearchParams {
	
	@SuppressWarnings("rawtypes")
	private List<Param> params;
	
	@SuppressWarnings({ "unchecked", "rawtypes" }) 
	public void setParams(Object params) throws RuntimeException {
		this.params = (List<Param>) params;
		if(!validParams()){
			this.params.clear();
			throw new RuntimeException("Os tipos de parâmetros para o Intraday devem ser: Active, Calendar e Calendar, nesta ordem.");
		}
	}

	@Override
	public Integer size() {
		return this.params.size();
	}

	@Override
	public boolean validParams() {
		boolean valid = true;
		if(this.params.size() != 3)
			valid = false;
		else{
			if(!(this.params.get(0).getParam() instanceof Active))
				valid = false;
			if(!(this.params.get(1).getParam() instanceof Calendar))
				valid = false;
			if(!(this.params.get(2).getParam() instanceof Calendar))
				valid = false;
		}
		return valid;
	}

	@Override
	public List<Param> getParams() {
		return this.params;
	}	
}
