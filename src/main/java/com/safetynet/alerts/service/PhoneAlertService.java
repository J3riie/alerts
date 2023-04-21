package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.repo.DataRepository;

@Service
public class PhoneAlertService {

    private final DataRepository repo;

    public PhoneAlertService(DataRepository repo) {
        this.repo = repo;
    }

    public List<String> getAddressesFromStation(int stationNumber) {
        final ArrayList<String> addresses = new ArrayList<>();
        for (final FireStationsDTO f : repo.getAllFirestations()) {
            if (f.getStation() == stationNumber) {
                addresses.add(f.getAddress());
            }
        }
        return addresses;
    }

    public List<String> getPhonesFromAddresses(List<String> addresses) {
        final ArrayList<String> phones = new ArrayList<>();
        for (final String address : addresses) {
            for (final PersonsDTO p : repo.getAllPersons()) {
                if (p.getAddress().equals(address)) {
                    phones.add(p.getPhone());
                }
            }
        }
        return phones;
    }
}
