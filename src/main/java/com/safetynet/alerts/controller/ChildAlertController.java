package com.safetynet.alerts.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.resource.ChildAlertDTO;
import com.safetynet.alerts.dto.response.ChildResponse;
import com.safetynet.alerts.dto.response.FamilyResponse;
import com.safetynet.alerts.service.ChildAlertService;

@RestController
@RequestMapping(value = "/childAlert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChildAlertController {

    private static final Logger logger = LoggerFactory.getLogger(ChildAlertController.class);

    @Autowired
    ChildAlertService childAlertService;

    @GetMapping
    ResponseEntity<ChildAlertDTO> getChildrenListAtAddress(@RequestParam(name = "address") String address) {
        logger.info("List of the children living at {} and their family :", address);
        final ChildAlertDTO response = childAlertService.getChildrenAtAddress(address);

        for (final ChildResponse c : response.getChildren()) {
            logger.info("{} {} {}", c.getFirstName(), c.getLastName(), c.getAge());
            logger.info("Family :");
            for (final FamilyResponse f : c.getFamily()) {
                logger.info("{} {}", f.getFirstName(), f.getLastName());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
