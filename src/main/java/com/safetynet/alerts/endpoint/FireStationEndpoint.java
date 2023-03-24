package com.safetynet.alerts.endpoint;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.service.endpoint.FireStationEndpointService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FireStationEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(FireStationEndpoint.class);

    @Autowired
    private FireStationEndpointService fireStationService;

    @PostMapping
    ResponseEntity<Void> addFireStation(@RequestBody @Valid FireStationsDTO newStation) {
        logger.info("Adding a new station - address mapping in the data");
        fireStationService.addFireStation(newStation);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    ResponseEntity<Void> modifyFireStation(@RequestParam(name = "address") String address, @RequestParam(name = "station") Integer station) {
        logger.info("Modifying the station for address {}", address);
        final FireStationsDTO firestation = fireStationService.modifyFireStation(address, station);
        if (firestation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping
    ResponseEntity<Void> deleteFireStation(@RequestParam(required = false, name = "address") String address,
            @RequestParam(required = false, name = "station") Integer station) {
        if (address == null) {
            logger.info("Deleting the mapping(s) for station {}", station);
            final boolean isPresent = fireStationService.deleteFireStation(station);
            if (isPresent) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (station == null) {
            logger.info("Deleting the mapping for address {}", address);
            final Optional<FireStationsDTO> optionalFirestation = fireStationService.deleteFireStation(address);
            if (optionalFirestation.isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
