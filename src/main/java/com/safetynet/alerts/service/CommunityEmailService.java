package com.safetynet.alerts.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.CommunityEmailDTO;
import com.safetynet.alerts.repo.DataRepository;

@Service
public class CommunityEmailService {

    private final DataRepository repo;

    public CommunityEmailService(DataRepository repo) {
        this.repo = repo;
    }

    public CommunityEmailDTO getEmailAddressesFromCity(String city) {
        final ArrayList<String> emailAddresses = new ArrayList<>();
        for (final PersonsDTO p : repo.getAllPersons()) {
            if (p.getCity().equals(city)) {
                emailAddresses.add(p.getEmail());
            }
        }
        final CommunityEmailDTO response = new CommunityEmailDTO();
        response.setEmailAddresses(emailAddresses);
        return response;
    }
}
