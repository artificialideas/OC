package com.openclassrooms.SafetyNet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    //@JsonProperty("firstName")
    private String firstName;
    //@JsonProperty("lastName")
    private String lastName;
    //@JsonProperty("address")
    private String address;
    //@JsonProperty("city")
    private String city;
    //@JsonProperty("zip")
    private String zip;
   //@JsonProperty("phone")
    private String phone;
    //@JsonProperty("email")
    private String email;
}