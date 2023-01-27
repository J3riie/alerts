package com.safetynet.alerts.resource;

import static org.springframework.http.HttpStatus.OK;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.FireStationDTO;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/firestation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class FireStation {
    private static final Logger logger = LogManager.getLogger(FireStation.class);

    @GetMapping
    ResponseEntity<ArrayList<FireStationDTO>> index(
            @RequestParam(name = "stationNumber") @Min(value = 1, message = "The value needs to be strictly positive") int stationNumber) {
        try (final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.json")) {
            logger.info("List of the persons covered by the firestation number {} :", stationNumber);
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode node = objectMapper.readTree(in);
            final JsonNode firestations = node.get("firestations");
            final ArrayList<String> addresses = new ArrayList<>();
            firestations.forEach(e -> getAddressesFromStation(stationNumber, addresses, e));

            final JsonNode persons = node.get("persons");
            final ArrayList<FireStationDTO> personsServed = new ArrayList<>();
            persons.forEach(e -> getInfoFromAddresses(addresses, personsServed, e));

            final JsonNode medicalRecords = node.get("medicalrecords");
            medicalRecords.forEach(e -> getAgeFromNames(personsServed, e));

            int childNumber = 0;
            int adultNumber = 0;
            for (final FireStationDTO p : personsServed) {
                if (p.isAdult()) {
                    adultNumber++;
                } else {
                    childNumber++;
                }
            }

            for (final FireStationDTO p : personsServed) {
                logger.info("{} {} {} {}", p.getFirstName(), p.getLastName(), p.getAddress(), p.getPhone());
            }
            logger.info("Number of adults : {}", adultNumber);
            logger.info("Number of children : {}", childNumber);
            return ResponseEntity.status(OK).body(personsServed);
        } catch (final Exception e) {
            logger.error("Json file not reachable/not present in the classpath", e);
            return ResponseEntity.status(500).body(null);
        }
    }

    private void getAddressesFromStation(int stationNumber, ArrayList<String> addresses, JsonNode e) {
        if (e.get("station").asInt() == stationNumber) {
            addresses.add(e.get("address").asText());
        }
    }

    private void getInfoFromAddresses(ArrayList<String> addresses, ArrayList<FireStationDTO> personsServed, JsonNode e) {
        for (final String address : addresses) {
            if (e.get("address").asText().equals(address)) {
                final FireStationDTO personServed = new FireStationDTO();
                personServed.setFirstName(e.get("firstName").asText());
                personServed.setLastName(e.get("lastName").asText());
                personServed.setAddress(e.get("address").asText());
                personServed.setPhone(e.get("phone").asText());
                personsServed.add(personServed);
            }
        }
    }

    private void getAgeFromNames(ArrayList<FireStationDTO> personsServed, JsonNode e) {
        for (final FireStationDTO p : personsServed) {
            if (e.get("firstName").asText().equals(p.getFirstName()) && e.get("lastName").asText().equals(p.getLastName())) {
                final String birthDateAsString = e.get("birthdate").asText();
                final String[] splitBirthDate = birthDateAsString.split("/");
                final int birthYear = Integer.parseInt(splitBirthDate[2]);
                final int actualYear = Calendar.getInstance().get(Calendar.YEAR);
                if (actualYear - birthYear >= 18) {
                    p.setAdult(true);
                }
            }
        }
    }
}
