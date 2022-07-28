package com.openclassrooms.SafetyNet.service;

import com.openclassrooms.SafetyNet.DAO.PersonDAO;
import com.openclassrooms.SafetyNet.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    private final PersonDAO personDAO = new PersonDAO();

    public Person search(String firstName, String lastName) {
        return personDAO.getPersonByFullName(firstName, lastName);
    }

    public List<Person> list() {
        return personDAO.getPersons();
    }

    public Person create(Person person) {
        return personDAO.save(person);
    }

    public Person update(String firstName, String lastName, Person personDetails) {
        return personDAO.update(firstName, lastName, personDetails);
    }

    public void delete(String firstName, String lastName) {
        personDAO.delete(firstName, lastName);
    }

}
