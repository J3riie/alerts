package com.safetynet.alerts.resource;

import java.util.ArrayList;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.resource.FireStationDTO;
import com.safetynet.alerts.dto.response.CoveredPersonResponse;
import com.safetynet.alerts.service.resource.FireStationService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class FireStation {

    private static final Logger logger = LoggerFactory.getLogger(FireStation.class);

    @Autowired
    FireStationService fireStationService;

    @GetMapping
    ResponseEntity<FireStationDTO> index(
            @RequestParam(name = "stationNumber") @Min(value = 1, message = "The value needs to be strictly positive") int stationNumber) {
        logger.info("List of the persons covered by the firestation number {} :", stationNumber);
        final ArrayList<String> addresses = fireStationService.getAddressesFromStation(stationNumber);
        final ArrayList<CoveredPersonResponse> personsCovered = fireStationService.getInfoFromAddresses(addresses);

        final int[] numberOfAdultsAndChildren = fireStationService.getNumberOfAdultsAndChildren(personsCovered);
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
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
