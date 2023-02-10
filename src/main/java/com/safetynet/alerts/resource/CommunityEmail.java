package com.safetynet.alerts.resource;

import static org.springframework.http.HttpStatus.OK;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.CommunityEmailDTO;

@RestController
@RequestMapping(value = "/communityEmail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class CommunityEmail {
    private static final Logger logger = LogManager.getLogger(CommunityEmail.class);

    @GetMapping
    ResponseEntity<CommunityEmailDTO> index(@RequestParam(name = "city") String city) {
        logger.info("List of the email addresses of the persons living in {} :", city);
        final ArrayList<String> emailAddresses = getEmailAddressesFromCity(city);

        final CommunityEmailDTO response = new CommunityEmailDTO();
        response.setEmailAddresses(emailAddresses);

        for (final String s : emailAddresses) {
            logger.info("{}", s);
        }
        return ResponseEntity.status(OK).body(response);
    }

    private ArrayList<String> getEmailAddressesFromCity(String city) {
        final ArrayList<String> emailAddresses = new ArrayList<>();
        for (final PersonsDTO p : App.getPersons()) {
            if (p.getCity().equals(city)) {
                emailAddresses.add(p.getEmail());
            }
        }
        return emailAddresses;
    }

}
