package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.repo.DataRepository;

@Service
public class CommunityEmailService {

    private final DataRepository repo;

    public CommunityEmailService(DataRepository repo) {
        this.repo = repo;
    }

    public List<String> getEmailAddressesFromCity(String city) {
        final ArrayList<String> emailAddresses = new ArrayList<>();
        for (final PersonsDTO p : repo.getAllPersons()) {
            if (p.getCity().equals(city)) {
                emailAddresses.add(p.getEmail());
            }
        }
        return emailAddresses;
    }
}
