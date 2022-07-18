package com.openclassrooms.SafetyNet;

import com.openclassrooms.SafetyNet.service.JsonReader;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetyNetApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SafetyNetApplication.class, args);
		JsonReader jsonReader = new JsonReader();
		jsonReader.readFile();
	}

}