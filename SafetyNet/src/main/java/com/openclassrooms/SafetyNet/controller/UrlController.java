package com.openclassrooms.SafetyNet.controller;

import com.openclassrooms.SafetyNet.DTO.UrlDTO;
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
     * READ
     * @return
     */
    @GetMapping("firestation")
    public List<UrlDTO> getPersonsByStation(
            @RequestParam(value = "stationNumber", required = true) int station) {
        return urlService.getPersonsByStation(station);
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
