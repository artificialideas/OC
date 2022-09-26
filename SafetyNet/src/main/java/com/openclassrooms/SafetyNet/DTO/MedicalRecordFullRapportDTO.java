package com.openclassrooms.SafetyNet.DTO;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecordFullRapportDTO extends MedicalRecordDTO {
    private int age;
    private List<String> medications;
    private List<String> allergies;
}
