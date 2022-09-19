package com.openclassrooms.SafetyNet.service;

import com.openclassrooms.SafetyNet.DAO.FirestationDAO;
import com.openclassrooms.SafetyNet.DAO.MedicalRecordDAO;
import com.openclassrooms.SafetyNet.DAO.PersonDAO;
import com.openclassrooms.SafetyNet.DTO.PersonDTO;
import com.openclassrooms.SafetyNet.DTO.PersonDetailsDTO;
import com.openclassrooms.SafetyNet.DTO.PersonListByStationDTO;
import com.openclassrooms.SafetyNet.model.Firestation;
import com.openclassrooms.SafetyNet.model.MedicalRecord;
import com.openclassrooms.SafetyNet.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UrlService {
    private final PersonDAO personDAO = new PersonDAO();
    private final FirestationDAO firestationDAO = new FirestationDAO();
    private final MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    public PersonListByStationDTO getPersonsByStation(int station) {
        List<String> addressStationList = new ArrayList<>();
        List<String> firstNameList = new ArrayList<>();
        List<PersonDetailsDTO> result = new ArrayList<>();

        // Firestation collection filtered by given station number
        List<Firestation> firestationCollection = getFirestationCollectionByStation(station);
        // Extract addresses
        for (Firestation firestationResource : firestationCollection) {
            addressStationList.add(firestationResource.getAddress());
        }

        // Person collection filtered by selected station addresses
        List<Person> personCollection = getPersonCollectionByAddress(addressStationList);
        // Extract first name
        for (Person personResource : personCollection) {
            firstNameList.add(personResource.getFirstName());
        }

        // MedicalRecord collection filtered by firstName
        List<MedicalRecord> medicalRecordCollection = getMedicalRecordCollectionByFirstName(firstNameList);
        // Extract birthdate
        int adults = 0;
        int children = 0;
        for (MedicalRecord resource : medicalRecordCollection) {
            if (getBirthdateByMedicalRecord(resource.getBirthdate()) > 17) {
                adults++;
            } else {
                children++;
            }
        }

        // PersonDetailsDTO object to send
        for (Person personResource : personCollection) {
            PersonDetailsDTO personDetailsDTO = new PersonDetailsDTO();
            personDetailsDTO.setFirstName(personResource.getFirstName());
            personDetailsDTO.setLastName(personResource.getLastName());
            personDetailsDTO.setAddress(personResource.getAddress());
            personDetailsDTO.setPhone(personResource.getPhone());
            result.add(personDetailsDTO);
        }
        PersonListByStationDTO personListByStationDTO = new PersonListByStationDTO();
        personListByStationDTO.setPersonDetailsDTOS(result);
        personListByStationDTO.setAdults(adults);
        personListByStationDTO.setChildren(children);

        return personListByStationDTO;
    }

    public List<PersonDTO> getPersonsByFamily(String address) {
        List<Person> personCollection = getPersonCollectionByAddress(address);
    }

    public List<String> getEmailsByCity(String city) {
        List<String> cityEmails = new ArrayList<>();
        List<Person> pCollection = (personDAO
                .getPersons()
                .stream()
                .filter(person -> (Objects.equals(person.getCity(), city)))
                .collect(Collectors.toList()));

        for (Person resource : pCollection) {
            cityEmails.add(resource.getEmail());
        }
        return cityEmails;
    }

    /** Methods */
    List<Firestation> getFirestationCollectionByStation(int station) {
        return (firestationDAO
                .getFirestations()
                .stream()
                .filter(firestation -> (Objects.equals(firestation.getStation(), station)))
                .collect(Collectors.toList()));
    }

    List<Person> getPersonCollectionByAddress(List<String> addresses) {
        return (personDAO
                .getPersons()
                .stream()
                .filter(person -> addresses.contains(person.getAddress()))
                .collect(Collectors.toList()));
    }

    List<MedicalRecord> getMedicalRecordCollectionByFirstName(List<String> firstNames) {
        return (medicalRecordDAO
                .getMedicalRecords()
                .stream()
                .filter(person -> firstNames.contains(person.getFirstName()))
                .collect(Collectors.toList()));
    }

    int getBirthdateByMedicalRecord(String birthDateString) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return Period.between(birthDate, currentDate).getYears();
    }
}
