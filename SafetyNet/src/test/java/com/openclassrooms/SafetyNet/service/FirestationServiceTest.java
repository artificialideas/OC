package com.openclassrooms.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.Firestation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class FirestationServiceTest {
    final int STATION = 2;
    final String ADDRESS = "29 15th St";

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
    @DisplayName(" returns Firestation resource if existant //search()")
    public void givenExistantFirestation_whenKnownStationANDAddress_shouldReturnFirestation() {
        // Existant mock resource
        firestation = new Firestation();
        firestation.setAddress("29 15th St");
        firestation.setStation(2);

        Firestation expected = firestation;
        Firestation actual = firestationService.search(STATION, ADDRESS);

        assertEquals(expected, actual);
    }
}