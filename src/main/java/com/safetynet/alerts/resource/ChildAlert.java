package com.safetynet.alerts.resource;

import static java.time.ZoneId.systemDefault;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.App;
import com.safetynet.alerts.dto.node.MedicalRecordsDTO;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.dto.resource.ChildAlertDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.dto.response.ChildResponse;
import com.safetynet.alerts.dto.response.FamilyResponse;

@RestController
@RequestMapping(value = "/childAlert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChildAlert {
    private static final Logger logger = LogManager.getLogger(ChildAlert.class);

    @GetMapping
    APIResponse<ChildAlertDTO> index(@RequestParam(name = "address") String address) {
        logger.info("List of the children living at {} and their family :", address);
        final ArrayList<FamilyResponse> family = getFamilyMembersFromAddress(address);
        final ArrayList<ChildResponse> children = getChildrenFromFamily(family);

        final ChildAlertDTO response = new ChildAlertDTO();
        response.setChildren(children);

        for (final ChildResponse c : children) {
            logger.info("{} {} {}", c.getFirstName(), c.getLastName(), c.getAge());
            logger.info("Family :");
            for (final FamilyResponse f : c.getFamily()) {
                logger.info("{} {}", f.getFirstName(), f.getLastName());
            }
        }
        return new APIResponse<>(HttpStatus.OK.value(), "List of the children living at given address and their family");
    }

    private ArrayList<FamilyResponse> getFamilyMembersFromAddress(String address) {
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

    private ArrayList<ChildResponse> getChildrenFromFamily(List<FamilyResponse> family) {
        final ArrayList<ChildResponse> children = new ArrayList<>();
        for (final FamilyResponse member : family) {
            for (final MedicalRecordsDTO m : App.getMedicalrecords()) {
                if (m.getFirstName().equals(member.getFirstName()) && m.getLastName().equals(member.getLastName())) {
                    final LocalDate birthDate = m.getBirthdate().toInstant().atZone(systemDefault()).toLocalDate();
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
