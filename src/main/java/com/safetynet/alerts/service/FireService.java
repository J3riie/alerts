package com.safetynet.alerts.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.FireDTO;
import com.safetynet.alerts.dto.response.InhabitantResponse;
import com.safetynet.alerts.dto.response.MedicalHistory;
import com.safetynet.alerts.repo.DataRepository;

@Service
public class FireService {

    private final DataRepository repo;

    public FireService(DataRepository repo) {
        this.repo = repo;
    }

    public FireDTO getPersonsInfoAtAddress(String address) {
        final int stationNumber = getFirestationNumberFromAddress(address);
        final List<InhabitantResponse> inhabitants = getInfoFromAddress(address);
        setPersonsMedicalHistory(inhabitants);

        final FireDTO response = new FireDTO();
        response.setInhabitants(inhabitants);
        response.setCoveringStation(stationNumber);
        return response;
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

    private int getFirestationNumberFromAddress(String address) {
        for (final FireStationsDTO f : repo.getAllFirestations()) {
            if (f.getAddress().equals(address)) {
                return f.getStation();
            }
        }
        return -1;
    }
}
