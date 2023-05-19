package com.safetynet.alerts.dto.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PhoneAlertDTO {

    @JsonProperty("phones")
    private List<String> phones;

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
