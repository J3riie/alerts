package com.safetynet.alerts.endpoint;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.service.endpoint.PersonService;

import jakarta.validation.Valid;

@RestController
@Validated
@RequestMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonEndpoint {
    private static final Logger logger = LogManager.getLogger(PersonEndpoint.class);

    @Autowired
    private PersonService personService;

    @PostMapping
    APIResponse<Void> addPerson(@RequestBody @Valid PersonsDTO newPerson) {
        logger.info("Adding {} {} in the data", newPerson.getFirstName(), newPerson.getLastName());
        personService.addPerson(newPerson);
        return new APIResponse<>(HttpStatus.CREATED.value(), String.format("%s %s added successfully", newPerson.getFirstName(), newPerson.getLastName()));
    }

    @PutMapping
    APIResponse<Void> modifyPerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName,
            @RequestParam(required = false, name = "address") String address, @RequestParam(required = false, name = "city") String city,
            @RequestParam(required = false, name = "zip") Integer zip, @RequestParam(required = false, name = "phone") String phone,
            @RequestParam(required = false, name = "email") String email) {
        logger.info("Modifying {} {}", firstName, lastName);
        final PersonsDTO person = personService.modifyPerson(firstName, lastName, address, city, zip, phone, email);
        if (person == null) {
            return new APIResponse<>(HttpStatus.NOT_FOUND.value(), String.format("Unable to modify %s %s : person not found", firstName, lastName));
        }
        return new APIResponse<>(HttpStatus.NO_CONTENT.value(), String.format("%s %s modified successfully", firstName, lastName));
    }

    @DeleteMapping
    APIResponse<Void> deletePerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        logger.info("Deleting {} {} from the data", firstName, lastName);
        final Optional<PersonsDTO> optionalPerson = personService.deletePerson(firstName, lastName);
        if (optionalPerson.isPresent()) {
            return new APIResponse<>(HttpStatus.OK.value(), String.format("%s %s deleted successfully", firstName, lastName));
        }
        return new APIResponse<>(HttpStatus.NOT_FOUND.value(), String.format("Unable to delete %s %s : person not found", firstName, lastName));
    }
}
