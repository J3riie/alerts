package com.safetynet.alerts.util;

import java.util.List;

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.repo.DataRepository;

public class JsonDataRepositoryTestUtil implements DataRepository {

    @Override
    public List<FireStationsDTO> getAllFirestations() {
        return TestRepository.getFirestations();
    }

    @Override
    public List<PersonsDTO> getAllPersons() {
        return TestRepository.getPersons();
    }

    @Override
    public List<MedicalRecordsDTO> getAllMedicalRecords() {
        return TestRepository.getMedicalRecords();
    }

    @Override
    public FireStationsDTO findFireStationFromAddress(String address) {
        return TestRepository.getFirestations().stream().filter(f -> f.addressMappingExists(address)).findFirst()
                .orElseThrow(() -> new RuntimeException("No station corresponding to address " + address));
    }

    @Override
    public MedicalRecordsDTO findMedicalRecordFromName(String firstName, String lastName) {
        return TestRepository.getMedicalRecords().stream().filter(m -> m.medicalRecordExists(firstName, lastName)).findFirst()
                .orElseThrow(() -> new RuntimeException("No medical record corresponding to names " + firstName + " " + lastName));
    }

    @Override
    public PersonsDTO findPersonWithName(String firstName, String lastName) {
        return TestRepository.getPersons().stream().filter(p -> p.personExists(firstName, lastName)).findFirst()
                .orElseThrow(() -> new RuntimeException("No person with names " + firstName + " " + lastName));
    }

}
