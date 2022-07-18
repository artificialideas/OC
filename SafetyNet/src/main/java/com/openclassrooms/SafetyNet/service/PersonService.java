package com.openclassrooms.SafetyNet.service;

import com.openclassrooms.SafetyNet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

//    public Iterable<Person> list() {
//        return personRepository.findAll();
//    }

//    public Person read(String firstName, String lastName) {
//        return personRepository.findByName(firstName, lastName);
//    }
//
//    public Person create(Person person) {
//        return personRepository.create(person);
//    }
//
//    public void delete(String firstName, String lastName) {
//        personRepository.delete(firstName, lastName);
//    }

}
