package com.openclassrooms.SafetyNet.service;

import com.openclassrooms.SafetyNet.DAO.MedicalRecordDAO;
import com.openclassrooms.SafetyNet.model.MedicalRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalRecordService {
    private final MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    public MedicalRecord search(String firstName, String lastName) {
        return medicalRecordDAO.getMedicalRecordByFullName(firstName, lastName);
    }

    public List<MedicalRecord> list() {
        return medicalRecordDAO.getMedicalRecords();
    }

    public List<MedicalRecord> getPeopleAndBirthdatesByMedicalRecord(String firstName, String lastName) {
        return medicalRecordDAO.getPeopleAndBirthdatesByMedicalRecord(firstName, lastName);
    }

    public MedicalRecord create(MedicalRecord medicalRecord) {
        return medicalRecordDAO.save(medicalRecord);
    }

    public MedicalRecord update(String firstName, String lastName, MedicalRecord medicalRecordDetails) {
        return medicalRecordDAO.update(firstName, lastName, medicalRecordDetails);
    }

    public Boolean delete(String firstName, String lastName) {
        return medicalRecordDAO.delete(firstName, lastName);
    }
}
