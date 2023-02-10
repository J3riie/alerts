package com.safetynet.alerts.endpoint;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.PersonsDTO;

@RestController
@RequestMapping(value = "/person", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonEndpoint {
    private static final Logger logger = LogManager.getLogger(PersonEndpoint.class);

    @PostMapping(value = "/post")
    void post(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "address") String address, @RequestParam(name = "city") String city, @RequestParam(name = "zip") int zip,
            @RequestParam(name = "phone") String phone, @RequestParam(name = "email") String email) {
        logger.info("Adding a new person in the data");
        final ArrayList<PersonsDTO> persons = new ArrayList<>(App.getPersons());
        final PersonsDTO personToPost = new PersonsDTO();
        personToPost.setFirstName(firstName);
        personToPost.setLastName(lastName);
        personToPost.setAddress(address);
        personToPost.setCity(city);
        personToPost.setZip(zip);
        personToPost.setPhone(phone);
        personToPost.setEmail(email);
        persons.add(personToPost);
        App.getDataJson().setPersons(persons);
    }

    @PutMapping(value = "/put")
    void put(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName,
            @RequestParam(required = false, name = "address") String address, @RequestParam(required = false, name = "city") String city,
            @RequestParam(required = false, name = "zip") Integer zip, @RequestParam(required = false, name = "phone") String phone,
            @RequestParam(required = false, name = "email") String email) {
        logger.info("Modifying {} {}", firstName, lastName);
        final ArrayList<PersonsDTO> persons = new ArrayList<>(App.getPersons());
        for (final PersonsDTO p : persons) {
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
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
            }
        }
        App.getDataJson().setPersons(persons);
    }

    @DeleteMapping(value = "/delete")
    void delete(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName) {
        logger.info("Deleting {} {} from the data", firstName, lastName);
        final ArrayList<PersonsDTO> persons = new ArrayList<>(App.getPersons());
        for (final PersonsDTO p : persons) {
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                persons.remove(p);
                break;
            }
        }
        App.getDataJson().setPersons(persons);
    }
}
