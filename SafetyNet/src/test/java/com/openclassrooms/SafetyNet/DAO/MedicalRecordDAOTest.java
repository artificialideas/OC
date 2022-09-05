package com.openclassrooms.SafetyNet.DAO;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.MedicalRecord;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordDAOTest {
    final String FIRST_NAME = "Jonanathan";
    final String LAST_NAME = "Marrack";

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
}
