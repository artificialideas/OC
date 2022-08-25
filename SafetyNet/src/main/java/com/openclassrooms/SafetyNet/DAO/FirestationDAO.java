package com.openclassrooms.SafetyNet.DAO;

import com.openclassrooms.SafetyNet.model.DataObjects;
import com.openclassrooms.SafetyNet.model.Firestation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

public class FirestationDAO {
    private static final Logger logger = LogManager.getLogger("FirestationDAO");

    public Firestation getFirestationByStation(int station, String address) {
        for (Firestation firestation: DataObjects.getFirestations()) {
            if ((Objects.equals(firestation.getStation(), station)) || (Objects.equals(firestation.getAddress(), address))) {
                return firestation;
            }
        }
        return null;
    }

    public List<Firestation> getFirestations() {
        return DataObjects.firestations;
    }

    public Firestation save(Firestation firestation) {
        Firestation savedFirestation = new Firestation();
        savedFirestation.setAddress(firestation.getAddress());
        savedFirestation.setStation(firestation.getStation());

        return savedFirestation;
    }

    public Firestation update(int station, String address, Firestation firestationDetails) {
        Firestation updatedFirestation = this.getFirestationByStation(station, address);

        if ((updatedFirestation != null) && (firestationDetails != null)) {
            if (firestationDetails.getStation() != 0) updatedFirestation.setStation(firestationDetails.getStation());
            if (firestationDetails.getAddress() != null) updatedFirestation.setAddress(firestationDetails.getAddress());

            return updatedFirestation;
        } else {
            logger.error("Error updating new Firestation.");
            return null;
        }
    }

    public Boolean delete(int station, String address) {
        Firestation deletedFirestation = this.getFirestationByStation(station, address);

        if (deletedFirestation != null) {
            DataObjects.getFirestations().remove(deletedFirestation);
            ResponseEntity.ok(deletedFirestation);
            return true;
        } else {
            logger.error(station + " does not exist in our list.");
            return false;
        }
    }
}
