package com.openclassrooms.SafetyNet.service;

import com.openclassrooms.SafetyNet.DAO.FirestationDAO;
import com.openclassrooms.SafetyNet.DAO.MedicalRecordDAO;
import com.openclassrooms.SafetyNet.DAO.PersonDAO;
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
        List<String> addressStation = new ArrayList<>();
        List<String> firstName = new ArrayList<>();
        List<Integer> birthDate = new ArrayList<>();
        List<PersonDetailsDTO> result = new ArrayList<>();

        // Firestation collection filtered by given station number
        List<Firestation> fsCollection = (firestationDAO
                .getFirestations()
                .stream()
                .filter(firestation -> (Objects.equals(firestation.getStation(), station)))
                .collect(Collectors.toList()));
        // Extract addresses
        for (Firestation resource : fsCollection) {
            addressStation.add(resource.getAddress());
        }

        // Person collection filtered by selected station addresses
        List<Person> pCollection = (personDAO
                .getPersons()
                .stream()
                .filter(person -> addressStation.contains(person.getAddress()))
                .collect(Collectors.toList()));
        for (Person resource : pCollection) {
            firstName.add(resource.getFirstName());
        }

        // MedicalRecord collection filtered by firstName
        List<MedicalRecord> mdCollection = (medicalRecordDAO
                .getMedicalRecords()
                .stream()
                .filter(person -> firstName.contains(person.getFirstName()))
                .collect(Collectors.toList()));
        int adults = 0;
        int children = 0;
        for (MedicalRecord resource : mdCollection) {
            birthDate.add(getBirthdateByMedicalRecord(resource.getBirthdate()));
            if (getBirthdateByMedicalRecord(resource.getBirthdate()) > 17) {
                adults++;
            } else {
                children++;
            }
        }

        // Create our UrlDTO object
        for (Person resource : pCollection) {
            PersonDetailsDTO personDetailsDTO = new PersonDetailsDTO();
            personDetailsDTO.setFirstName(resource.getFirstName());
            personDetailsDTO.setLastName(resource.getLastName());
            personDetailsDTO.setAddress(resource.getAddress());
            personDetailsDTO.setPhone(resource.getPhone());
            result.add(personDetailsDTO);
        }
        PersonListByStationDTO personListByStationDTO = new PersonListByStationDTO();
        personListByStationDTO.setPersonDetailsDTOS(result);
        personListByStationDTO.setAdults(adults);
        personListByStationDTO.setChildren(children);

        return personListByStationDTO;
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
    public int getBirthdateByMedicalRecord(String birthDateString) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return Period.between(birthDate, currentDate).getYears();
    }
}
