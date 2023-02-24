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
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.PersonInfoDTO;
import com.safetynet.alerts.dto.response.MedicalHistory;
import com.safetynet.alerts.dto.response.PersonResponse;

@RestController
@RequestMapping(value = "/personInfo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonInfo {
    private static final Logger logger = LogManager.getLogger(PersonInfo.class);

    @GetMapping
    ResponseEntity<PersonInfoDTO> index(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        logger.info("List of the persons info whose names are {} {} :", firstName, lastName);
        final ArrayList<PersonResponse> personsInfo = getInfoFromNames(firstName, lastName);
        setPersonsMedicalHistory(personsInfo);

        final PersonInfoDTO response = new PersonInfoDTO();
        response.setPersons(personsInfo);

        for (final PersonResponse p : personsInfo) {
            logger.info("{} {} {} {} {} {}", p.getFirstName(), p.getLastName(), p.getAddress(), p.getAge(), p.getEmailAddress(), p.getMedicalHistory());
        }
        return ResponseEntity.status(OK).body(response);
    }

    private ArrayList<PersonResponse> getInfoFromNames(String firstName, String lastName) {
        final ArrayList<PersonResponse> persons = new ArrayList<>();
        for (final PersonsDTO p : App.getPersons()) {
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                final PersonResponse person = new PersonResponse();
                person.setFirstName(p.getFirstName());
                person.setLastName(p.getLastName());
                person.setAddress(p.getAddress());
                person.setEmailAddress(p.getEmail());
                for (final MedicalRecordsDTO m : App.getMedicalRecords()) {
                    if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)) {
                        final LocalDate birthDate = m.getBirthdate().toInstant().atZone(systemDefault()).toLocalDate();
                        final LocalDate now = LocalDate.now();
                        final long yearsBetween = ChronoUnit.YEARS.between(birthDate, now);
                        final int age = (int) yearsBetween;
                        person.setAge(age);
                    }
                }
                persons.add(person);
            }
        }
        return persons;
    }

    private void setPersonsMedicalHistory(List<PersonResponse> persons) {
        for (final PersonResponse p : persons) {
            for (final MedicalRecordsDTO m : App.getMedicalRecords()) {
                if (m.getFirstName().equals(p.getFirstName()) && m.getLastName().equals(p.getLastName())) {
                    final MedicalHistory medicationResponse = new MedicalHistory();
                    medicationResponse.setMedications(m.getMedications());
                    medicationResponse.setAllergies(m.getAllergies());
                    p.setMedicalHistory(medicationResponse);
                }
            }
        }
    }

}
