package com.safetynet.alerts.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.response.MedicalHistory;
import com.safetynet.alerts.dto.response.PersonResponse;
import com.safetynet.alerts.repo.DataRepository;

@Service
public class PersonInfoService {

    private final DataRepository repo;

    public PersonInfoService(DataRepository repo) {
        this.repo = repo;
    }

    public List<PersonResponse> getInfoFromNames(String firstName, String lastName) {
        final ArrayList<PersonResponse> persons = new ArrayList<>();
        for (final PersonsDTO p : repo.getAllPersons()) {
            if (p.getFirstName().equals(firstName) && p.getLastName().equals(lastName)) {
                final PersonResponse person = new PersonResponse();
                person.setFirstName(p.getFirstName());
                person.setLastName(p.getLastName());
                person.setAddress(p.getAddress());
                person.setEmailAddress(p.getEmail());
                for (final MedicalRecordsDTO m : repo.getAllMedicalRecords()) {
                    if (m.getFirstName().equals(firstName) && m.getLastName().equals(lastName)) {
                        final LocalDate birthDate = m.getBirthdate();
                        final LocalDate now = LocalDate.now();
                        final long yearsBetween = ChronoUnit.YEARS.between(birthDate, now);
                        final int age = (int) yearsBetween;
                        person.setAge(age);
                    }
                }
                persons.add(person);
            }
        }
        return persons;
    }

    public void setPersonsMedicalHistory(List<PersonResponse> persons) {
        for (final PersonResponse p : persons) {
            for (final MedicalRecordsDTO m : repo.getAllMedicalRecords()) {
                if (m.getFirstName().equals(p.getFirstName()) && m.getLastName().equals(p.getLastName())) {
                    final MedicalHistory medicationResponse = new MedicalHistory();
                    medicationResponse.setMedications(m.getMedications());
                    medicationResponse.setAllergies(m.getAllergies());
                    p.setMedicalHistory(medicationResponse);
                }
            }
        }
    }

}
