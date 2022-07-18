package com.openclassrooms.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;

import java.io.File;
import java.io.IOException;

public class JsonReader {
    public void readFile() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PersonService personService = new PersonService();

            DataObjects dataObject = objectMapper.readValue(new File("resources/data.json"), DataObjects.class);
            //System.out.println(personService.list());
            System.out.println(personService.search("Jonanathan", "Marrack"));
        } catch(IOException ex) {
            System.out.println("Error when reading the JSON file. " + ex);
        }
    }
}
