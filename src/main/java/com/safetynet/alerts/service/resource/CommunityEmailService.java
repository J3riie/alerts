package com.safetynet.alerts.service.resource;

import java.util.ArrayList;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.PersonsDTO;

public class CommunityEmailService {

    public ArrayList<String> getEmailAddressesFromCity(String city) {
        final ArrayList<String> emailAddresses = new ArrayList<>();
        for (final PersonsDTO p : App.getPersons()) {
            if (p.getCity().equals(city)) {
                emailAddresses.add(p.getEmail());
            }
        }
        return emailAddresses;
    }
}
