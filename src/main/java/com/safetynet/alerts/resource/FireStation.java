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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.FireStationDTO;
import com.safetynet.alerts.dto.response.CoveredPersonResponse;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class FireStation {
    private static final Logger logger = LogManager.getLogger(FireStation.class);

    @GetMapping
    ResponseEntity<FireStationDTO> index(
            @RequestParam(name = "stationNumber") @Min(value = 1, message = "The value needs to be strictly positive") int stationNumber) {
        logger.info("List of the persons covered by the firestation number {} :", stationNumber);
        final ArrayList<String> addresses = getAddressesFromStation(stationNumber);
        final ArrayList<CoveredPersonResponse> personsCovered = getInfoFromAddresses(addresses);

        final int[] numberOfAdultsAndChildren = getNumberOfAdultsAndChildren(personsCovered);
        final int numberOfAdults = numberOfAdultsAndChildren[0];
        final int numberOfChildren = numberOfAdultsAndChildren[1];

        final FireStationDTO response = new FireStationDTO();
        response.setCoveredPersons(personsCovered);
        response.setNumberOfAdults(numberOfAdults);
        response.setNumberOfChildren(numberOfChildren);

        for (final CoveredPersonResponse p : personsCovered) {
            logger.info("{} {} {} {}", p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone());
        }
        logger.info("Number of adults : {}", numberOfAdults);
        logger.info("Number of children : {}", numberOfChildren);
        return ResponseEntity.status(OK).body(response);
    }

    private ArrayList<String> getAddressesFromStation(int stationNumber) {
        final ArrayList<String> addresses = new ArrayList<>();
        for (final FireStationsDTO f : App.getFirestations()) {
            if (f.getStation() == stationNumber) {
                addresses.add(f.getAddress());
            }
        }
        return addresses;
    }

    private ArrayList<CoveredPersonResponse> getInfoFromAddresses(List<String> addresses) {
        final ArrayList<CoveredPersonResponse> personsCovered = new ArrayList<>();
        for (final PersonsDTO p : App.getPersons()) {
            for (final String address : addresses) {
                if (p.getAddress().equals(address)) {
                    final CoveredPersonResponse personCovered = new CoveredPersonResponse();
                    personCovered.setFirstName(p.getFirstName());
                    personCovered.setLastName(p.getLastName());
                    personCovered.setAddress(p.getAddress());
                    personCovered.setPhone(p.getPhone());
                    personsCovered.add(personCovered);
                }
            }
        }
        return personsCovered;
    }

    private int[] getNumberOfAdultsAndChildren(List<CoveredPersonResponse> personsCovered) {
        final int[] numberOfAdultsAndChildren = { 0, 0 };
        for (final CoveredPersonResponse p : personsCovered) {
            for (final MedicalRecordsDTO m : App.getMedicalrecords()) {
                if (m.getFirstName().equals(p.getFirstName()) && m.getLastName().equals(p.getLastName())) {
                    final LocalDate birthDate = m.getBirthdate().toInstant().atZone(systemDefault()).toLocalDate();
                    final LocalDate now = LocalDate.now();
                    final long yearsBetween = ChronoUnit.YEARS.between(birthDate, now);
                    final int age = (int) yearsBetween;
                    if (age >= 18) {
                        numberOfAdultsAndChildren[0]++;
                    } else {
                        numberOfAdultsAndChildren[1]++;
                    }
                }
            }
        }
        return numberOfAdultsAndChildren;
    }
}
