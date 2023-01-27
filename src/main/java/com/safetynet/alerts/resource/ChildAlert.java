package com.safetynet.alerts.resource;

import static org.springframework.http.HttpStatus.OK;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.ChildAlertDTO;
import com.safetynet.alerts.dto.FamilyDTO;

@RestController
@RequestMapping(value = "/childAlert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class ChildAlert {
    private static final Logger logger = LogManager.getLogger(ChildAlert.class);

    @GetMapping
    ResponseEntity<ArrayList<ChildAlertDTO>> index(@RequestParam(name = "address") String address) {
        try (final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.json")) {
            logger.info("List of the children living at {} and their family :", address);
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode node = objectMapper.readTree(in);

            final JsonNode persons = node.get("persons");
            final ArrayList<FamilyDTO> family = new ArrayList<>();
            persons.forEach(e -> getFamilyMembersFromAddress(address, family, e));

            final JsonNode medicalRecords = node.get("medicalrecords");
            final ArrayList<ChildAlertDTO> children = new ArrayList<>();
            medicalRecords.forEach(e -> getChildrenFromFamily(family, children, e));

            for (final ChildAlertDTO c : children) {
                logger.info("{} {} {} {}", c.getFirstName(), c.getLastName(), c.getAge(), c.getFamily());
            }
            return ResponseEntity.status(OK).body(children);
        } catch (final Exception e) {
            logger.error("Json file not reachable/not present in the classpath", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    private void getFamilyMembersFromAddress(String address, ArrayList<FamilyDTO> family, JsonNode e) {
        if (e.get("address").asText().equals(address)) {
            final FamilyDTO member = new FamilyDTO();
            member.setFirstName(e.get("firstName").asText());
            member.setLastName(e.get("lastName").asText());
            family.add(member);
        }
    }

    private void getChildrenFromFamily(ArrayList<FamilyDTO> family, ArrayList<ChildAlertDTO> children, JsonNode e) {
        for (final FamilyDTO member : family) {
            if (e.get("firstName").asText().equals(member.getFirstName()) && e.get("lastName").asText().equals(member.getLastName())) {
                final String birthDateAsString = e.get("birthdate").asText();
                final String[] splitBirthDate = birthDateAsString.split("/");
                final int birthYear = Integer.parseInt(splitBirthDate[2]);
                final int actualYear = Calendar.getInstance().get(Calendar.YEAR);
                if (actualYear - birthYear < 18) {
                    final ChildAlertDTO child = new ChildAlertDTO();
                    child.setFirstName(member.getFirstName());
                    child.setLastName(member.getLastName());
                    child.setAge(actualYear - birthYear);
                    child.setFamily(family);
                    children.add(child);
                }
            }
        }
    }
}
