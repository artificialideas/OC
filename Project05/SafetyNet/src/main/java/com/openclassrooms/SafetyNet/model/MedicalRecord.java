package com.openclassrooms.SafetyNet.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class MedicalRecord {
    private String firstName;
    private String lastName;
    private String birthdate;
    private String[] medications;
    private String[] allergies;

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(@NotNull LocalDate birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YYYY");
        this.birthdate = birthdate.format(formatter);
    }

    public String[] getMedications() {
        return medications.clone();
    }
    public void setMedications(String[] medications) {
        this.medications = Arrays.copyOf(medications, medications.length);
    }

    public String[] getAllergies() {
        return allergies.clone();
    }
    public void setAllergies(String[] allergies) {
        this.allergies = Arrays.copyOf(allergies, allergies.length);
    }
}
