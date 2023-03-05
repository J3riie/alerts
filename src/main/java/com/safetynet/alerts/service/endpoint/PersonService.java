package com.safetynet.alerts.service.endpoint;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.PersonsDTO;

@Service
public class PersonService {

    public void addPerson(PersonsDTO person) {
        App.getPersons().add(person);
    }

    public PersonsDTO modifyPerson(String firstName, String lastName, String address, String city, Integer zip, String phone, String email) {
        return App.getPersons().stream().filter(p -> p.personExists(firstName, lastName)).findFirst().map(p -> {
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
    }

    public Optional<PersonsDTO> deletePerson(String firstName, String lastName) {
        final Optional<PersonsDTO> optionalPerson = App.getPersons().stream().filter(p -> p.personExists(firstName, lastName)).findFirst();
        if (optionalPerson.isPresent()) {
            App.getPersons().remove(optionalPerson.get());
        }
        return optionalPerson;
    }
}
