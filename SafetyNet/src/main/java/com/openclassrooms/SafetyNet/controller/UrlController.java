package com.openclassrooms.SafetyNet.controller;

import com.openclassrooms.SafetyNet.DTO.*;
import com.openclassrooms.SafetyNet.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/")
public class UrlController {
    @Autowired
    private UrlService urlService;

    /**
     * READ - Filter People through a selected firestation number
     * @return - A list of all persons as well as total of adults/children
     */
    @GetMapping("firestation")
    public PersonListByStationDTO getPersonsByStation(
            @RequestParam(value = "stationNumber", required = true) int station) {
        return urlService.getPersonsByStation(station);
    }


    /**
     * READ - Filter Person through address
     * @return - A list of all members living in the same house
     */
    @GetMapping("childAlert")
    public MedicalRecordFamilyDTO getPersonsByFamily(
            @RequestParam(value = "address", required = true) String address) {
        return urlService.getPersonsByFamily(address);
    }

    /**
     * READ - Filter Person collection through firestation number
     * @return - A list of all phones from people in the same area
     */
    @GetMapping("phoneAlert")
    public List<String> getPhonesByStation(
            @RequestParam(value = "firestation", required = true) int station) {
        return urlService.getPhonesByStation(station);
    }

    /**
     * READ - Filter Person and Medical Record collections through address
     * @return - A list of all families covered by the same firestation
     */
    @GetMapping("fire")
    public FirestationByFamilyAddressDTO getFamilyByAddress(
            @RequestParam(value = "address", required = true) String address) {
        return urlService.getFamilyByAddress(address);
    }

    /**
     * READ - Filter Person and Medical Record collections through station number
     * @return - A list of all people covered by the same firestation
     */
    @GetMapping("flood/stations")
    public List<FirestationByFamilyDetailsDTO> getFamilyByStation(
            @RequestParam(value = "stations", required = true) String stations) {
        List<Integer> integerList = new ArrayList<>();

        Pattern numPattern = Pattern.compile("(\\d+)");
        Matcher matcher = numPattern.matcher(stations);
        while (matcher.find()) {
            integerList.add(Integer.valueOf(matcher.group()));
        }

        return urlService.getFamilyByStation(integerList);
    }

    /**
     * READ - Filter Person collection through first and last names
     * @return - A list of all details of selected Person with list of names sharing same last name
     */
    @GetMapping("personInfo")
    public PersonDetailsDTO getPersonDetails(
            @RequestParam(value = "firstName", required = true) String firstName,
            @RequestParam(value = "lastName", required = true) String lastName) {
        return urlService.getPersonDetails(firstName, lastName);
    }

    /**
     * READ - Filter Person collection through city
     * @return - A list of all emails from the selected city
     */
    @GetMapping("communityEmail")
    public List<String> getEmailsByCity(
            @RequestParam(value = "city", required = true) String city) {
        return urlService.getEmailsByCity(city);
    }
}
