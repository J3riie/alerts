package com.safetynet.alerts.endpoint;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.service.endpoint.MedicalRecordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/medicalRecord", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalRecordEndpoint {
    private static final Logger logger = LogManager.getLogger(MedicalRecordEndpoint.class);

    @Autowired
    private MedicalRecordService medicalRecordService;

    @PostMapping
    APIResponse<Void> addMedicalRecord(@RequestBody @Valid MedicalRecordsDTO newMedicalRecord) {
        logger.info("Adding a new medical record in the data");
        medicalRecordService.addMedicalRecord(newMedicalRecord);
        return new APIResponse<>(HttpStatus.CREATED.value(),
                String.format("%s %s's medical record added successfully", newMedicalRecord.getFirstName(), newMedicalRecord.getLastName()));
    }

    @PutMapping
    APIResponse<Void> modifyMedicalRecord(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName,
            @RequestParam(required = false, name = "birthdate") Date birthdate, @RequestParam(required = false, name = "medications") List<String> medications,
            @RequestParam(required = false, name = "allergies") List<String> allergies) {
        logger.info("Modifying {} {}'s medical record", firstName, lastName);
        final MedicalRecordsDTO medicalRecord = medicalRecordService.modifyMedicalRecord(firstName, lastName, birthdate, medications, allergies);
        if (medicalRecord == null) {
            return new APIResponse<>(HttpStatus.NOT_FOUND.value(), String.format("Unable to modify %s %s's medical record : not found", firstName, lastName));
        }
        return new APIResponse<>(HttpStatus.NO_CONTENT.value(), String.format("%s %s's medical record modified successfully", firstName, lastName));
    }

    @DeleteMapping
    APIResponse<Void> deleteMedicalRecord(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        logger.info("Deleting {} {}'s medical record from the data", firstName, lastName);
        final Optional<MedicalRecordsDTO> optionalMedicalRecord = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (optionalMedicalRecord.isPresent()) {
            return new APIResponse<>(HttpStatus.OK.value(), String.format("%s %s's medical record deleted successfully", firstName, lastName));
        }
        return new APIResponse<>(HttpStatus.NOT_FOUND.value(), String.format("Unable to delete %s %s's medical record : not found", firstName, lastName));
    }
}
