package com.openclassrooms.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordServiceTest {
    final String FIRST_NAME = "Eric";
    final String LAST_NAME = "Cadigan";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MedicalRecord medicalRecord;
    private MedicalRecordService medicalRecordService;

    @BeforeEach
    public void setup() throws IOException {
        objectMapper.readValue(new File("resources/data.json"), DataObjects.class);

        medicalRecordService = new MedicalRecordService();
    }

    @Test
    @DisplayName(" returns Medical Record resource if existant //search()")
    public void givenExistantMedicalRecord_whenKnownFirstANDLastName_shouldReturnMedicalRecord() {
        // Existant mock resource
        List<String> meds = new ArrayList<>(1);
        meds.add("tradoxidine:400mg");

        medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Eric");
        medicalRecord.setLastName("Cadigan");
        medicalRecord.setBirthdate("08/06/1945");
        medicalRecord.setMedications(meds);
        medicalRecord.setAllergies(Collections.emptyList());

        MedicalRecord expected = medicalRecord;
        MedicalRecord actual = medicalRecordService.search(FIRST_NAME, LAST_NAME);

        assertEquals(expected, actual);
    }
}