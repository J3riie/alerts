package com.safetynet.alerts.endpoint;

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

import com.safetynet.alerts.dto.resource.PersonInfoDTO;
import com.safetynet.alerts.dto.response.PersonResponse;
import com.safetynet.alerts.service.PersonInfoService;

@RestController
@RequestMapping(value = "/personInfo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonInfoEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(PersonInfoEndpoint.class);

    @Autowired
    PersonInfoService personInfoService;

    @GetMapping
    ResponseEntity<PersonInfoDTO> getPersonsWithNames(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        logger.info("List of the persons info whose names are {} {} :", firstName, lastName);

        final PersonInfoDTO response = personInfoService.getPersonsWithNames(firstName, lastName);

        for (final PersonResponse p : response.getPersons()) {
            logger.info("{} {} {} {} {} {}", p.getFirstName(), p.getLastName(), p.getAddress(), p.getAge(), p.getEmailAddress(), p.getMedicalHistory());
        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
