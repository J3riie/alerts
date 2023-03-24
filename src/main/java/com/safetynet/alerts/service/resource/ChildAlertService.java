package com.safetynet.alerts.service.resource;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.response.ChildResponse;
import com.safetynet.alerts.dto.response.FamilyResponse;

@Service
public class ChildAlertService {

    public ArrayList<FamilyResponse> getFamilyMembersFromAddress(String address) {
        final ArrayList<FamilyResponse> family = new ArrayList<>();
        for (final PersonsDTO p : App.getPersons()) {
            if (p.getAddress().equals(address)) {
                final FamilyResponse member = new FamilyResponse();
                member.setFirstName(p.getFirstName());
                member.setLastName(p.getLastName());
                family.add(member);
            }
        }
        return family;
    }

    public ArrayList<ChildResponse> getChildrenFromFamily(ArrayList<FamilyResponse> family) {
        final ArrayList<ChildResponse> children = new ArrayList<>();
        for (final FamilyResponse member : family) {
            for (final MedicalRecordsDTO m : App.getMedicalRecords()) {
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
