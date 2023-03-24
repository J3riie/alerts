package com.safetynet.alerts.service.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;

@Service
public class PhoneAlertService {

    public ArrayList<String> getAddressesFromStation(int stationNumber) {
        final ArrayList<String> addresses = new ArrayList<>();
        for (final FireStationsDTO f : App.getFirestations()) {
            if (f.getStation() == stationNumber) {
                addresses.add(f.getAddress());
            }
        }
        return addresses;
    }

    public ArrayList<String> getPhonesFromAddresses(List<String> addresses) {
        final ArrayList<String> phones = new ArrayList<>();
        for (final String address : addresses) {
            for (final PersonsDTO p : App.getPersons()) {
                if (p.getAddress().equals(address)) {
                    phones.add(p.getPhone());
                }
            }
        }
        return phones;
    }
}
