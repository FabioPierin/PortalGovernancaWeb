package br.com.pierin.pgw.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;

public class DateUtil implements Serializable{
	
	private static final long serialVersionUID = 7441007205812723454L;
	
	private static String DEFAULT_FORMAT = "EEE, dd MMMM yyyy HH:mm:ss z";
	
	
	public static Date convertStringDate(String dateValue){
		
		DateFormat formatter = new SimpleDateFormat(DEFAULT_FORMAT);
		Date date = null;
		try {
			date = formatter.parse(dateValue);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
		
	}
	
	public static Date addDaysToCurrentDate(int nOfDays){
		
		DateTime date= new DateTime();
		DateTime newDate = date.plusDays(nOfDays);
		
		return newDate.toDate();
		
	}

}
