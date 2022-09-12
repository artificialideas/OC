package com.openclassrooms.SafetyNet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class JsonReaderTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private JsonReader jsonReader;
    private DataObjects dataObjects;

    @BeforeEach
    public void setup() throws IOException {
        objectMapper.readValue(new File("resources/data.json"), DataObjects.class);

        jsonReader = new JsonReader();
    }

    @Disabled
    @Test
    @DisplayName(" returns Person collection if existant Json //readFile()")
    public void givenExistantJsonFile_whenReadPerson_shouldReturnPersonCollection() throws IOException {
        List<Person> returns = new ArrayList<>();
        dataObjects = mock(DataObjects.class);

        when(dataObjects.getPersons().size()).thenReturn(23);
    }
}