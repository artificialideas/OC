package com.openclassrooms.SafetyNet.DTO;

import lombok.Data;

import java.util.List;

@Data
public class FirestationByFamilyAddressDTO {
    private int station;
    private List<MedicalRecordFullRapportDTO> family;
}
