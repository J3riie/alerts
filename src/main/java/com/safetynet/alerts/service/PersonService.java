package com.safetynet.alerts.service;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.PersonsDTO;

@Service
public class PersonService {

    public void addPerson(PersonsDTO person) {
        App.getPersons().add(person);
    }
}
