package com.safetynet.alerts.service.endpoint;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;

@Service
public class MedicalRecordEndpointService {

    public void addMedicalRecord(MedicalRecordsDTO medicalRecord) {
        App.getMedicalRecords().add(medicalRecord);
    }

    public MedicalRecordsDTO modifyMedicalRecord(String firstName, String lastName, Date birthdate, List<String> medications, List<String> allergies) {
        return App.getMedicalRecords().stream().filter(m -> m.medicalRecordExists(firstName, lastName)).findFirst().map(m -> {
            if (birthdate != null) {
                m.setBirthdate(birthdate);
            }
            if (medications != null) {
                m.setMedications(medications);
            }
            if (allergies != null) {
                m.setAllergies(allergies);
            }
            return m;
        }).orElse(null);
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
