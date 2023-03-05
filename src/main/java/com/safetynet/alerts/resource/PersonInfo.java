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

import com.safetynet.alerts.dto.resource.PersonInfoDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.dto.response.PersonResponse;
import com.safetynet.alerts.service.resource.PersonInfoService;

@RestController
@RequestMapping(value = "/personInfo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonInfo {

    private static final Logger logger = LogManager.getLogger(PersonInfo.class);

    @Autowired
    PersonInfoService personInfoService;

    @GetMapping
    APIResponse<PersonInfoDTO> index(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        logger.info("List of the persons info whose names are {} {} :", firstName, lastName);
        final ArrayList<PersonResponse> personsInfo = personInfoService.getInfoFromNames(firstName, lastName);
        personInfoService.setPersonsMedicalHistory(personsInfo);

        final PersonInfoDTO response = new PersonInfoDTO();
        response.setPersons(personsInfo);

        for (final PersonResponse p : personsInfo) {
            logger.info("{} {} {} {} {} {}", p.getFirstName(), p.getLastName(), p.getAddress(), p.getAge(), p.getEmailAddress(), p.getMedicalHistory());
        }
        return new APIResponse<>(HttpStatus.OK.value(), String.format("List of the persons info whose names are %s %s", firstName, lastName), response);
    }
}
