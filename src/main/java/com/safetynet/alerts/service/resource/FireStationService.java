package com.safetynet.alerts.service.resource;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.response.CoveredPersonResponse;

@Service
public class FireStationService {

    public ArrayList<String> getAddressesFromStation(int stationNumber) {
        final ArrayList<String> addresses = new ArrayList<>();
        for (final FireStationsDTO f : App.getFirestations()) {
            if (f.getStation() == stationNumber) {
                addresses.add(f.getAddress());
            }
        }
        return addresses;
    }

    public ArrayList<CoveredPersonResponse> getInfoFromAddresses(List<String> addresses) {
        final ArrayList<CoveredPersonResponse> personsCovered = new ArrayList<>();
        for (final PersonsDTO p : App.getPersons()) {
            for (final String address : addresses) {
                if (p.getAddress().equals(address)) {
                    final CoveredPersonResponse personCovered = new CoveredPersonResponse();
                    personCovered.setFirstName(p.getFirstName());
                    personCovered.setLastName(p.getLastName());
                    personCovered.setAddress(p.getAddress());
                    personCovered.setPhone(p.getPhone());
                    personsCovered.add(personCovered);
                }
            }
        }
        return personsCovered;
    }

    public int[] getNumberOfAdultsAndChildren(List<CoveredPersonResponse> personsCovered) {
        final int[] numberOfAdultsAndChildren = { 0, 0 };
        for (final CoveredPersonResponse p : personsCovered) {
            for (final MedicalRecordsDTO m : App.getMedicalRecords()) {
                if (m.getFirstName().equals(p.getFirstName()) && m.getLastName().equals(p.getLastName())) {
                    final LocalDate birthDate = m.getBirthdate();
                    final LocalDate now = LocalDate.now();
                    final long yearsBetween = ChronoUnit.YEARS.between(birthDate, now);
                    final int age = (int) yearsBetween;
                    if (age >= 18) {
                        numberOfAdultsAndChildren[0]++;
                    } else {
                        numberOfAdultsAndChildren[1]++;
                    }
                }
            }
        }
        return numberOfAdultsAndChildren;
    }
}
