package com.openclassrooms.SafetyNet.DTO;

import lombok.Data;

@Data
public class PersonDetailsDTO extends PersonDTO {
    private String address;
    private String phone;
}
