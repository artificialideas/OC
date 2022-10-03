package com.openclassrooms.SafetyNet.DTO;

import lombok.Data;

import java.util.List;

@Data
public class FirestationByFamilyDetailsDTO {
    private String address;
    private List<MedicalRecordFullRapportDTO> people;
}
