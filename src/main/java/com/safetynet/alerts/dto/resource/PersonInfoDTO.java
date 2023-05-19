package com.safetynet.alerts.dto.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.safetynet.alerts.dto.response.PersonResponse;

public class PersonInfoDTO {

    @JsonProperty("persons")
    private List<PersonResponse> persons;

    public List<PersonResponse> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonResponse> persons) {
        this.persons = persons;
    }
}
