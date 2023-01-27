package com.safetynet.alerts.resource;

import static org.springframework.http.HttpStatus.OK;

import java.io.InputStream;
import java.util.ArrayList;

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
import com.safetynet.alerts.dto.PhoneAlertDTO;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/phoneAlert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class PhoneAlert {
    private static final Logger logger = LogManager.getLogger(PhoneAlert.class);

    @GetMapping
    ResponseEntity<ArrayList<PhoneAlertDTO>> index(
            @RequestParam(name = "firestation") @Min(value = 1, message = "The value needs to be strictly positive") int stationNumber) {
        try (final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("data.json")) {
            logger.info("List of phone number of the persons covered by the firestation number {} :", stationNumber);
            final ObjectMapper objectMapper = new ObjectMapper();
            final JsonNode node = objectMapper.readTree(in);
            final JsonNode firestations = node.get("firestations");
            final ArrayList<String> addresses = new ArrayList<>();
            firestations.forEach(e -> getAddressesFromStation(stationNumber, addresses, e));

            final JsonNode persons = node.get("persons");
            final ArrayList<PhoneAlertDTO> phones = new ArrayList<>();
            persons.forEach(e -> getPhonesFromAddresses(addresses, phones, e));

            for (final PhoneAlertDTO p : phones) {
                logger.info("{}", p.getPhone());
            }
            return ResponseEntity.status(OK).body(phones);
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

    private void getPhonesFromAddresses(ArrayList<String> addresses, ArrayList<PhoneAlertDTO> phones, JsonNode e) {
        for (final String address : addresses) {
            if (e.get("address").asText().equals(address)) {
                final PhoneAlertDTO phone = new PhoneAlertDTO();
                phone.setPhone(e.get("phone").asText());
                phones.add(phone);
            }
        }
    }
}