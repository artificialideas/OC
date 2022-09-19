package com.openclassrooms.SafetyNet.DTO;

import lombok.Data;

import java.util.List;

@Data
public class PersonListByStationDTO {
    private List<PersonDetailsDTO> personDetailsDTOS;
    private int adults;
    private int children;
}
