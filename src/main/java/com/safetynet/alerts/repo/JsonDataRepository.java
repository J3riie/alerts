package com.safetynet.alerts.repo;

import java.util.List;

import org.springframework.stereotype.Component;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;

@Component
public class JsonDataRepository implements DataRepository {

    @Override
    public List<FireStationsDTO> getAllFirestations() {
        return App.getFirestations();
    }

    @Override
    public List<PersonsDTO> getAllPersons() {
        return App.getPersons();
    }

    @Override
    public List<MedicalRecordsDTO> getAllMedicalRecords() {
        return App.getMedicalRecords();
    }

    @Override
    public FireStationsDTO findFireStationFromAddress(String address) {
        return App.getFirestations().stream().filter(f -> f.addressMappingExists(address)).findFirst()
                .orElseThrow(() -> new RuntimeException("No station corresponding to address " + address));
    }

    @Override
    public MedicalRecordsDTO findMedicalRecordFromName(String firstName, String lastName) {
        return App.getMedicalRecords().stream().filter(m -> m.medicalRecordExists(firstName, lastName)).findFirst()
                .orElseThrow(() -> new RuntimeException("No medical record corresponding to names " + firstName + " " + lastName));
    }

    @Override
    public PersonsDTO findPersonWithName(String firstName, String lastName) {
        return App.getPersons().stream().filter(p -> p.personExists(firstName, lastName)).findFirst()
                .orElseThrow(() -> new RuntimeException("No person with names " + firstName + " " + lastName));
    }

}
