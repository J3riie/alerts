package com.safetynet.alerts.endpoint;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.resource.PhoneAlertDTO;
import com.safetynet.alerts.service.PhoneAlertService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/phoneAlert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class PhoneAlertEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(PhoneAlertEndpoint.class);

    @Autowired
    PhoneAlertService phoneAlertService;

    @GetMapping
    ResponseEntity<PhoneAlertDTO> getPhoneNumbersFromPersonsCoveredByStation(
            @RequestParam(name = "firestation") @Min(value = 1, message = "The value needs to be strictly positive") int stationNumber) {
        logger.info("List of phone numbers of the persons covered by the firestation number {} :", stationNumber);
        final List<String> addresses = phoneAlertService.getAddressesFromStation(stationNumber);
        final List<String> phones = phoneAlertService.getPhonesFromAddresses(addresses);

        final PhoneAlertDTO response = new PhoneAlertDTO();
        response.setPhones(phones);

        for (final String p : phones) {
            logger.info("{}", p);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}