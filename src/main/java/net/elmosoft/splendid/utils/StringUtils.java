package net.elmosoft.splendid.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

public class StringUtils {

	private static final int DEFAULT_CHARACTERS_COUNT = 6;
	private static final String DATE_TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
	private static final String TIMESTAMP_FORMAT = "HHmmss";

	public static String buildString(Object... parts) {
		StringBuilder msg = new StringBuilder();
		for (Object part : parts) {
			msg.append(part);
		}
		return msg.toString();
	}

	public static String generateRandomString(String prefix, int numbersCount) {
		return buildString(prefix, RandomStringUtils.randomAlphanumeric(numbersCount));
	}

	public static String generateRandomString(String prefix) {
		return generateRandomString(prefix, DEFAULT_CHARACTERS_COUNT);
	}
	
	public static String getDateTimeStamp() {
		return new SimpleDateFormat(DATE_TIMESTAMP_FORMAT).format(new Date());
	}

	public static String getTimeStamp() {
		return new SimpleDateFormat(TIMESTAMP_FORMAT).format(new Date());
	}

}
