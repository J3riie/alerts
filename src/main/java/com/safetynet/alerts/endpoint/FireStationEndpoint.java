package com.safetynet.alerts.endpoint;

import java.util.ArrayList;

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
import com.safetynet.alerts.dto.node.FireStationsDTO;

@RestController
@RequestMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FireStationEndpoint {
    private static final Logger logger = LogManager.getLogger(FireStationEndpoint.class);

    @PostMapping(value = "/post")
    void post(@RequestParam(name = "address") String address, @RequestParam(name = "station") Integer station) {
        logger.info("Adding a new station - address mapping in the data");
        final ArrayList<FireStationsDTO> firestations = new ArrayList<>(App.getFirestations());
        final FireStationsDTO firestationToPost = new FireStationsDTO();
        firestationToPost.setAddress(address);
        firestationToPost.setStation(station);
        firestations.add(firestationToPost);
        App.getDataJson().setFirestations(firestations);
    }

    @PutMapping(value = "/put")
    void put(@RequestParam(name = "address") String address, @RequestParam(name = "station") Integer station) {
        logger.info("Modifying the station for address {}", address);
        final ArrayList<FireStationsDTO> firestations = new ArrayList<>(App.getFirestations());
        for (final FireStationsDTO f : firestations) {
            if (f.getAddress().equals(address)) {
                f.setStation(station);
            }
        }
        App.getDataJson().setFirestations(firestations);
    }

    @DeleteMapping(value = "/delete")
    void delete(@RequestParam(required = false, name = "address") String address, @RequestParam(required = false, name = "station") Integer station) {
        if (address == null && station == null) {
            logger.error("Use one and only one parameter to get the corresponding mapping to be deleted.");
        } else if (address == null) {
            logger.info("Deleting the mapping for station {}", station);
            final ArrayList<FireStationsDTO> firestations = new ArrayList<>(App.getFirestations());
            for (final FireStationsDTO f : firestations) {
                if (f.getStation() == station) {
                    firestations.remove(f);
                }
            }
            App.getDataJson().setFirestations(firestations);
        } else if (station == null) {
            logger.info("Deleting the mapping for address {}", address);
            final ArrayList<FireStationsDTO> firestations = new ArrayList<>(App.getFirestations());
            for (final FireStationsDTO f : firestations) {
                if (f.getAddress().equals(address)) {
                    firestations.remove(f);
                    break;
                }
            }
            App.getDataJson().setFirestations(firestations);
        } else {
            logger.error("Use one and only one parameter to get the corresponding mapping to be deleted.");
        }
    }
}
