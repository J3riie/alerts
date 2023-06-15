package com.safetynet.alerts.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.FireStationDTO;
import com.safetynet.alerts.dto.response.CoveredPersonResponse;
import com.safetynet.alerts.repository.DataRepository;

@Service
public class FireStationService {

    private final DataRepository repo;

    public FireStationService(DataRepository repo) {
        this.repo = repo;
    }

    public FireStationDTO getPersonsCoveredByStation(int stationNumber) {

        final List<CoveredPersonResponse> personsCovered = getInfoFromStation(stationNumber);

        final int[] numberOfAdultsAndChildren = getNumberOfAdultsAndChildren(personsCovered);
        final int numberOfAdults = numberOfAdultsAndChildren[0];
        final int numberOfChildren = numberOfAdultsAndChildren[1];

        final FireStationDTO response = new FireStationDTO();
        response.setCoveredPersons(personsCovered);
        response.setNumberOfAdults(numberOfAdults);
        response.setNumberOfChildren(numberOfChildren);

        return response;
    }

    private List<CoveredPersonResponse> getInfoFromStation(int stationNumber) {
        final List<String> addresses = getAddressesFromStation(stationNumber);
        return getInfoFromAddresses(addresses);
    }

    public void addFireStation(FireStationsDTO firestation) {
        repo.getAllFirestations().add(firestation);
    }

    public FireStationsDTO modifyFireStation(String address, Integer station) {
        final FireStationsDTO firestation = repo.findFireStationFromAddress(address);
        firestation.setStation(station);
        return firestation;
    }

    public Optional<FireStationsDTO> deleteFireStation(String address) {
        final Optional<FireStationsDTO> optionalFirestation = repo.getAllFirestations().stream().filter(f -> f.addressMappingExists(address)).findFirst();
        if (optionalFirestation.isPresent()) {
            repo.getAllFirestations().remove(optionalFirestation.get());
        }
        return optionalFirestation;
    }

    public boolean deleteFireStation(Integer station) {
        Optional<FireStationsDTO> optionalFirestation = repo.getAllFirestations().stream().filter(f -> f.stationMappingExists(station)).findFirst();
        boolean isPresent = false;
        while (optionalFirestation.isPresent()) {
            isPresent = true;
            repo.getAllFirestations().remove(optionalFirestation.get());
            optionalFirestation = repo.getAllFirestations().stream().filter(f -> f.stationMappingExists(station)).findFirst();
        }
        return isPresent;
    }

    private int[] getNumberOfAdultsAndChildren(List<CoveredPersonResponse> personsCovered) {
        final int[] numberOfAdultsAndChildren = { 0, 0 };
        for (final CoveredPersonResponse p : personsCovered) {
            for (final MedicalRecordsDTO m : repo.getAllMedicalRecords()) {
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

    private List<String> getAddressesFromStation(int stationNumber) {
        final ArrayList<String> addresses = new ArrayList<>();
        for (final FireStationsDTO f : repo.getAllFirestations()) {
            if (f.getStation() == stationNumber) {
                addresses.add(f.getAddress());
            }
        }
        return addresses;
    }

    private List<CoveredPersonResponse> getInfoFromAddresses(List<String> addresses) {
        final ArrayList<CoveredPersonResponse> personsCovered = new ArrayList<>();
        for (final PersonsDTO p : repo.getAllPersons()) {
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
}
