package com.openclassrooms.SafetyNet.controller;

import com.openclassrooms.SafetyNet.DTO.MedicalRecordFamilyDTO;
import com.openclassrooms.SafetyNet.DTO.PersonListByStationDTO;
import com.openclassrooms.SafetyNet.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("childAlert")
    public MedicalRecordFamilyDTO getPersonsByFamily(
            @RequestParam(value = "address", required = true) String address) {
        return urlService.getPersonsByFamily(address);
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
