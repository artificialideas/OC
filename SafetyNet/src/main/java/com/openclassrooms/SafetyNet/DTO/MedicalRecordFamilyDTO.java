package com.openclassrooms.SafetyNet.DTO;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecordFamilyDTO {
    private List<MedicalRecordDetailsDTO> children;
    private List<MedicalRecordDTO> adults;
}
