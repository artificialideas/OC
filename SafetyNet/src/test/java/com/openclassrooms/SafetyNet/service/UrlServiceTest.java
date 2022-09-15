package com.openclassrooms.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private UrlService urlService;

    @BeforeEach
    public void setup() throws IOException {
        objectMapper.readValue(new File("resources/data.json"), DataObjects.class);

        urlService = new UrlService();
    }

    @Test
    @DisplayName("returns a list of emails through given city //getEmailsByCity()")
    public void givenPerson_whenCity_shouldReturnListOfEmails() {
        assert(urlService.getEmailsByCity("Culver").contains("jaboyd@email.com"));
    }
}
