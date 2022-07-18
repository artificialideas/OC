package com.openclassrooms.SafetyNet.service;

import com.openclassrooms.SafetyNet.DAO.PersonDAO;
import com.openclassrooms.SafetyNet.model.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    private final PersonDAO personDAO = new PersonDAO();

    public Iterable<Person> list() {
        return personDAO.getAllPersons();
    }

    public Person search(String firstName, String lastName) {
        return personDAO.getPersonByFullName(firstName, lastName);
    }
//
//    public Person create(Person person) {
//        return personRepository.create(person);
//    }
//
//    public void delete(String firstName, String lastName) {
//        personRepository.delete(firstName, lastName);
//    }

}
