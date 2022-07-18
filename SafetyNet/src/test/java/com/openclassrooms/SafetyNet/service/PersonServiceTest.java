package com.openclassrooms.SafetyNet.service;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
class PersonServiceTest {
    private static PersonService personService;

//    @Test
//    public void givenNewPerson_whenCreate_shouldReturnListOfPersons() {
//        Person person = new Person();
//        person.setFirstName("Lili");
//        person.setLastName("Potter");
//        person.setAddress("113 Steppes Pl");
//        person.setCity("Culver");
//        person.setZip(97451);
//        person.setPhone("222-333-4444");
//        person.setEmail("lili@mail.com");
//
//        personService.create(person);
//
//        assertThat(personService.search("Lili", "Potter"));
//    }
//
//    @Test
//    public void givenNewPerson_whenMissingAddress_shouldReturnException() {
//        Person person = new Person();
//        person.setFirstName("Lili");
//        person.setLastName("Potter");
//        person.setAddress(null);
//        person.setCity("Culver");
//        person.setZip(97451);
//        person.setPhone("222-333-4444");
//        person.setEmail("lili@mail.com");
//
//        personService.create(person);
//
//        assertThrows(IllegalStateException.class, () -> personService.create(person));
//    }
}