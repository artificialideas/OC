package com.openclassrooms.SafetyNet.DAO;

import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.MedicalRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MedicalRecordDAO {
    private static final Logger logger = LogManager.getLogger("MedicalRecordDAO");

    public MedicalRecord getMedicalRecordByFullName(String first, String last) {
        for (MedicalRecord medicalRecord: DataObjects.getMedicalrecords()) {
            if ((Objects.equals(medicalRecord.getFirstName(), first)) && (Objects.equals(medicalRecord.getLastName(), last))) {
                return medicalRecord;
            }
        }
        return null;
    }

    public List<MedicalRecord> getMedicalRecords() {
        return DataObjects.medicalrecords;
    }

    public MedicalRecord save(MedicalRecord medicalRecord) {
        MedicalRecord savedMedicalRecord = new MedicalRecord();
        savedMedicalRecord.setFirstName(medicalRecord.getFirstName());
        savedMedicalRecord.setLastName(medicalRecord.getLastName());
        savedMedicalRecord.setBirthdate(medicalRecord.getBirthdate());
        savedMedicalRecord.setMedications(medicalRecord.getMedications());
        savedMedicalRecord.setAllergies(medicalRecord.getAllergies());

        return savedMedicalRecord;
    }

    public MedicalRecord update(String first, String last, MedicalRecord medicalRecordDetails) {
        MedicalRecord updatedMedicalRecord = this.getMedicalRecordByFullName(first,last);

        if ((updatedMedicalRecord != null) && (medicalRecordDetails != null)) {
            //if (medicalRecordDetails.getFirstName() != null) updatedMedicalRecord.setFirstName(medicalRecordDetails.getFirstName());
            //if (medicalRecordDetails.getLastName() != null) updatedMedicalRecord.setLastName(medicalRecordDetails.getLastName());
            if (medicalRecordDetails.getBirthdate() != null) updatedMedicalRecord.setBirthdate(medicalRecordDetails.getBirthdate());

            // Lists may be empty, so we must compare old values with updated values
            // Medications list
            if (medicalRecordDetails.getMedications() != null) {
                Collections.sort(updatedMedicalRecord.getMedications());
                Collections.sort(medicalRecordDetails.getMedications());
                if (!(medicalRecordDetails.getMedications().equals(updatedMedicalRecord.getMedications())))
                    updatedMedicalRecord.setMedications(medicalRecordDetails.getMedications());
            }
            // Allergies list
            if (medicalRecordDetails.getAllergies() != null) {
                Collections.sort(updatedMedicalRecord.getAllergies());
                Collections.sort(medicalRecordDetails.getAllergies());
                if (!(medicalRecordDetails.getAllergies().equals(updatedMedicalRecord.getAllergies())))
                    updatedMedicalRecord.setAllergies(medicalRecordDetails.getAllergies());
            }

            return updatedMedicalRecord;
        } else {
            logger.error("Error updating new Medical Record.");
            return null;
        }
    }

    public Boolean delete(String first, String last) {
        MedicalRecord deletedMedicalRecord = this.getMedicalRecordByFullName(first,last);

        if (deletedMedicalRecord != null) {
            DataObjects.getMedicalrecords().remove(deletedMedicalRecord);
            return true;
        } else {
            logger.error(first + ' ' + last + " does not exist in our list.");
            return false;
        }
    }
}
