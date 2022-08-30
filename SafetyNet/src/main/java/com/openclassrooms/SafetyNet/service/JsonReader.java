package com.openclassrooms.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;

import java.io.File;
import java.io.IOException;

public class JsonReader {
    public void readFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            objectMapper.readValue(new File("resources/data.json"), DataObjects.class);
        } catch(IOException ex) {
            System.out.println("Error when reading the JSON file. " + ex);
        }
        System.out.println("persons: " + DataObjects.getPersons());
        System.out.println("firestations: " + DataObjects.getFirestations());
        System.out.println("medical records: " + DataObjects.getMedicalRecords());
    }
}
