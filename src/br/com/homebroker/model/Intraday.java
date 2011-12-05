package br.com.homebroker.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Intraday extends DataDays{

	public Intraday(Double max, Double min, Double close, Double open,
			Long volume, Active active, Date date) {
		super(max, min, close, open, volume, active, date);
	}
	
	public Intraday(){
		
	}

	public Long getId() {
		return null;
	}	
	
}
