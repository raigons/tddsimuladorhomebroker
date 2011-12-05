package br.com.homebroker.model.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@Component
@ApplicationScoped
public class DateConvertor {

	private String datePattern;
	
	public Date convertStringToDate(String date) {
		if(isAValidDateFormat(date)){
			DateFormat formatter;
			formatter = new SimpleDateFormat(datePattern);
			try {
				return formatter.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	private boolean isAValidDateFormat(String date){
		return isDateFormatOnly(date) || isDateTimeFormat(date) || isDateWithSlashesFormatOnly(date);
	}
	
	private boolean isDateTimeFormat(String date){
		String datetimeRegex = "[0-9]{2}[-][0-9]{2}[-][0-9]{4}[ ][0-9]{2}[:][0-9]{2}";
		this.datePattern = "dd-MM-yyyy HH:mm";
		return Pattern.matches(datetimeRegex, date);
	}
	
	private boolean isDateFormatOnly(String date){
		String dateRegex = "[0-9]{2}[-][0-9]{2}[-][0-9]{4}";
		this.datePattern = "dd-MM-yyyy";
		return Pattern.matches(dateRegex, date);		
	}
	
	private boolean isDateWithSlashesFormatOnly(String date){
		String dateRegex = "[0-9]{2}[/][0-9]{2}[/][0-9]{4}";
		this.datePattern = "dd/MM/yyyy";
		return Pattern.matches(dateRegex, date);
	}
	public String getOnlyDatePatternWithSlashes(){
		String dateRegex = "[0-9]{2}[/][0-9]{2}[/][0-9]{4}";
		return dateRegex;
	}
}
