package com.openclassrooms.SafetyNet.DAO;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PersonDAOTest {
    final String FIRST_NAME = "Jonanathan";
    final String LAST_NAME = "Marrack";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Person person;
    private static PersonDAO personDAO;

    @BeforeEach
    public void setup() throws IOException {
        objectMapper.readValue(new File("resources/data.json"), DataObjects.class);

        personDAO = new PersonDAO();
    }

    @Test
    @DisplayName("returns Person resource if it is listed //getPersonByFullName()")
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
        Person actual = personDAO.getPersonByFullName(FIRST_NAME, LAST_NAME);

        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("returns null for non existant Person //getPersonByFullName()")
    public void givenNonExistantPerson_whenGetPerson_shouldReturnNull() {
        assertNull(personDAO.getPersonByFullName(FIRST_NAME, "Pollock"));
    }

    @Test
    @DisplayName("returns collection of Person //getPersons()")
    public void givenPersonCollection_whenGetCollection_shouldReturnListOf23Person() {
        assertEquals(23, personDAO.getPersons().size());
    }

    @Test
    @DisplayName("returns Person resource after save //save()")
    public void givenNewPerson_whenSave_shouldReturnPerson() {
        person = new Person();
        person.setFirstName("Lili");
        person.setLastName("Potter");
        person.setAddress("113 Steppes Pl");
        person.setCity("Culver");
        person.setZip(97451);
        person.setPhone("222-333-4444");
        person.setEmail("lili@mail.com");

        Person expected = person;
        Person actual = personDAO.save(person);

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
        Person actual = personDAO.update(FIRST_NAME, LAST_NAME, updatedPerson);

        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("returns null for non existant Person //update()")
    public void givenNonExistantPerson_whenUpdate_shouldReturnNull() {
        assertNull(personDAO.update(FIRST_NAME, LAST_NAME, null));
    }

    @Test
    @DisplayName("returns true if an existant Person resource has been deleted //delete()")
    public void givenExistantPerson_whenDelete_shouldReturnTrue() {
        assertTrue(personDAO.delete(FIRST_NAME, LAST_NAME));
    }
    @Test
    @DisplayName("returns false for non existant Person //delete()")
    public void givenNonExistantPerson_whenDelete_shouldReturnFalse() {
        assertFalse(personDAO.delete(FIRST_NAME, "Pollock"));
    }
}