package com.openclassrooms.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.DAO.PersonDAO;
import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
class PersonServiceTest {
    final String FIRST_NAME = "Jonanathan";
    final String LAST_NAME = "Marrack";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Person person;
    private static PersonDAO personDAO;
    private PersonService personService;

    @BeforeEach
    public void setup() throws IOException {
        objectMapper.readValue(new File("resources/data.json"), DataObjects.class);

        personService = new PersonService();
    }

    @Test
    @DisplayName(" returns Person resource if existant //search()")
    public void givenExistantPerson_whenKnownFullName_shouldReturnPerson() {
        // Existant mock resource
        person = new Person();
        person.setFirstName("Jonanathan");
        person.setLastName("Marrack");
        person.setAddress("29 15th St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setPhone("841-874-6513");
        person.setEmail("drk@email.com");

        Person expected = person;
        Person actual = personService.search(FIRST_NAME, LAST_NAME);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("returns collection of Person //list()")
    public void givenPersonCollection_whenList_shouldReturnListOf23Person() {
        assertEquals(23, personService.list().size());
    }

    @Test
    @DisplayName("returns Person resource after create //create()")
    public void givenNewPerson_whenCreate_shouldReturnPerson() {
        person = new Person();
        person.setFirstName("Lili");
        person.setLastName("Potter");
        person.setAddress("113 Steppes Pl");
        person.setCity("Culver");
        person.setZip(97451);
        person.setPhone("222-333-4444");
        person.setEmail("lili@mail.com");

        Person expected = person;
        Person actual = personService.create(person);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("returns the updated details of an existant Person resource //update()")
    public void givenExistantPerson_whenKnownFirstANDLastName_shouldReturnUpdatedPerson() {
        // Existant mock resource
        person = new Person();
        person.setFirstName("Jonanathan");
        person.setLastName("Marrack");
        person.setAddress("29 15th St");
        person.setCity("Culver");
        person.setZip(97451);
        person.setPhone("841-874-6513");
        person.setEmail("drk@email.com");

        // Updated data
        Person updatedPerson = new Person();
        updatedPerson.setAddress("29 15th St");

        Person expected = person;
        Person actual = personService.update(FIRST_NAME, LAST_NAME, updatedPerson);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("returns true if an existant Person resource has been deleted //delete()")
    public void givenExistantPerson_whenDelete_shouldReturnTrue() {
        assertTrue(personService.delete(FIRST_NAME, LAST_NAME));
    }
}