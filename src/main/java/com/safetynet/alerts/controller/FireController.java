package com.safetynet.alerts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.resource.FireDTO;
import com.safetynet.alerts.dto.response.InhabitantResponse;
import com.safetynet.alerts.service.FireService;

@RestController
@RequestMapping(value = "/fire", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FireController {

    private static final Logger logger = LoggerFactory.getLogger(FireController.class);

    private final FireService fireService;

    public FireController(FireService fireService) {
        this.fireService = fireService;
    }

    @GetMapping
    ResponseEntity<FireDTO> getPersonsInfoAtAddress(@RequestParam(name = "address") String address) {
        logger.info("List of the persons living at {}, their medical history and the station covering them :", address);

        final FireDTO response = fireService.getPersonsInfoAtAddress(address);

        for (final InhabitantResponse i : response.getInhabitants()) {
            logger.info("{} {} {} {} {}", i.getFirstName(), i.getLastName(), i.getPhone(), i.getAge(), i.getMedicalHistory());
        }
        if (response.getCoveringStation() == -1) {
            logger.error("The referenced address isn't covered by any station");
        } else {
            logger.info("Firestation serving {} is the station number {}.", address, response.getCoveringStation());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
