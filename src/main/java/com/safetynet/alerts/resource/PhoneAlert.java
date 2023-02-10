package com.safetynet.alerts.resource;

import static org.springframework.http.HttpStatus.OK;

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
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.PhoneAlertDTO;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/phoneAlert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class PhoneAlert {
    private static final Logger logger = LogManager.getLogger(PhoneAlert.class);

    @GetMapping
    ResponseEntity<PhoneAlertDTO> index(
            @RequestParam(name = "firestation") @Min(value = 1, message = "The value needs to be strictly positive") int stationNumber) {
        logger.info("List of phone number of the persons covered by the firestation number {} :", stationNumber);
        final ArrayList<String> addresses = getAddressesFromStation(stationNumber);
        final ArrayList<String> phones = getPhonesFromAddresses(addresses);

        final PhoneAlertDTO response = new PhoneAlertDTO();
        response.setPhones(phones);

        for (final String p : phones) {
            logger.info("{}", p);
        }
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

    private ArrayList<String> getPhonesFromAddresses(List<String> addresses) {
        final ArrayList<String> phones = new ArrayList<>();
        for (final String address : addresses) {
            for (final PersonsDTO p : App.getPersons()) {
                if (p.getAddress().equals(address)) {
                    phones.add(p.getPhone());
                }
            }
        }
        return phones;
    }
}