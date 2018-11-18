package net.elmosoft.splendid.utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import net.elmosoft.splendid.driver.exceptions.CommonTestRuntimeException;

public class FileUtils {

	public static String getAbsolutePath(String path) {
		URL resource = FileUtils.class.getResource(path);
		File file;
		try {
			file = Paths.get(resource.toURI()).toFile();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			throw new CommonTestRuntimeException("File not found: " + path);
		}
		return file.getAbsolutePath();
	}
}
