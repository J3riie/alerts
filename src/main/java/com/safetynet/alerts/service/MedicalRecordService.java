package com.safetynet.alerts.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.repo.DataRepository;

@Service
public class MedicalRecordService {

    private final DataRepository repo;

    public MedicalRecordService(DataRepository repo) {
        this.repo = repo;
    }

    public void addMedicalRecord(MedicalRecordsDTO medicalRecord) {
        repo.getAllMedicalRecords().add(medicalRecord);
    }

    public MedicalRecordsDTO modifyMedicalRecord(String firstName, String lastName, Date birthdate, List<String> medications, List<String> allergies) {
        final MedicalRecordsDTO medicalRecords = repo.findMedicalRecordFromName(firstName, lastName);
        if (birthdate != null) {
            medicalRecords.setBirthdate(birthdate);
        }
        if (medications != null) {
            medicalRecords.setMedications(medications);
        }
        if (allergies != null) {
            medicalRecords.setAllergies(allergies);
        }
        return medicalRecords;
    }

    public Optional<MedicalRecordsDTO> deleteMedicalRecord(String firstName, String lastName) {
        final Optional<MedicalRecordsDTO> optionalMedicalRecord = App.getMedicalRecords().stream().filter(m -> m.medicalRecordExists(firstName, lastName))
                .findFirst();
        if (optionalMedicalRecord.isPresent()) {
            App.getMedicalRecords().remove(optionalMedicalRecord.get());
        }
        return optionalMedicalRecord;
    }
}
