package com.openclassrooms.SafetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.Firestation;
import com.openclassrooms.SafetyNet.service.FirestationService;
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

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Firestation firestation;
    private FirestationService firestationService;

    @BeforeEach
    public void setup() throws IOException {
        objectMapper.readValue(new File("resources/data.json"), DataObjects.class);

        firestationService = new FirestationService();
    }

    @Test
    @DisplayName("GET - returns Firestation collection list //list()")
    public void givenJSONFile_whenGetFirestation_shouldReturnCollectionOfFirestation() throws Exception {
        mockMvc.perform( get("/firestation/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].station", is(3)))
                .andExpect(jsonPath("$.[0].address", is("1509 Culver St")));
    }

    @Test
    @DisplayName("POST - returns a new added Firestation resource //create()")
    public void givenNewFirestation_whenStationANDAddress_shouldReturnFirestationResource() throws Exception {
        // Mock resource
        firestation = new Firestation();
        firestation.setAddress("951 LoneTree Rd");
        firestation.setStation(4);
        //personService.create(firestation);

        MvcResult result = mockMvc.perform( post("/firestation/")
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .content(objectMapper.writeValueAsString(firestation)))
                                    .andExpect(jsonPath("$.station", is(4)))
                                    .andExpect(jsonPath("$.address", is("951 LoneTree Rd")))
                                    .andExpect(status().is2xxSuccessful())
                                    .andReturn();
        assertNotNull(result);
    }

    @Test
    @DisplayName("PUT - returns the updated details of an existant Firestation resource //update()")
    public void givenExistantFirestation_whenStationORAddressChanges_shouldReturnTrue() throws Exception {
        final int STATION = 4;
        final String ADDRESS = "112 Steppes Pl";

        firestation = new Firestation();
        firestation.setAddress("60 ThunderBolt St");
        firestationService.update(STATION, ADDRESS, firestation);

        mockMvc.perform( put("/firestation/" + STATION + '/' + ADDRESS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firestation)))
                .andExpect(jsonPath("$.station", is(4)))
                .andExpect(jsonPath("$.address", is("60 ThunderBolt St")))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("DELETE - returns a boolean with the result of the delete demand //delete()")
    public  void givenExistantFirestation_whenDeleteWithStationANDAddress_shouldReturnTrue() throws Exception {
        final int STATION = 2;
        final String ADDRESS = "951 LoneTree Rd";

        firestationService.delete(STATION, ADDRESS);

        mockMvc.perform( delete("/firestation/" + STATION + '/' + ADDRESS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}