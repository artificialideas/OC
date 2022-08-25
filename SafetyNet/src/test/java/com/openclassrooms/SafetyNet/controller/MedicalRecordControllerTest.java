package com.openclassrooms.SafetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.MedicalRecord;
import com.openclassrooms.SafetyNet.service.MedicalRecordService;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerTest {
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
    @DisplayName("GET - returns MedicalRecord collection list //list()")
    public void givenJSONFile_whenGetMedicalRecord_shouldReturnCollectionOfMedicalRecord() throws Exception {
        mockMvc.perform( get("/medicalRecord/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].firstName", is("John")));
                //.andExpect(jsonPath("$.[0].medications", Matchers.hasSize(2)));
                //.andExpect(jsonPath("$.[0].medications", Matchers.containsInAnyOrder("aznol:350mg", "hydrapermazol:100mg")));
    }

    @Test
    @DisplayName("POST - returns a new added MedicalRecord resource //create()")
    public void givenNewMedicalRecord_whenFirstANDLastName_shouldReturnMedicalRecordResource() throws Exception {
        // Mock resource
        List<String> meds = new ArrayList<>(1);
        meds.add("ibupurin:100mg");

        medicalRecord = new MedicalRecord();
        medicalRecord.setFirstName("Grace");
        medicalRecord.setLastName("Hopper");
        medicalRecord.setBirthdate("01/01/1999");
        medicalRecord.setMedications(meds);
        medicalRecord.setAllergies(Collections.emptyList());
        //MedicalRecordService.create(medicalRecord);

        MvcResult result = mockMvc.perform( post("/medicalRecord/")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(objectMapper.writeValueAsString(medicalRecord)))
                                    .andExpect(jsonPath("$.firstName", is("Grace")))
                                    .andExpect(status().is2xxSuccessful())
                                    .andReturn();
        assertNotNull(result);
    }

    @Test
    @DisplayName("PUT - returns the updated details of an existant MedicalRecord resource //update()")
    public void givenExistantMedicalRecord_whenMedicationChanges_shouldReturnTrue() throws Exception {
        final String FIRST_NAME = "Jacob";
        final String LAST_NAME = "Boyd";

        medicalRecord = new MedicalRecord();
        medicalRecord.setMedications(Collections.emptyList());
        medicalRecordService.update(FIRST_NAME, LAST_NAME, medicalRecord);

        mockMvc.perform( put("/medicalRecord/" + FIRST_NAME + '-' + LAST_NAME)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(medicalRecord)))
                .andExpect(jsonPath("$.firstName", is("Jacob")))
                .andExpect(jsonPath("$.medications").value(IsNull.nullValue()))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("DELETE - returns a boolean with the result of the delete demand //delete()")
    public  void givenExistantMedicalRecord_whenDeleteWithFirstANDLastName_shouldReturnTrue() throws Exception {
        final String FIRST_NAME = "Jacob";
        final String LAST_NAME = "Boyd";

        medicalRecordService.delete(FIRST_NAME, LAST_NAME);

        mockMvc.perform( delete("/medicalRecord/" + FIRST_NAME + '-' + LAST_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}