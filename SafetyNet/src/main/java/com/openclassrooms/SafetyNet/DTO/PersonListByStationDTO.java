package com.openclassrooms.SafetyNet.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PersonListByStationDTO {
    private List<PersonDTO> people;
    private int adults;
    private int children;
}
