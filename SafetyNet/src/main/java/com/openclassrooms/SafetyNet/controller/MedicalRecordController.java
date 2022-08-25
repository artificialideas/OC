package com.openclassrooms.SafetyNet.controller;

import com.openclassrooms.SafetyNet.model.MedicalRecord;
import com.openclassrooms.SafetyNet.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    /**
     * READ - Get MedicalRecord collection
     * @return - An Iterable object of MedicalRecord with all its details
     */
    @GetMapping("/")
    public Iterable<MedicalRecord> list() {
        return medicalRecordService.list();
    }

    /**
     * CREATE - Add a new MedicalRecord resource
     * @return - A response with all the records and the new medical record
     */
    @PostMapping("/")
    public ResponseEntity<MedicalRecord> create(
            @RequestBody MedicalRecord newMedicalRecord) {
        MedicalRecord medicalRecord = medicalRecordService.create(newMedicalRecord);
        if (Objects.isNull(medicalRecord)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(medicalRecord, HttpStatus.CREATED);
        }
    }

    /**
     * UPDATE - Modify an existing MedicalRecord resource
     * @return - A response with the updated record details
     */
    @PutMapping("/{firstName}-{lastName}")
    public ResponseEntity<MedicalRecord> update(
            @PathVariable(value = "firstName", required = true) String firstName,
            @PathVariable(value = "lastName", required = true) String lastName,
            @RequestBody MedicalRecord medicalRecordDetails) {
        MedicalRecord updatedMedicalRecord = medicalRecordService.update(firstName, lastName, medicalRecordDetails);
        return ResponseEntity.ok(updatedMedicalRecord);
    }

    /**
     * DELETE - Delete an existing MedicalRecord resource
     * @return - A boolean response with de delete result (true or false)
     */
    @DeleteMapping("/{firstName}-{lastName}")
    public Boolean delete(
            @PathVariable(value = "firstName", required = true) String firstName,
            @PathVariable(value = "lastName", required = true) String lastName) {
        return medicalRecordService.delete(firstName, lastName);
    }
}
