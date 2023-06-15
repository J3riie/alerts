package com.safetynet.alerts.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.repository.DataRepository;

@Service
public class PersonService {

    private final DataRepository repo;

    public PersonService(DataRepository repo) {
        this.repo = repo;
    }

    public void addPerson(PersonsDTO person) {
        repo.getAllPersons().add(person);
    }

    public PersonsDTO modifyPerson(String firstName, String lastName, String address, String city, Integer zip, String phone, String email) {
        final PersonsDTO person = repo.findPersonWithName(firstName, lastName);
        if (address != null) {
            person.setAddress(address);
        }
        if (city != null) {
            person.setCity(city);
        }
        if (zip != null) {
            person.setZip(zip);
        }
        if (phone != null) {
            person.setPhone(phone);
        }
        if (email != null) {
            person.setEmail(email);
        }
        return person;
    }

    public Optional<PersonsDTO> deletePerson(String firstName, String lastName) {
        final Optional<PersonsDTO> optionalPerson = repo.getAllPersons().stream().filter(p -> p.personExists(firstName, lastName)).findFirst();
        if (optionalPerson.isPresent()) {
            repo.getAllPersons().remove(optionalPerson.get());
        }
        return optionalPerson;
    }
}
