package com.openclassrooms.SafetyNet.controller;

import com.openclassrooms.SafetyNet.model.Person;
import com.openclassrooms.SafetyNet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController     //It will insert the response's return using JSON format inside the HTTP's body
@RequestMapping("/persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * READ - Get all people
     * @return - An Iterable object of Person with all its details
     */
    @GetMapping("/")
    public Iterable<Person> list() {
        return personService.list();
    }

//    /**
//     * READ - Get one person through its first and last names
//     * @return - A response with the new person details
//     */
//    @GetMapping("/{firstName}-{lastName}")
//    public ResponseEntity<Person> read(
//        @RequestParam(value = "firstName", required = true) String firstName,
//        @RequestParam(value = "lastName", required = true) String lastName) {
//
//        Person foundPerson = personService.read(firstName, lastName);
//        if (foundPerson == null) {
//            return ResponseEntity.notFound().build();
//        } else {
//            return ResponseEntity.ok(foundPerson);
//        }
//    }
//
//    /**
//     * CREATE - Add a new person
//     * @return - A response with all the people and the new person
//     */
//    @PostMapping("/")
//    public ResponseEntity<Person> create(
//            @RequestBody Person person) throws URISyntaxException {
//
//        Person createdPerson = personService.create(person);
//        if (createdPerson == null) {
//            return ResponseEntity.notFound().build();
//        } else {
//            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//                    .path("/{firstName}-{lastName}")
//                    .buildAndExpand(createdPerson.getFirstName() + '-' + createdPerson.getLastName())
//                    .toUri();
//
//            return ResponseEntity.created(uri).body(createdPerson);
//        }
//    }
//
//    /**
//     * UPDATE - Modify an existing person
//     * @return - A response with the updated person details
//     */
//    @PutMapping("/{firstName}-{lastName}")
//    public ResponseEntity<Person> update(
//            @RequestBody Person person,
//            @PathVariable(value = "firstName", required = true) String firstName,
//            @PathVariable(value = "lastName", required = true) String lastName) {
//
//        Person updatedPerson = personService.update(firstName, lastName);
//        if (updatedPerson == null) {
//            return ResponseEntity.notFound().build();
//        } else {
//            return ResponseEntity.ok(updatedPerson);
//        }
//    }
//
//    /**
//     * DELETE - Delete an existing person
//     * @return - A response with all the people without the deleted person
//     */
//    @DeleteMapping("/{firstName}-{lastName}")
//    public ResponseEntity<Object> deletePerson(
//            @PathVariable(value = "firstName", required = true) String firstName,
//            @PathVariable(value = "lastName", required = true) String lastName) {
//        personService.delete(firstName, lastName);
//        return ResponseEntity.noContent().build();
//    }
}
