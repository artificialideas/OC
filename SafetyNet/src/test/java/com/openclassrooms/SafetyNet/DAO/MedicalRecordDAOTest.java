package com.openclassrooms.SafetyNet.DAO;


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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordDAOTest {
    final String FIRST_NAME = "Eric";
    final String LAST_NAME = "Cadigan";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private MedicalRecord medicalRecord;
    private static MedicalRecordDAO medicalRecordDAO;

    @BeforeEach
    public void setup() throws IOException {
        objectMapper.readValue(new File("resources/data.json"), DataObjects.class);

        medicalRecordDAO = new MedicalRecordDAO();
    }

    @Test
    @DisplayName("returns the updated details of an existant Medical Record resource //update()")
    public void givenExistantMedicalRecord_whenAddAllergy_shouldReturnUpdatedMedicalRecordResource() {
        // Mock resource
        List<String> meds = new ArrayList<>(1);
        meds.add("tradoxidine:400mg");
        List<String> allergies = new ArrayList<>(1);
        allergies.add("shellfish");

        medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Eric");
        medicalRecord.setLastName("Cadigan");
        medicalRecord.setBirthdate("08/06/1945");
        medicalRecord.setMedications(meds);
        medicalRecord.setAllergies(allergies);

        // Updated data
        MedicalRecord updatedMedicalRecord = new MedicalRecord();
        updatedMedicalRecord.setAllergies(allergies);

        MedicalRecord expected = medicalRecord;
        MedicalRecord actual = medicalRecordDAO.update(FIRST_NAME, LAST_NAME, updatedMedicalRecord);

        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("returns null for non existant Medical Record //update()")
    public void givenNonExistantMedicalRecord_whenUpdate_shouldReturnNull() {
        assertNull(medicalRecordDAO.update(FIRST_NAME, LAST_NAME, null));
    }
}
