package com.isaac.agenda.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Funcoes {

	private static DateFormat dateFormatDh = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	static {
		dateFormatDh.setLenient(false);
	}
	
	public static String dateToStr(Date data) {
		try {
			return dateFormatDh.format(data);
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public static Date strToDate(String data) {
		try {
			return dateFormatDh.parse(data);
		} catch (ParseException e) {
			return null;
		}
	}
}
