package com.openclassrooms.SafetyNet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DataObjects {
    @JsonProperty("persons")
    public static List<Person> persons;
    @JsonProperty("firestations")
    public static List<Firestation> firestations;
    @JsonProperty("medicalrecords")
    public static List<MedicalRecord> medicalrecords;

    public static List<Person> getPersons() {
        return persons;
    }
    public static void setPersons(List<Person> persons) {
        DataObjects.persons = persons;
    }

    public static List<Firestation> getFirestations() {
        return firestations;
    }
    public static void setFirestations(List<Firestation> firestations) {
        DataObjects.firestations = firestations;
    }

    public static List<MedicalRecord> getMedicalRecords() {
        return medicalrecords;
    }
    public static void setMedicalRecords(List<MedicalRecord> medicalrecords) {
        DataObjects.medicalrecords = medicalrecords;
    }
}
