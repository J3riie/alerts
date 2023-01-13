package com.safetynet.alerts;

import static org.springframework.http.HttpStatus.OK;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.PersonDTO;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class HelloController {
    private static final Logger logger = LogManager.getLogger(HelloController.class);

    @GetMapping
    ResponseEntity<PersonDTO> index(
            @RequestParam(name = "stationNumber") @Min(value = 1, message = "The value needs to be strictly positive") int stationNumber) {
        logger.info("List of the persons covered by the firestation number {} :", stationNumber);
        final PersonDTO person = new PersonDTO();
        person.setFirstName("Robin");
        person.setLastName("Hugues");
        person.setAddress("add");
        person.setPhone("00");
        return ResponseEntity.status(OK).body(person);
    }
}
