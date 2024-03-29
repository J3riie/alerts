package com.safetynet.alerts.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.service.MedicalRecordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/medicalRecord", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MedicalRecordController {
    private static final Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    ResponseEntity<Void> addMedicalRecord(@RequestBody @Valid MedicalRecordsDTO newMedicalRecord) {
        logger.info("Adding a new medical record in the data");
        medicalRecordService.addMedicalRecord(newMedicalRecord);
        logger.info("Medical record added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    ResponseEntity<Void> modifyMedicalRecord(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName,
            @RequestParam(required = false, name = "birthdate") Date birthdate, @RequestParam(required = false, name = "medications") List<String> medications,
            @RequestParam(required = false, name = "allergies") List<String> allergies) {
        logger.info("Modifying {} {}'s medical record", firstName, lastName);
        try {
            medicalRecordService.modifyMedicalRecord(firstName, lastName, birthdate, medications, allergies);
            logger.info("Medical record modified successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final Exception e) {
            logger.error("Medical record requested not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping
    ResponseEntity<Void> deleteMedicalRecord(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        logger.info("Deleting {} {}'s medical record from the data", firstName, lastName);
        final Optional<MedicalRecordsDTO> optionalMedicalRecord = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        if (optionalMedicalRecord.isPresent()) {
            logger.info("Medical record deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        logger.error("Medical record requested not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
