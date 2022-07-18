package com.openclassrooms.SafetyNet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data                       //Lombok annotation; it will create getters/setters
public class DataObjects {
    @JsonProperty("persons")
    private List<Person> persons;
    @JsonProperty("firestations")
    private List<Firestation> firestations;
    @JsonProperty("medicalrecords")
    private List<MedicalRecord> medicalRecords;
}
