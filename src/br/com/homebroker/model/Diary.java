package br.com.homebroker.model;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Diary extends DataDays{

	public Diary(){
		
	}
	
	public Diary(Double max, Double min, Double close, Double open,
			Long volume, Active active, Date date) {
		super(max, min, close, open, volume, active, date);
	}
	
}
