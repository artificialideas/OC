package com.openclassrooms.SafetyNet.repository;

import com.openclassrooms.SafetyNet.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository     //Bean that will communicate with our DB
public interface PersonRepository extends CrudRepository<Person, Long> {
}
