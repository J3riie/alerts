package com.safetynet.alerts.repo;

import java.util.List;

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;

public interface DataRepository {

    public List<FireStationsDTO> getAllFirestations();

    public List<PersonsDTO> getAllPersons();

    public List<MedicalRecordsDTO> getAllMedicalRecords();

    public FireStationsDTO findFireStationFromAddress(String address);

    public MedicalRecordsDTO findMedicalRecordFromName(String firstName, String lastName);

    public PersonsDTO findPersonWithName(String firstName, String lastName);
}
