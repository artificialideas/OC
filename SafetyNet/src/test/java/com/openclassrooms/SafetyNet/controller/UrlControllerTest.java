package com.openclassrooms.SafetyNet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openclassrooms.SafetyNet.model.DataObjects;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlControllerTest {
    final int STATION = 2;
    final String ADDRESS = "1509 Culver St";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() throws IOException {
        objectMapper.readValue(new File("resources/data.json"), DataObjects.class);
    }

    @Test
    @DisplayName("GET - returns list of people under the same firestation area //getPersonsByStation()")
    public void givenPerson_whenStationNumber_shouldReturnListOfPersonsWithDetails() throws Exception {
        mockMvc.perform( get("/firestation?stationNumber=" + STATION)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.people.[0].firstName", is("Jonanathan")))
                .andExpect(jsonPath("$.people.[0].lastName", is("Marrack")))
                .andExpect(jsonPath("$.people.[0].address", is("29 15th St")))
                .andExpect(jsonPath("$.people.[0].phone", is("841-874-6513")))
                .andExpect(jsonPath("$.adults", is(4)))
                .andExpect(jsonPath("$.children", is(1)));
    }

    @Test
    @DisplayName("GET - returns list of children with their relatives living in given address //getPersonsByFamily()")
    public void givenPerson_whenAddress_shouldReturnListOfChildrenANDRelatives() throws Exception {
        mockMvc.perform( get("/childAlert?address=" + ADDRESS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.children.[0].firstName", is("Tenley")))
                .andExpect(jsonPath("$.children.[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$.children.[0].age", is(10)))
                .andExpect(jsonPath("$.children.[0].adults.[0].firstName", is("John")));
    }

    @Test
    @DisplayName("GET - returns list of phones related to given firestation number //getPhonesByStation()")
    public void givenPerson_whenStationNumber_shouldReturnListOfPhones() throws Exception {
        mockMvc.perform( get("/phoneAlert?firestation=" + STATION)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0]", is("841-874-6513")));
    }

    @Test
    @DisplayName("GET - returns list of residents living in given address //getFamilyByAddress()")
    public void givenPersonANDFirestation_whenAddress_shouldReturnResidentsANDStation() throws Exception {
        mockMvc.perform( get("/fire?address=" + ADDRESS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.station", is(3)))
                .andExpect(jsonPath("$.family.[0].firstName", is("John")))
                .andExpect(jsonPath("$.family.[0].phone", is("841-874-6512")))
                .andExpect(jsonPath("$.family.[0].age", is(38)))
                .andExpect(jsonPath("$.family.[0].allergies.[0]", is("nillacilan")));
    }

    @Test
    @DisplayName("GET - returns list of residents living in given list of stations //getFamilyByStation()")
    public void givenPersonANDFirestation_whenListOfStationNumbers_shouldReturnListOfResidents() throws Exception {
        mockMvc.perform( get("/flood/stations?stations=" + "2 4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].address", is("29 15th St")))
                .andExpect(jsonPath("$.[0].people.[0].firstName", is("Jonanathan")))
                .andExpect(jsonPath("$.[0].people.[0].phone", is("841-874-6513")))
                .andExpect(jsonPath("$.[0].people.[0].age", is(33)))
                .andExpect(jsonPath("$.[0].people.[0].medications", Matchers.hasSize(0)))
                .andExpect(jsonPath("$.[1].address", is("489 Manchester St")))
                .andExpect(jsonPath("$.[2].address", is("892 Downing Ct")))
                .andExpect(jsonPath("$.[2].people.[0].firstName", is("Sophia")));
    }

    @Test
    @DisplayName("GET - returns Person details given first and last names //getPersonDetails()")
    public void givenPersonANDMedicalRecord_whenFirstANDLastName_shouldReturnPersonDetails() throws Exception {
        final String FIRST = "Tony";
        final String LAST = "Cooper";

        mockMvc.perform( get("/personInfo?firstName=" + FIRST + "&lastName=" + LAST)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is("Tony")))
                .andExpect(jsonPath("$.address", is("112 Steppes Pl")))
                .andExpect(jsonPath("$.age", is(28)))
                .andExpect(jsonPath("$.email", is("tcoop@ymail.com")))
                .andExpect(jsonPath("$.medications", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.relatives.[0].firstName", is("Lily")))
                .andExpect(jsonPath("$.relatives.[0].lastName", is("Cooper")));
    }

    @Test
    @DisplayName("GET - returns list of emails related to given city //getEmailsByCity()")
    public void givenPerson_whenCity_shouldReturnListOfEmails() throws Exception {
        final String CITY = "Culver";

        mockMvc.perform( get("/communityEmail?city=" + CITY)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0]", is("jaboyd@email.com")));
    }
}
