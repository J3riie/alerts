package com.safetynet.alerts.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.resource.FireStationDTO;
import com.safetynet.alerts.dto.response.CoveredPersonResponse;
import com.safetynet.alerts.service.FireStationService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class FireStationController {
    private static final Logger logger = LoggerFactory.getLogger(FireStationController.class);

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping
    ResponseEntity<FireStationDTO> getPersonsCoveredByStation(
            @RequestParam(name = "stationNumber") @Min(value = 1, message = "The value needs to be strictly positive") int stationNumber) {
        logger.info("List of the persons covered by the firestation number {} :", stationNumber);
        final FireStationDTO response = fireStationService.getPersonsCoveredByStation(stationNumber);

        for (final CoveredPersonResponse p : response.getCoveredPersons()) {
            logger.info("{} {} {} {}", p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone());
        }
        logger.info("Number of adults : {}", response.getNumberOfAdults());
        logger.info("Number of children : {}", response.getNumberOfChildren());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    ResponseEntity<Void> addFireStation(@RequestBody @Valid FireStationsDTO newStation) {
        logger.info("Adding a new station - address mapping in the data");
        fireStationService.addFireStation(newStation);
        logger.info("Firestation added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    ResponseEntity<Void> modifyFireStation(@RequestParam(name = "address") String address, @RequestParam(name = "station") Integer station) {
        logger.info("Modifying the station for address {}", address);
        try {
            fireStationService.modifyFireStation(address, station);
            logger.info("Firestation modified successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final Exception e) {
            logger.error("Firestation requested could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping
    ResponseEntity<Void> deleteFireStation(@RequestParam(required = false, name = "address") String address,
            @RequestParam(required = false, name = "station") Integer station) {
        if (address == null) {
            logger.info("Deleting the mapping(s) for station {}", station);
            final boolean isPresent = fireStationService.deleteFireStation(station);
            if (isPresent) {
                logger.info("Firestation deleted successfully");
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            logger.error("Firestation requested could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (station == null) {
            logger.info("Deleting the mapping for address {}", address);
            final Optional<FireStationsDTO> optionalFirestation = fireStationService.deleteFireStation(address);
            if (optionalFirestation.isPresent()) {
                logger.info("Firestation deleted successfully");
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            logger.error("Address requested could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            logger.error("Wrong request parameters");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
