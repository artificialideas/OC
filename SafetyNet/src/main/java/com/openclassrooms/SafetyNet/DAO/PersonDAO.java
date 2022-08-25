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

    public Person save(Person person) {
        Person savedPerson = new Person();
        savedPerson.setFirstName(person.getFirstName());
        savedPerson.setLastName(person.getLastName());
        savedPerson.setAddress(person.getAddress());
        savedPerson.setCity(person.getCity());
        savedPerson.setZip(person.getZip());
        savedPerson.setPhone(person.getPhone());
        savedPerson.setEmail(person.getEmail());

        return savedPerson;
    }

    public Person update(String first, String last, Person personDetails) {
        Person updatedPerson = this.getPersonByFullName(first,last);

        if ((updatedPerson != null) && (personDetails != null)) {
            //if (personDetails.getFirstName() != null) updatedPerson.setFirstName(personDetails.getFirstName());
            //if (personDetails.getLastName() != null) updatedPerson.setLastName(personDetails.getLastName());
            if (personDetails.getAddress() != null) updatedPerson.setAddress(personDetails.getAddress());
            if (personDetails.getCity() != null) updatedPerson.setCity(personDetails.getCity());
            if (personDetails.getZip() != 0) updatedPerson.setZip(personDetails.getZip());
            if (personDetails.getPhone() != null) updatedPerson.setPhone(personDetails.getPhone());
            if (personDetails.getEmail() != null) updatedPerson.setEmail(personDetails.getEmail());

            return updatedPerson;
        } else {
            logger.error("Error updating new Person.");
            return null;
        }
    }

    public Boolean delete(String first, String last) {
        Person deletedPerson = this.getPersonByFullName(first,last);

        if (deletedPerson != null) {
            DataObjects.getPersons().remove(deletedPerson);
            return true;
        } else {
            logger.error(first + ' ' + last + " does not exist in our list.");
            return false;
        }
    }
}
