package com.openclassrooms.SafetyNet.controller;

import com.openclassrooms.SafetyNet.model.Firestation;
import com.openclassrooms.SafetyNet.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/firestation")
public class FirestationController {
    @Autowired
    private FirestationService firestationService;

    /**
     * READ - Get Firestation collection
     * @return - An Iterable object of Firestation with all its details
     */
    @GetMapping("/")
    public Iterable<Firestation> list() {
        return firestationService.list();
    }

    /**
     * CREATE - Add a new Firestation resource
     * @return - A response with all the firestations and the new firestation
     */
    @PostMapping("/")
    public ResponseEntity<Firestation> create(
            @RequestBody Firestation newFirestation) {
        Firestation firestation = firestationService.create(newFirestation);
        if (Objects.isNull(firestation)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(firestation, HttpStatus.CREATED);
        }
    }

    /**
     * UPDATE - Modify an existing Firestation resource
     * @return - A response with the updated firestation details
     */
    @PutMapping("/{station}/{address}")
    public ResponseEntity<Firestation> update(
            @PathVariable(value = "station", required = true) int station,
            @PathVariable(value = "address", required = true) String address,
            @RequestBody Firestation firestationDetails) {
        Firestation updatedFirestation = firestationService.update(station, address, firestationDetails);
        return ResponseEntity.ok(updatedFirestation);
    }

    /**
     * DELETE - Delete an existing Firestation resource
     * @return - A boolean response with de delete result (true or false)
     */
    @DeleteMapping("/{station}/{address}")
    public Boolean delete(
            @PathVariable(value = "station", required = true) int station,
            @PathVariable(value = "address", required = true) String address) {
        return firestationService.delete(station, address);
    }
}
