package com.openclassrooms.SafetyNet.service;

import com.openclassrooms.SafetyNet.DAO.PersonDAO;
import com.openclassrooms.SafetyNet.model.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private final PersonDAO personDAO = new PersonDAO();

    public List<Person> list() {
        return personDAO.getPersons();
    }

//    public Person search(String firstName, String lastName) {
//        return personDAO.getPersonByFullName(firstName, lastName);
//    }

    public Person create(Person person) {
        return personDAO.save(person);
    }
//    public List<Person> create(Person person) {
//        List<Person> allPersons = new ArrayList<>();
//
//        for (Person per: personDAO.getPersons()) {
//            allPersons.add(per);
//        }
//        allPersons.add(personDAO.save(person));
//
//        return allPersons;
//    }

    public boolean update(String firstName, String lastName) {
        return personDAO.update(firstName, lastName);
    }

    public void delete(String firstName, String lastName) {
        personDAO.delete(firstName, lastName);
    }

}
