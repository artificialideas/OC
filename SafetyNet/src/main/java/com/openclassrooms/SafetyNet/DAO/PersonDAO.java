package com.openclassrooms.SafetyNet.DAO;

import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.Person;

import java.util.List;

public class PersonDAO {
    public List<Person> getAllPersons() {
        return DataObjects.persons;
    }

    //public String getPersonByFullName() {}
}
