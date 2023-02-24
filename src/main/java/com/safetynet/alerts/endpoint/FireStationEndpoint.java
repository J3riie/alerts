package com.safetynet.alerts.endpoint;

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

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.service.FireStationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FireStationEndpoint {
    private static final Logger logger = LogManager.getLogger(FireStationEndpoint.class);

    @Autowired
    private FireStationService fireStationService;

    @PostMapping
    APIResponse<Void> addFireStation(@RequestBody @Valid FireStationsDTO newStation) {
        logger.info("Adding a new station - address mapping in the data");
        fireStationService.addFireStation(newStation);
        return new APIResponse<>(HttpStatus.CREATED.value(),
                String.format("Station %d mapped successfully with address %s", newStation.getStation(), newStation.getAddress()));
    }

    @PutMapping
    APIResponse<Void> modifyFireStation(@RequestParam(name = "address") String address, @RequestParam(name = "station") Integer station) {
        logger.info("Modifying the station for address {}", address);
        final FireStationsDTO firestation = fireStationService.modifyFireStation(address, station);
        if (firestation == null) {
            return new APIResponse<>(HttpStatus.NOT_FOUND.value(), String.format("Unable to modify station for address %s : not found", address));
        }
        return new APIResponse<>(HttpStatus.NO_CONTENT.value(), String.format("Station %d successfully mapped to %s", station, address));
    }

    @DeleteMapping
    APIResponse<Void> deleteFireStation(@RequestParam(required = false, name = "address") String address,
            @RequestParam(required = false, name = "station") Integer station) {
        if (address == null) {
            logger.info("Deleting the mapping(s) for station {}", station);
            final boolean isPresent = fireStationService.deleteFireStation(station);
            if (isPresent) {
                return new APIResponse<>(HttpStatus.OK.value(), String.format("Mapping successfully deleted for station number %d", station));
            }
            return new APIResponse<>(HttpStatus.NOT_FOUND.value(), String.format("Unable to delete mapping : no mapping found for station number %d", station));
        } else if (station == null) {
            logger.info("Deleting the mapping for address {}", address);
            final Optional<FireStationsDTO> optionalFirestation = fireStationService.deleteFireStation(address);
            if (optionalFirestation.isPresent()) {
                return new APIResponse<>(HttpStatus.OK.value(), String.format("Mapping successfully deleted for address %s", address));
            }
            return new APIResponse<>(HttpStatus.NOT_FOUND.value(), String.format("Unable to delete mapping : no mapping found for address %s", address));
        } else {
            return new APIResponse<>(HttpStatus.BAD_REQUEST.value(), "Use one and only one parameter to get the corresponding mapping to be deleted.");
        }
    }
}
