package com.safetynet.alerts.resource;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.resource.CommunityEmailDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.service.resource.CommunityEmailService;

@RestController
@RequestMapping(value = "/communityEmail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CommunityEmail {

    private static final Logger logger = LogManager.getLogger(CommunityEmail.class);

    @Autowired
    CommunityEmailService communityEmailService;

    @GetMapping
    APIResponse<CommunityEmailDTO> index(@RequestParam(name = "city") String city) {
        logger.info("List of the email addresses of the persons living in {} :", city);
        final ArrayList<String> emailAddresses = communityEmailService.getEmailAddressesFromCity(city);

        final CommunityEmailDTO response = new CommunityEmailDTO();
        response.setEmailAddresses(emailAddresses);

        for (final String s : emailAddresses) {
            logger.info("{}", s);
        }
        return new APIResponse<>(HttpStatus.OK.value(), String.format("List of the email addresses of the persons living in %s", city), response);
    }
}
