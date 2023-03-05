package com.safetynet.alerts.resource;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.resource.PhoneAlertDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.service.resource.PhoneAlertService;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/phoneAlert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class PhoneAlert {

    private static final Logger logger = LogManager.getLogger(PhoneAlert.class);

    @Autowired
    PhoneAlertService phoneAlertService;

    @GetMapping
    APIResponse<PhoneAlertDTO> index(
            @RequestParam(name = "firestation") @Min(value = 1, message = "The value needs to be strictly positive") int stationNumber) {
        logger.info("List of phone numbers of the persons covered by the firestation number {} :", stationNumber);
        final ArrayList<String> addresses = phoneAlertService.getAddressesFromStation(stationNumber);
        final ArrayList<String> phones = phoneAlertService.getPhonesFromAddresses(addresses);

        final PhoneAlertDTO response = new PhoneAlertDTO();
        response.setPhones(phones);

        for (final String p : phones) {
            logger.info("{}", p);
        }
        return new APIResponse<>(HttpStatus.OK.value(),
                String.format("List of phone numbers of the persons covered by the firestation number %d", stationNumber), response);
    }
}