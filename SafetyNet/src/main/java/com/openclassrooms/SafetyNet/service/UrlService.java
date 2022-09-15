package com.openclassrooms.SafetyNet.service;

import com.openclassrooms.SafetyNet.DAO.FirestationDAO;
import com.openclassrooms.SafetyNet.DAO.MedicalRecordDAO;
import com.openclassrooms.SafetyNet.DAO.PersonDAO;
import com.openclassrooms.SafetyNet.DTO.UrlDTO;
import com.openclassrooms.SafetyNet.model.Firestation;
import com.openclassrooms.SafetyNet.model.MedicalRecord;
import com.openclassrooms.SafetyNet.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UrlService {
    private final PersonDAO personDAO = new PersonDAO();
    private final FirestationDAO firestationDAO = new FirestationDAO();
    private final MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    public List<UrlDTO> getPersonsByStation(int station) {
        List<String> addressStation = new ArrayList<>();
        List<String> firstName = new ArrayList<>();
        List<Integer> birthDate = new ArrayList<>();
        List<UrlDTO> result = new ArrayList<>();

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
                .filter(person -> Arrays.asList(addressStation).contains(person.getAddress()))
                .collect(Collectors.toList()));
        for (Person resource : pCollection) {
            firstName.add(resource.getFirstName());
        }

        // MedicalRecord collection filtered by firstName
        List<MedicalRecord> mdCollection = (medicalRecordDAO
                .getMedicalRecords()
                .stream()
                .filter(person -> Arrays.asList(firstName).contains(person.getFirstName()))
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
            UrlDTO toReturn = new UrlDTO();
            toReturn.setFirstName(resource.getFirstName());
            toReturn.setLastName(resource.getLastName());
            toReturn.setAddress(resource.getAddress());
            toReturn.setPhone(resource.getPhone());
            result.add(toReturn);
        }
        String info = ("For station number " + station + " there are " + adults + " adults and " + children + " children (less than 17 years old).");
System.out.println(info);
        return result;
    }

    public int getBirthdateByMedicalRecord(String birthDateString) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = LocalDate.parse(birthDateString, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return Period.between(birthDate, currentDate).getYears();
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

    private UrlDTO convertDataIntoDTO (UrlDTO data) {
        // create instance of our UrlDTO class
        UrlDTO dto = new UrlDTO();

        //set all data in dto from the UrlDTO
        dto.setFirstName(data.getFirstName());
        dto.setLastName(data.getLastName());
        dto.setAddress(data.getAddress());
        dto.setCity(data.getCity());
        dto.setPhone(data.getPhone());
        dto.setEmail(data.getEmail());
        dto.setStation(data.getStation());
        dto.setBirthdate(data.getBirthdate());
        dto.setMedications(data.getMedications());
        dto.setAllergies(data.getAllergies());

        return dto;
    }
}
