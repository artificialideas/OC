package com.openclassrooms.SafetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.Person;
import com.openclassrooms.SafetyNet.service.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Person person;
    private PersonService personService;

    @BeforeEach
    public void setup() throws IOException {
        objectMapper.readValue(new File("resources/data.json"), DataObjects.class);

        personService = new PersonService();
    }

    @Test
    @DisplayName("GET - returns Person collection list //list()")
    public void givenJSONFile_whenGetPerson_shouldReturnCollectionOfPerson() throws Exception {
        mockMvc.perform( get("/person/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].firstName", is("John")));
    }

    @Test
    @DisplayName("POST - returns a new added Person resource //create()")
    public void givenNewPerson_whenFirstANDLastName_shouldReturnPersonResource() throws Exception {
        // Mock resource
        person = new Person();
        person.setFirstName("Grace");
        person.setLastName("Hopper");
        person.setAddress("951 LoneTree Rd");
        person.setCity("Culver");
        person.setZip(97451);
        person.setPhone("841-874-7458");
        person.setEmail("gracehopper@mail.com");
        //personService.create(person);

        MvcResult result = mockMvc.perform( post("/person/")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(objectMapper.writeValueAsString(person)))
                                    .andExpect(jsonPath("$.firstName", is("Grace")))
                                    .andExpect(status().is2xxSuccessful())
                                    .andReturn();
        assertNotNull(result);
    }

    @Test
    @DisplayName("PUT - returns the updated details of an existant Person resource //update()")
    public void givenExistantPerson_whenAddressChanges_shouldReturnTrue() throws Exception {
        final String FIRST_NAME = "Felicia";
        final String LAST_NAME = "Boyd";

        person = new Person();
        person.setAddress("29 15th St");
        personService.update(FIRST_NAME, LAST_NAME, person);

        mockMvc.perform( put("/person/" + FIRST_NAME + '-' + LAST_NAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(jsonPath("$.firstName", is("Felicia")))
                .andExpect(jsonPath("$.address", is("29 15th St")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("DELETE - returns a boolean with the result of the delete demand //delete()")
    public  void givenExistantPerson_whenDeleteWithFirstANDLastName_shouldReturnTrue() throws Exception {
        final String FIRST_NAME = "Felicia";
        final String LAST_NAME = "Boyd";

        personService.delete(FIRST_NAME, LAST_NAME);

        mockMvc.perform( delete("/person/" + FIRST_NAME + '-' + LAST_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}