package com.safetynet.alerts.dto.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommunityEmailDTO {

    @JsonProperty("emails")
    private List<String> emailAddresses;

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }
}
