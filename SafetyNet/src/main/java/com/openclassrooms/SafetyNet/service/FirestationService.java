package com.openclassrooms.SafetyNet.service;

import com.openclassrooms.SafetyNet.DAO.FirestationDAO;
import com.openclassrooms.SafetyNet.model.Firestation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {
    private final FirestationDAO firestationDAO = new FirestationDAO();

    public Firestation search(int station, String address) {
        return firestationDAO.getFirestationByStation(station, address);
    }

    public List<Firestation> list() {
        return firestationDAO.getFirestations();
    }

    public Firestation create(Firestation firestation) {
        return firestationDAO.save(firestation);
    }

    public Firestation update(int station, String address, Firestation firestationDetails) {
        return firestationDAO.update(station, address, firestationDetails);
    }

    public Boolean delete(int station, String address) {
        return firestationDAO.delete(station, address);
    }

//    public List<String> getFirestationsByStation(int station) {
//        firestationDAO.getFirestationsByStation(station);
//        if (firestationDAO.getFirestationsByStation(station).size() > 0) {
//        }
//    }
}
