package com.safetynet.alerts.endpoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;

@RestController
@RequestMapping(value = "/medicalRecord", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalRecordEndpoint {
    private static final Logger logger = LogManager.getLogger(MedicalRecordEndpoint.class);

    @PostMapping(value = "/post")
    void post(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "birthdate") Date birthdate, @RequestParam(required = false, name = "medications") List<String> medications,
            @RequestParam(required = false, name = "allergies") List<String> allergies) {
        logger.info("Adding a new medical record in the data");
        final ArrayList<MedicalRecordsDTO> medicalRecords = new ArrayList<>(App.getMedicalrecords());
        final MedicalRecordsDTO medicalRecordToPost = new MedicalRecordsDTO();
        medicalRecordToPost.setFirstName(firstName);
        medicalRecordToPost.setLastName(lastName);
        medicalRecordToPost.setBirthdate(birthdate);
        medicalRecordToPost.setMedications(medications);
        medicalRecordToPost.setAllergies(allergies);
        medicalRecords.add(medicalRecordToPost);
        App.getDataJson().setMedicalrecords(medicalRecords);
    }

    @PutMapping(value = "/put")
    void put(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName,
            @RequestParam(required = false, name = "birthdate") Date birthdate, @RequestParam(required = false, name = "medications") List<String> medications,
            @RequestParam(required = false, name = "allergies") List<String> allergies) {
        logger.info("Modifying {} {}'s medical record", firstName, lastName);
        final ArrayList<MedicalRecordsDTO> medicalRecords = new ArrayList<>(App.getMedicalrecords());
        for (final MedicalRecordsDTO m : medicalRecords) {
            if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)) {
                if (birthdate != null) {
                    m.setBirthdate(birthdate);
                }
                if (medications != null) {
                    m.setMedications(medications);
                }
                if (allergies != null) {
                    m.setAllergies(allergies);
                }
            }
        }
        App.getDataJson().setMedicalrecords(medicalRecords);
    }

    @DeleteMapping(value = "/delete")
    void delete(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        logger.info("Deleting {} {}'s medical record from the data", firstName, lastName);
        final ArrayList<MedicalRecordsDTO> medicalRecords = new ArrayList<>(App.getMedicalrecords());
        for (final MedicalRecordsDTO m : medicalRecords) {
            if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)) {
                medicalRecords.remove(m);
                break;
            }
        }
        App.getDataJson().setMedicalrecords(medicalRecords);
    }
}
