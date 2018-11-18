package net.elmosoft.splendid.utils;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
public class JsonUtils {

    public static Object getJsonFromFile(String filename) {
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader(filename));
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
    }
    
	public static <T> T getObjectFromJson(String jsonPath, Class<T> classOfT) {
		Gson gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
		String fileData = null;
		try {
			fileData = new String(Files.readAllBytes(Paths.get(jsonPath)), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
		return gson.fromJson(fileData, classOfT);
	}
}
