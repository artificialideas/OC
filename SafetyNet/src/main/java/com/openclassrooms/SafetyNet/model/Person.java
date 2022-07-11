package com.openclassrooms.SafetyNet.model;

import lombok.Data;

@Data                       //Lombok annotation; it will create getters/setters
public class Person {
    //@JsonProperty("firstName")
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    @Override
    public String toString() {
        return '{' +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", address=" + address + '\'' +
                    ", city='" + city + '\'' +
                    ", zip='" + zip + '\'' +
                    ", phone='" + phone + '\'' +
                    ", email='" + email + '\'' +
                '}';
    }
}
