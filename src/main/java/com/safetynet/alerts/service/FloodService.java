package com.safetynet.alerts.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.FloodDTO;
import com.safetynet.alerts.dto.response.CoveredHouseResponse;
import com.safetynet.alerts.dto.response.InhabitantResponse;
import com.safetynet.alerts.dto.response.MedicalHistory;
import com.safetynet.alerts.repository.DataRepository;

@Service
public class FloodService {

    private final DataRepository repo;

    public FloodService(DataRepository repo) {
        this.repo = repo;
    }

    public FloodDTO getInfoFromPersonsCoveredByStations(List<Integer> stations) {
        final List<String> stationAddresses = getFirestationAddressesFromNumbers(stations);

        final ArrayList<CoveredHouseResponse> coveredHouses = new ArrayList<>();
        for (final String s : stationAddresses) {
            final List<InhabitantResponse> inhabitants = getInfoFromAddress(s);
            setPersonsMedicalHistory(inhabitants);
            final CoveredHouseResponse coveredHouse = new CoveredHouseResponse();
            coveredHouse.setAddress(s);
            coveredHouse.setInhabitants(inhabitants);
            coveredHouses.add(coveredHouse);
        }

        final FloodDTO response = new FloodDTO();
        response.setCoveredHouses(coveredHouses);
        return response;
    }

    private List<String> getFirestationAddressesFromNumbers(List<Integer> stations) {
        final ArrayList<String> stationAddresses = new ArrayList<>();
        for (final int s : stations) {
            for (final FireStationsDTO f : repo.getAllFirestations()) {
                if (f.getStation() == s && !stationAddresses.contains(f.getAddress())) {
                    stationAddresses.add(f.getAddress());
                }
            }
        }
        return stationAddresses;
    }

    private List<InhabitantResponse> getInfoFromAddress(String address) {
        final ArrayList<InhabitantResponse> inhabitants = new ArrayList<>();
        for (final PersonsDTO p : repo.getAllPersons()) {
            if (p.getAddress().equals(address)) {
                final InhabitantResponse inhabitant = new InhabitantResponse();
                inhabitant.setFirstName(p.getFirstName());
                inhabitant.setLastName(p.getLastName());
                inhabitant.setPhone(p.getPhone());
                inhabitants.add(inhabitant);
            }
        }
        for (final InhabitantResponse i : inhabitants) {
            for (final MedicalRecordsDTO m : repo.getAllMedicalRecords()) {
                if (m.getFirstName().equals(i.getFirstName()) && m.getLastName().equals(i.getLastName())) {
                    final LocalDate birthDate = m.getBirthdate();
                    final LocalDate now = LocalDate.now();
                    final long yearsBetween = ChronoUnit.YEARS.between(birthDate, now);
                    final int age = (int) yearsBetween;
                    i.setAge(age);
                }
            }
        }
        return inhabitants;
    }

    private void setPersonsMedicalHistory(List<InhabitantResponse> inhabitants) {
        for (final InhabitantResponse i : inhabitants) {
            for (final MedicalRecordsDTO m : repo.getAllMedicalRecords()) {
                if (m.getFirstName().equals(i.getFirstName()) && m.getLastName().equals(i.getLastName())) {
                    final MedicalHistory medicationResponse = new MedicalHistory();
                    medicationResponse.setMedications(m.getMedications());
                    medicationResponse.setAllergies(m.getAllergies());
                    i.setMedicalHistory(medicationResponse);
                }
            }
        }
    }

}
