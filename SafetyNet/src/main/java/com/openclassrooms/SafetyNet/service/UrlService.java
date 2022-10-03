package com.openclassrooms.SafetyNet.service;

import com.openclassrooms.SafetyNet.DAO.FirestationDAO;
import com.openclassrooms.SafetyNet.DAO.MedicalRecordDAO;
import com.openclassrooms.SafetyNet.DAO.PersonDAO;
import com.openclassrooms.SafetyNet.DTO.*;
import com.openclassrooms.SafetyNet.model.Firestation;
import com.openclassrooms.SafetyNet.model.MedicalRecord;
import com.openclassrooms.SafetyNet.model.Person;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UrlService {
    private final PersonDAO personDAO = new PersonDAO();
    private final FirestationDAO firestationDAO = new FirestationDAO();
    private final MedicalRecordDAO medicalRecordDAO = new MedicalRecordDAO();

    public PersonListByStationDTO getPersonsByStation(int station) {
        List<String> addressStationList = new ArrayList<>();
        List<String> firstNameList = new ArrayList<>();
        int adults = 0;
        int children = 0;
        List<PersonDTO> allPersonsDTO = new ArrayList<>();

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
        for (MedicalRecord resource : medicalRecordCollection) {
            if (getBirthdateByMedicalRecord(resource.getBirthdate()) > 17) {
                adults++;
            } else {
                children++;
            }
        }

        // PersonDTO object to send
        for (Person personResource : personCollection) {
            PersonDTO personDTO = new PersonDTO();
            personDTO.setFirstName(personResource.getFirstName());
            personDTO.setLastName(personResource.getLastName());
            personDTO.setAddress(personResource.getAddress());
            personDTO.setPhone(personResource.getPhone());
            allPersonsDTO.add(personDTO);
        }
        PersonListByStationDTO personListByStationDTO = new PersonListByStationDTO();
        personListByStationDTO.setPeople(allPersonsDTO);
        personListByStationDTO.setAdults(adults);
        personListByStationDTO.setChildren(children);

        return personListByStationDTO;
    }

    public MedicalRecordFamilyDTO getPersonsByFamily(String address) {
        List<String> firstNameList = new ArrayList<>();
        List<MedicalRecordDTO> adultsDTO = new ArrayList<>();
        List<MedicalRecordDetailsDTO> familyDTO = new ArrayList<>();

        // Get all persons sharing the same address
        List<Person> personCollection = personDAO
                .getPersons()
                .stream()
                .filter(person -> (Objects.equals(person.getAddress(), address)))
                .collect(Collectors.toList());
        // Extract first name
        for (Person personResource : personCollection) {
            firstNameList.add(personResource.getFirstName());
        }

        // MedicalRecord collection filtered by firstName
        List<MedicalRecord> medicalRecordCollection = getMedicalRecordCollectionByFirstName(firstNameList);
        // Group by ages
        for (MedicalRecord medicalRecordResource : medicalRecordCollection) {
            int age = getBirthdateByMedicalRecord(medicalRecordResource.getBirthdate());
            if (age > 17) {
                // Adults
                MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
                medicalRecordDTO.setFirstName(medicalRecordResource.getFirstName());
                medicalRecordDTO.setLastName(medicalRecordResource.getLastName());
                adultsDTO.add(medicalRecordDTO);
            } else {
                // Children
                MedicalRecordDetailsDTO medicalRecordDetailsDTO = new MedicalRecordDetailsDTO();
                medicalRecordDetailsDTO.setFirstName(medicalRecordResource.getFirstName());
                medicalRecordDetailsDTO.setLastName(medicalRecordResource.getLastName());
                medicalRecordDetailsDTO.setAge(age);
                medicalRecordDetailsDTO.setAdults(adultsDTO);
                familyDTO.add(medicalRecordDetailsDTO);
            }
        }

        MedicalRecordFamilyDTO medicalRecordFamilyDTO = new MedicalRecordFamilyDTO();
        if (familyDTO.size() > 0) {
            medicalRecordFamilyDTO.setChildren(familyDTO);
            return medicalRecordFamilyDTO;
        }
        return null;
    }

    public List<String> getPhonesByStation(int station) {
        List<String> addressStationList = new ArrayList<>();
        List<String> cityPhones = new ArrayList<>();

        // Firestation collection filtered by given station number
        List<Firestation> firestationCollection = getFirestationCollectionByStation(station);
        // Extract addresses
        for (Firestation firestationResource : firestationCollection) {
            addressStationList.add(firestationResource.getAddress());
        }

        // Person collection filtered by selected station addresses
        List<Person> personCollection = getPersonCollectionByAddress(addressStationList);
        // Extract phone number and add it to return list (avoid repetitions)
        for (Person personResource : personCollection) {
            if (!(cityPhones.contains(personResource.getPhone()))) {
                cityPhones.add(personResource.getPhone());
            }
        }

        return cityPhones;
    }

    public FirestationByFamilyAddressDTO getFamilyByAddress(String address) {
        List<MedicalRecordFullRapportDTO> familyDTO = new ArrayList<>();

        // Get all persons sharing the same address
        List<Person> personCollection = personDAO
                .getPersons()
                .stream()
                .filter(person -> (Objects.equals(person.getAddress(), address)))
                .collect(Collectors.toList());
        // Extract first name
        for (Person personResource : personCollection) {
            MedicalRecord medicalRecordResource = medicalRecordDAO
                    .getMedicalRecords()
                    .stream()
                    .filter(person -> person.getFirstName().equals(personResource.getFirstName()))
                    .findFirst()
                    .orElse(null);
            int age = getBirthdateByMedicalRecord(medicalRecordResource.getBirthdate());

            MedicalRecordFullRapportDTO medicalRecordFullRapportDTO = new MedicalRecordFullRapportDTO();
            medicalRecordFullRapportDTO.setFirstName(medicalRecordResource.getFirstName());
            medicalRecordFullRapportDTO.setLastName(medicalRecordResource.getLastName());
            medicalRecordFullRapportDTO.setPhone(personResource.getPhone());
            medicalRecordFullRapportDTO.setAge(age);
            medicalRecordFullRapportDTO.setMedications(medicalRecordResource.getMedications());
            medicalRecordFullRapportDTO.setAllergies(medicalRecordResource.getAllergies());
            familyDTO.add(medicalRecordFullRapportDTO);
        }
        familyDTO.sort((a, b) -> b.getAge() - a.getAge());
        FirestationByFamilyAddressDTO firestationByFamilyAddressDTO = new FirestationByFamilyAddressDTO();
        firestationByFamilyAddressDTO.setStation(getFirestationResourceByAddress(address).getStation());
        firestationByFamilyAddressDTO.setFamily(familyDTO);

        return firestationByFamilyAddressDTO;
    }

    public List<FirestationByFamilyDetailsDTO> getFamilyByStation(List<Integer> stations) { //ToDo fix last logic (print person if equals address)
        String currentFirestationAddress = "";
        List<MedicalRecordFullRapportDTO> personMedicalRecord = new ArrayList<>();
        List<FirestationByFamilyDetailsDTO> allPeople = new ArrayList<>();

        // Firestation collection filtered by given list of station numbers
        List<Firestation> firestationCollection = firestationDAO
                .getFirestations()
                .stream()
                .filter(firestation -> stations.contains(firestation.getStation()))
                .collect(Collectors.toList());
        // Extract addresses
        for (Firestation firestationResource : firestationCollection) {
            currentFirestationAddress = firestationResource.getAddress();
            // Avoid repetitions using station address
            if (!(allPeople.contains(currentFirestationAddress))) {
                // For each address, find who lives in there
                List<Person> personCollection = personDAO
                        .getPersons()
                        .stream()
                        .filter(person -> (Objects.equals(person.getAddress(), firestationResource.getAddress())))
                        .collect(Collectors.toList());

                // For each Person, get its Medical Record
                for (Person personResource : personCollection) {
                    if (Objects.equals(personResource.getAddress(), firestationResource.getAddress())) {
                        MedicalRecord medicalRecordResource = medicalRecordDAO
                                .getMedicalRecords()
                                .stream()
                                .filter(person -> person.getFirstName().equals(personResource.getFirstName()))
                                .findFirst()
                                .orElse(null);
                        int age = getBirthdateByMedicalRecord(medicalRecordResource.getBirthdate());

                        MedicalRecordFullRapportDTO medicalRecordFullRapportDTO = new MedicalRecordFullRapportDTO();
                        medicalRecordFullRapportDTO.setFirstName(medicalRecordResource.getFirstName());
                        medicalRecordFullRapportDTO.setLastName(medicalRecordResource.getLastName());
                        medicalRecordFullRapportDTO.setPhone(personResource.getPhone());
                        medicalRecordFullRapportDTO.setAge(age);
                        medicalRecordFullRapportDTO.setMedications(medicalRecordResource.getMedications());
                        medicalRecordFullRapportDTO.setAllergies(medicalRecordResource.getAllergies());
                        personMedicalRecord.add(medicalRecordFullRapportDTO);
                    }
                }

                System.out.println("personMedicalRecord" + personMedicalRecord);
                FirestationByFamilyDetailsDTO firestationByFamilyDetailsDTO = new FirestationByFamilyDetailsDTO();
                firestationByFamilyDetailsDTO.setAddress(firestationResource.getAddress());
                firestationByFamilyDetailsDTO.setPeople(personMedicalRecord);
                allPeople.add(firestationByFamilyDetailsDTO);
            }
        }

        return allPeople;
    }

    public List<String> getEmailsByCity(String city) {
        List<String> cityEmails = new ArrayList<>();

        List<Person> personCollection = (personDAO
                .getPersons()
                .stream()
                .filter(person -> (Objects.equals(person.getCity(), city)))
                .collect(Collectors.toList()));

        for (Person personResource : personCollection) {
            cityEmails.add(personResource.getEmail());
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
    Firestation getFirestationResourceByAddress(String address) {
        return (firestationDAO
                .getFirestations()
                .stream()
                .filter(x -> x.getAddress().equals(address))
                .findFirst()
                .orElse(null));
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

    int getBirthdateByMedicalRecord(String birthDay) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = LocalDate.parse(birthDay, DateTimeFormatter.ofPattern("MM/dd/yyyy"));

        return Period.between(birthDate, currentDate).getYears();
    }
}
