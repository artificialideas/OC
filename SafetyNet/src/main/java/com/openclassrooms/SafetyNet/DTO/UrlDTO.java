package com.openclassrooms.SafetyNet.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UrlDTO {
    // Person resource
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String phone;
    private String email;

    // Firestation resource
    private int station;

    // Medical Record resource
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;
}
