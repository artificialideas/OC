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

    public List<Person> getAllPersons() {
        return DataObjects.persons;
    }

    //void save()
    //void update()
    //void delete()
}
