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

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.service.PersonService;

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
        logger.info("Adding a new person in the data");
        personService.addPerson(newPerson);
        return new APIResponse<>(HttpStatus.CREATED.value(), "Person added successfully");
    }

    @PutMapping
    APIResponse<Void> modifyPerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName,
            @RequestParam(required = false, name = "address") String address, @RequestParam(required = false, name = "city") String city,
            @RequestParam(required = false, name = "zip") Integer zip, @RequestParam(required = false, name = "phone") String phone,
            @RequestParam(required = false, name = "email") String email) {
        logger.info("Modifying {} {}", firstName, lastName);
        final PersonsDTO person = App.getPersons().stream().filter(p -> p.personExists(firstName, lastName)).findFirst().map(p -> {
            if (address != null) {
                p.setAddress(address);
            }
            if (city != null) {
                p.setCity(city);
            }
            if (zip != null) {
                p.setZip(zip);
            }
            if (phone != null) {
                p.setPhone(phone);
            }
            if (email != null) {
                p.setEmail(email);
            }
            return p;
        }).orElse(null);
        if (person == null) {
            return new APIResponse<>(HttpStatus.NOT_FOUND.value(), "Unable to modify person : not found");
        }
        return new APIResponse<>(HttpStatus.NO_CONTENT.value(), "Person modified successfully");
    }

    @DeleteMapping
    APIResponse<Void> deletePerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        logger.info("Deleting {} {} from the data", firstName, lastName);
        final Optional<PersonsDTO> optionalPerson = App.getPersons().stream().filter(p -> p.personExists(firstName, lastName)).findFirst();
        if (optionalPerson.isPresent()) {
            App.getPersons().remove(optionalPerson.get());
            return new APIResponse<>(HttpStatus.OK.value(), "Person deleted successfully");
        }
        return new APIResponse<>(HttpStatus.NOT_FOUND.value(), "Unable to delete person : not found");
    }
}
