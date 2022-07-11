package com.openclassrooms.SafetyNet.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public interface PersonRepository {
    @Value("file:${file}")
    private String fileDirectory;

    default List<Person> findAll() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Person> persons = List.of(objectMapper.readValue(new File(fileDirectory), Person.class));

            return persons;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
