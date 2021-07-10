package com.cable.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConvertionUtils {

	public static Date convertStringTODate(String strDate) {
		Date date = null;
		if(strDate!=null) {
			try {
				date = new SimpleDateFormat("dd/MM/yyyy").parse(strDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;  
	}

	public static Date convertStringTODateByFormat(String strDate,String format) {
		Date date = null;
		if(strDate!=null) {
			try {
				date = new SimpleDateFormat(format).parse(strDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return date;  
	}
	
	public static String convertDateTOStringByFormat(Date date,String format){  
		String strDate = null;
		if(date!=null) {
			DateFormat dateFormat = new SimpleDateFormat(format);  
			strDate = dateFormat.format(date);
		}
		return strDate;
	}

	public static String convertDateTOString(Date date){  
		String strDate = null;
		if(date!=null) {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
			strDate = dateFormat.format(date);
		}
		return strDate;
	}  
}
