package com.safetynet.alerts.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.PhoneAlertDTO;
import com.safetynet.alerts.repository.DataRepository;

@Service
public class PhoneAlertService {

    private final DataRepository repo;

    public PhoneAlertService(DataRepository repo) {
        this.repo = repo;
    }

    public PhoneAlertDTO getPhoneNumbersFromPersonsCoveredByStation(int stationNumber) {
        final List<String> addresses = getAddressesFromStation(stationNumber);
        final List<String> phones = getPhonesFromAddresses(addresses);

        final PhoneAlertDTO response = new PhoneAlertDTO();
        response.setPhones(phones);

        return response;
    }

    private List<String> getAddressesFromStation(int stationNumber) {
        final ArrayList<String> addresses = new ArrayList<>();
        for (final FireStationsDTO f : repo.getAllFirestations()) {
            if (f.getStation() == stationNumber) {
                addresses.add(f.getAddress());
            }
        }
        return addresses;
    }

    private List<String> getPhonesFromAddresses(List<String> addresses) {
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
