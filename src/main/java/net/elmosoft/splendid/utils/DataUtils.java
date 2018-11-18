package net.elmosoft.splendid.utils;

import java.util.List;

import org.json.simple.JSONArray;

public class DataUtils {

	public static Object[][] listToDataProviderArray(List<?> list) {
		if (list.size() == 0) {
			return new Object[][] {};
		}
		Object[][] data = new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i);
		}
		return data;
	}
	
	public static Object[][] arrayToDataProviderArray(Object[] array) {
		if (array.length == 0) {
			return new Object[][] {};
		}
		Object[][] data = new Object[array.length][1];
		for (int i = 0; i < array.length; i++) {
			data[i][0] = array[i];
		}
		return data;
	}

	public static Object[][] jsonArrayToDataProviderArray(JSONArray list) {
		if (list.size() == 0) {
			return new Object[][] {};
		}
		Object[][] data = new Object[list.size()][1];
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i);
		}
		return data;
	}
}
