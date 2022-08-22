package com.openclassrooms.SafetyNet.controller;

import com.openclassrooms.SafetyNet.model.Person;
import com.openclassrooms.SafetyNet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController     //It will insert the response's return using JSON format inside the HTTP's body
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    /**
     * READ - Get all Person resources
     * @return - An Iterable object of Person with all its details
     */
    @GetMapping("/")
    public Iterable<Person> list() {
        return personService.list();
    }

    /**
     * CREATE - Add a new Person resource
     * @return - A response with all the people and the new person
     */
    @PostMapping("/")
    public ResponseEntity<Person> create(
            @RequestBody Person newPerson) {
        Person person = personService.create(newPerson);
        if (Objects.isNull(person)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(person, HttpStatus.CREATED);
        }
    }

    /**
     * UPDATE - Modify an existing Person resource
     * @return - A response with the updated person details
     */
    @PutMapping("/{firstName}-{lastName}")
    public ResponseEntity<Person> update(
            @PathVariable(value = "firstName", required = true) String firstName,
            @PathVariable(value = "lastName", required = true) String lastName,
            @RequestBody Person personDetails) {
        Person updatedPerson = personService.update(firstName, lastName, personDetails);
        return ResponseEntity.ok(updatedPerson);
    }

    /**
     * DELETE - Delete an existing Person resource
     * @return - A response with all the people without the deleted person
     */
    @DeleteMapping("/{firstName}-{lastName}")
    public ResponseEntity<Person> delete(
            @PathVariable(value = "firstName", required = true) String firstName,
            @PathVariable(value = "lastName", required = true) String lastName) {
        personService.delete(firstName, lastName);
        return ResponseEntity.noContent().build();
    }
}
