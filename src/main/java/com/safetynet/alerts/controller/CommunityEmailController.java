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

import com.safetynet.alerts.dto.resource.CommunityEmailDTO;
import com.safetynet.alerts.service.CommunityEmailService;

@RestController
@RequestMapping(value = "/communityEmail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CommunityEmailController {

    private static final Logger logger = LoggerFactory.getLogger(CommunityEmailController.class);

    private final CommunityEmailService communityEmailService;

    public CommunityEmailController(CommunityEmailService communityEmailService) {
        this.communityEmailService = communityEmailService;
    }

    @GetMapping
    ResponseEntity<CommunityEmailDTO> getEmailsFromCity(@RequestParam(name = "city") String city) {
        logger.info("List of the email addresses of the persons living in {} :", city);
        final CommunityEmailDTO response = communityEmailService.getEmailAddressesFromCity(city);

        for (final String s : response.getEmailAddresses()) {
            logger.info("{}", s);
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
