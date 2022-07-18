package com.openclassrooms.SafetyNet.DAO;

import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;

public class PersonDAO {
    private static final Logger logger = LogManager.getLogger("PersonDAO");

    public Person getPersonByFullName(String first, String last) {
        for (Person person: DataObjects.getPersons()) {
            if ((Objects.equals(person.getFirstName(), first)) && (Objects.equals(person.getLastName(), last))) {
                return person;
            }
        }
        return null;
    }

    public List<Person> getPersons() {
        return DataObjects.persons;
    }

//    public Person save(Person person) {
//        Person savedPerson = new Person();
//        savedPerson.setFirstName(person.getFirstName());
//        savedPerson.setLastName(person.getLastName());
//        savedPerson.setAddress(person.getAddress());
//        savedPerson.setCity(person.getCity());
//        savedPerson.setZip(person.getZip());
//        savedPerson.setPhone(person.getPhone());
//        savedPerson.setEmail(person.getEmail());
//
//        return savedPerson;
//    }

//    public boolean update(String first, String last) {
//        for (Person person: DataObjects.getPersons()) {
//            if ((Objects.equals(person.getFirstName(), first)) &&
//                    (Objects.equals(person.getLastName(),last))) {
//                return true;
//            } else {
//                logger.error("Error saving new Person");
//            }
//        }
//        return false;
//    }
    //void delete()
}
