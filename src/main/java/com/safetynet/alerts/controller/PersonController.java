package com.safetynet.alerts.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.service.PersonService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonService personService;

    @PostMapping
    ResponseEntity<Void> addPerson(@RequestBody @Valid PersonsDTO newPerson) {
        logger.info("Adding {} {} in the data", newPerson.getFirstName(), newPerson.getLastName());
        personService.addPerson(newPerson);
        logger.info("Person added successfully");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    ResponseEntity<Void> modifyPerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName,
            @RequestParam(required = false, name = "address") String address, @RequestParam(required = false, name = "city") String city,
            @RequestParam(required = false, name = "zip") Integer zip, @RequestParam(required = false, name = "phone") String phone,
            @RequestParam(required = false, name = "email") String email) {
        logger.info("Modifying {} {}", firstName, lastName);
        try {
            personService.modifyPerson(firstName, lastName, address, city, zip, phone, email);
            logger.info("Person modified successfully");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (final Exception e) {
            logger.error("Person requested could not be found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping
    ResponseEntity<Void> deletePerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        logger.info("Deleting {} {} from the data", firstName, lastName);
        final Optional<PersonsDTO> optionalPerson = personService.deletePerson(firstName, lastName);
        if (optionalPerson.isPresent()) {
            logger.info("Person deleted successfully");
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        logger.error("Person requested could not be found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
