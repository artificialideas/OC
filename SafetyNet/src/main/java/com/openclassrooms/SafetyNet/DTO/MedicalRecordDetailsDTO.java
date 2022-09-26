package com.openclassrooms.SafetyNet.DTO;

import lombok.Data;

import java.util.List;

@Data
public class MedicalRecordDetailsDTO extends MedicalRecordDTO{
    private int age;
    private List<MedicalRecordDTO> adults;
}
