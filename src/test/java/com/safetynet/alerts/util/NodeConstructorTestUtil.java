package com.safetynet.alerts.util;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;

public class NodeConstructorTestUtil {

    public FireStationsDTO createValidFireStationsDTO() {
        final FireStationsDTO fireStationsDTO = new FireStationsDTO();
        fireStationsDTO.setAddress("default address");
        fireStationsDTO.setStation(1);
        return fireStationsDTO;
    }

    public FireStationsDTO createInvalidFireStationsDTO() {
        final FireStationsDTO fireStationsDTO = new FireStationsDTO();
        fireStationsDTO.setAddress("");
        fireStationsDTO.setStation(0);
        return fireStationsDTO;
    }

    public FireStationsDTO createCustomFireStationsDTO(String address, Integer stationNumber) {
        final FireStationsDTO fireStationsDTO = new FireStationsDTO();
        fireStationsDTO.setAddress(address);
        fireStationsDTO.setStation(stationNumber);
        return fireStationsDTO;
    }

    public MedicalRecordsDTO createValidMedicalRecordsDTO() {
        final MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
        medicalRecordsDTO.setFirstName("default first name");
        medicalRecordsDTO.setLastName("default last name");
        medicalRecordsDTO.setBirthdate(Date.from(Instant.now()));
        medicalRecordsDTO.setMedications(null);
        medicalRecordsDTO.setAllergies(null);
        return medicalRecordsDTO;
    }

    public MedicalRecordsDTO createInvalidMedicalRecordsDTO() {
        final MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
        medicalRecordsDTO.setFirstName("default first name");
        medicalRecordsDTO.setLastName("default last name");
        medicalRecordsDTO.setBirthdate(null);
        medicalRecordsDTO.setMedications(null);
        medicalRecordsDTO.setAllergies(null);
        return medicalRecordsDTO;
    }

    public MedicalRecordsDTO createCustomMedicalRecordsDTO(String firstName, String lastName, Date birthdate, List<String> medications,
            List<String> allergies) {
        final MedicalRecordsDTO medicalRecordsDTO = new MedicalRecordsDTO();
        medicalRecordsDTO.setFirstName(firstName);
        medicalRecordsDTO.setLastName(lastName);
        medicalRecordsDTO.setBirthdate(birthdate);
        medicalRecordsDTO.setMedications(medications);
        medicalRecordsDTO.setAllergies(allergies);
        return medicalRecordsDTO;
    }

    public PersonsDTO createValidPersonsDTO() {
        final PersonsDTO personsDTO = new PersonsDTO();
        personsDTO.setFirstName("default first name");
        personsDTO.setLastName("default last name");
        personsDTO.setAddress("default address");
        personsDTO.setCity("default city");
        personsDTO.setZip(00000);
        personsDTO.setEmail("default@email.com");
        return personsDTO;
    }

    public PersonsDTO createInvalidPersonsDTO() {
        final PersonsDTO personsDTO = new PersonsDTO();
        personsDTO.setFirstName("default first name");
        personsDTO.setLastName("default last name");
        personsDTO.setAddress("default address");
        personsDTO.setCity("default city");
        personsDTO.setZip(00000);
        personsDTO.setEmail("invalid email");
        return personsDTO;
    }

    public PersonsDTO createCustomPersonsDTO(String firstName, String lastName, String address, String city, Integer zip, String phone, String email) {
        final PersonsDTO personsDTO = new PersonsDTO();
        personsDTO.setFirstName(firstName);
        personsDTO.setLastName(lastName);
        personsDTO.setAddress(address);
        personsDTO.setCity(city);
        personsDTO.setZip(zip);
        personsDTO.setEmail(email);
        return personsDTO;
    }
}
