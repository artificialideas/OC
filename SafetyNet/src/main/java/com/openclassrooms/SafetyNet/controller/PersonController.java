package com.openclassrooms.SafetyNet.controller;

import com.openclassrooms.SafetyNet.model.Person;
import com.openclassrooms.SafetyNet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;

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
            @RequestBody Person person) throws URISyntaxException {

        Person createdPerson = personService.create(person);
        if (createdPerson == null) {
            return ResponseEntity.notFound().build();
        } else {
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{firstName}-{lastName}")
                    .buildAndExpand(createdPerson.getFirstName() + '-' + createdPerson.getLastName())
                    .toUri();

            return ResponseEntity.created(uri).body(createdPerson);
        }
    }
//
    /**
     * UPDATE - Modify an existing Person resource
     * @return - A response with the updated person details
     */
    @PutMapping("/{firstName}-{lastName}")
    public ResponseEntity<Boolean> update(
            @PathVariable(value = "firstName", required = true) String firstName,
            @PathVariable(value = "lastName", required = true) String lastName,
            @RequestBody Person personDetails) {

        boolean updatedPerson = personService.update(firstName, lastName);
        if (updatedPerson) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(false);
        }
    }

    /**
     * DELETE - Delete an existing Person resource
     * @return - A response with all the people without the deleted person
     */
    @DeleteMapping("/{firstName}-{lastName}")
    public ResponseEntity<Object> delete(
            @PathVariable(value = "firstName", required = true) String firstName,
            @PathVariable(value = "lastName", required = true) String lastName) {
        personService.delete(firstName, lastName);
        return ResponseEntity.noContent().build();
    }
}
