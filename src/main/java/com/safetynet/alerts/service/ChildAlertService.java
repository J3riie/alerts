package com.safetynet.alerts.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.ChildAlertDTO;
import com.safetynet.alerts.dto.response.ChildResponse;
import com.safetynet.alerts.dto.response.FamilyResponse;
import com.safetynet.alerts.repo.DataRepository;

@Service
public class ChildAlertService {

    private final DataRepository repo;

    public ChildAlertService(DataRepository repo) {
        this.repo = repo;
    }

    public ChildAlertDTO getChildrenAtAddress(String address) {
        final List<FamilyResponse> family = getFamilyMembersFromAddress(address);
        final List<ChildResponse> children = getChildrenFromFamily(family);

        final ChildAlertDTO response = new ChildAlertDTO();
        response.setChildren(children);
        return response;
    }

    private List<FamilyResponse> getFamilyMembersFromAddress(String address) {
        final ArrayList<FamilyResponse> family = new ArrayList<>();
        for (final PersonsDTO p : repo.getAllPersons()) {
            if (p.getAddress().equals(address)) {
                final FamilyResponse member = new FamilyResponse();
                member.setFirstName(p.getFirstName());
                member.setLastName(p.getLastName());
                family.add(member);
            }
        }
        return family;
    }

    private List<ChildResponse> getChildrenFromFamily(List<FamilyResponse> family) {
        final ArrayList<ChildResponse> children = new ArrayList<>();
        for (final FamilyResponse member : family) {
            for (final MedicalRecordsDTO m : repo.getAllMedicalRecords()) {
                if (m.getFirstName().equals(member.getFirstName()) && m.getLastName().equals(member.getLastName())) {
                    final LocalDate birthDate = m.getBirthdate();
                    final LocalDate now = LocalDate.now();
                    final long yearsBetween = ChronoUnit.YEARS.between(birthDate, now);
                    final int age = (int) yearsBetween;
                    if (age < 18) {
                        final ChildResponse child = new ChildResponse();
                        child.setFirstName(member.getFirstName());
                        child.setLastName(member.getLastName());
                        child.setAge(age);
                        child.setFamily(family);
                        children.add(child);
                    }
                }
            }
        }
        return children;
    }
}
