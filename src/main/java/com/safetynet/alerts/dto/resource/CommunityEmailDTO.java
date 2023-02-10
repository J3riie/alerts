package com.safetynet.alerts.dto.resource;

import java.util.List;

public class CommunityEmailDTO {

    private List<String> emailAddresses;

    public List<String> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(List<String> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }
}
