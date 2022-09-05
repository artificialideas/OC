package com.openclassrooms.SafetyNet.DAO;


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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationDAOTest {
    final String ADDRESS = "29 15th St";
    final int STATION = 2;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Firestation firestation;
    private static FirestationDAO firestationDAO;

    @BeforeEach
    public void setup() throws IOException {
        objectMapper.readValue(new File("resources/data.json"), DataObjects.class);

        firestationDAO = new FirestationDAO();
    }

    @Test
    @DisplayName("returns null for non existant Firestation //getFirestationByStation()")
    public void givenNonExistentFirestation_whenGetFirestation_shouldReturnNull() {
        assertNull(firestationDAO.getFirestationByStation(1, ADDRESS));
    }

    @Test
    @DisplayName("returns null for non existant Person //update()")
    public void givenNonExistantPerson_whenUpdate_shouldReturnNull() {
        assertNull(firestationDAO.update(STATION, ADDRESS, null));
    }

    @Test
    @DisplayName("returns false for non existant Person //delete()")
    public void givenNonExistantPerson_whenDelete_shouldReturnFalse() {
        assertFalse(firestationDAO.delete(STATION, "112 Steppes Pl"));
    }
}
