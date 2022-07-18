package com.openclassrooms.SafetyNet;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
public class SafetyNetApplication {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		//SpringApplication.run(SafetyNetApplication.class, args);
		ObjectMapper mapper = new ObjectMapper();
		InputStream fileInputStream = new FileInputStream("resources/data.json");

		DataObjects dataObjects = mapper.readValue(fileInputStream, DataObjects.class);
		System.out.println(dataObjects.getPersons());
		fileInputStream.close();

	}
//	public List<DataObjects> readJson() {
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			return List.of(objectMapper.readValue(new File("resources/data.json"), DataObjects.class));
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//		return null;
//	}
}
