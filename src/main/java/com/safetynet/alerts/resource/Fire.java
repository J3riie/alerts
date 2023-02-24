package com.safetynet.alerts.resource;

import static java.time.ZoneId.systemDefault;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.FireDTO;
import com.safetynet.alerts.dto.response.InhabitantResponse;
import com.safetynet.alerts.dto.response.MedicalHistory;

@RestController
@RequestMapping(value = "/fire", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class Fire {
    private static final Logger logger = LogManager.getLogger(Fire.class);

    @GetMapping
    ResponseEntity<FireDTO> index(@RequestParam(name = "address") String address) {
        logger.info("List of the persons living at {}, their medical history and the station covering them :", address);
        final int stationNumber = getFirestationNumberFromAddress(address);
        final ArrayList<InhabitantResponse> inhabitants = getInfoFromAddress(address);
        setPersonsMedicalHistory(inhabitants);

        final FireDTO response = new FireDTO();
        response.setInhabitants(inhabitants);
        response.setCoveringStation(stationNumber);

        for (final InhabitantResponse i : inhabitants) {
            logger.info("{} {} {} {} {}", i.getFirstName(), i.getLastName(), i.getPhone(), i.getAge(), i.getMedicalHistory());
        }
        if (stationNumber == -1) {
            logger.error("The referenced address isn't covered by any station");
        } else {
            logger.info("Firestation serving {} is the station number {}.", address, stationNumber);
        }
        return ResponseEntity.status(OK).body(response);
    }

    private ArrayList<InhabitantResponse> getInfoFromAddress(String address) {
        final ArrayList<InhabitantResponse> inhabitants = new ArrayList<>();
        for (final PersonsDTO p : App.getPersons()) {
            if (p.getAddress().equals(address)) {
                final InhabitantResponse inhabitant = new InhabitantResponse();
                inhabitant.setFirstName(p.getFirstName());
                inhabitant.setLastName(p.getLastName());
                inhabitant.setPhone(p.getPhone());
                inhabitants.add(inhabitant);
            }
        }
        for (final InhabitantResponse i : inhabitants) {
            for (final MedicalRecordsDTO m : App.getMedicalRecords()) {
                if (m.getFirstName().equals(i.getFirstName()) && m.getLastName().equals(i.getLastName())) {
                    final LocalDate birthDate = m.getBirthdate().toInstant().atZone(systemDefault()).toLocalDate();
                    final LocalDate now = LocalDate.now();
                    final long yearsBetween = ChronoUnit.YEARS.between(birthDate, now);
                    final int age = (int) yearsBetween;
                    i.setAge(age);
                }
            }
        }
        return inhabitants;
    }

    private void setPersonsMedicalHistory(List<InhabitantResponse> inhabitants) {
        for (final InhabitantResponse i : inhabitants) {
            for (final MedicalRecordsDTO m : App.getMedicalRecords()) {
                if (m.getFirstName().equals(i.getFirstName()) && m.getLastName().equals(i.getLastName())) {
                    final MedicalHistory medicationResponse = new MedicalHistory();
                    medicationResponse.setMedications(m.getMedications());
                    medicationResponse.setAllergies(m.getAllergies());
                    i.setMedicalHistory(medicationResponse);
                }
            }
        }
    }

    private int getFirestationNumberFromAddress(String address) {
        for (final FireStationsDTO f : App.getFirestations()) {
            if (f.getAddress().equals(address)) {
                return f.getStation();
            }
        }
        return -1;
    }

}
