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

import com.safetynet.alerts.dto.parent.PersonDTO;

@RestController
@RequestMapping(value = "/childAlert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChildAlert {
    private static final Logger logger = LogManager.getLogger(ChildAlert.class);

    @GetMapping
    ResponseEntity<ArrayList<PersonDTO>> index(@RequestParam(name = "address") String address) {
        logger.info("List of children living at {} :", address);
        final ArrayList<PersonDTO> persons = new ArrayList<>();
        final PersonDTO person = new PersonDTO();

        person.setFirstName("Robin");
        person.setLastName("Hugues");
        person.setAddress("add");
        person.setPhone("00");
        persons.add(person);
        for (final PersonDTO p : persons) {
            logger.info("{} {}", p.getFirstName(), p.getLastName());
        }
        return ResponseEntity.status(OK).body(persons);
    }
}
