package com.safetynet.alerts.resource;

import static org.springframework.http.HttpStatus.OK;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.child.PhoneAlertDTO;

import jakarta.validation.constraints.Min;

@RestController
@RequestMapping(value = "/phoneAlert", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class PhoneAlert {
    private static final Logger logger = LogManager.getLogger(PhoneAlert.class);

    @GetMapping
    ResponseEntity<ArrayList<PhoneAlertDTO>> index(
            @RequestParam(name = "firestation") @Min(value = 1, message = "The value needs to be strictly positive") int stationNumber)
            throws JsonProcessingException {
        logger.info("List of phone number of the persons covered by the firestation number {} :", stationNumber);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = "{\r\n" + "    \"persons\": [\r\n"
                + "        { \"firstName\":\"John\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" },\r\n"
                + "        { \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6513\", \"email\":\"drk@email.com\" },\r\n"
                + "        { \"firstName\":\"Tenley\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"tenz@email.com\" },\r\n"
                + "        { \"firstName\":\"Roger\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" },\r\n"
                + "        { \"firstName\":\"Felicia\", \"lastName\":\"Boyd\", \"address\":\"1509 Culver St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6544\", \"email\":\"jaboyd@email.com\" },\r\n"
                + "        { \"firstName\":\"Jonanathan\", \"lastName\":\"Marrack\", \"address\":\"29 15th St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6513\", \"email\":\"drk@email.com\" },\r\n"
                + "        { \"firstName\":\"Tessa\", \"lastName\":\"Carman\", \"address\":\"834 Binoc Ave\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"tenz@email.com\" },\r\n"
                + "        { \"firstName\":\"Peter\", \"lastName\":\"Duncan\", \"address\":\"644 Gershwin Cir\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6512\", \"email\":\"jaboyd@email.com\" },\r\n"
                + "        { \"firstName\":\"Foster\", \"lastName\":\"Shepard\", \"address\":\"748 Townings Dr\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6544\", \"email\":\"jaboyd@email.com\" },\r\n"
                + "        { \"firstName\":\"Tony\", \"lastName\":\"Cooper\", \"address\":\"112 Steppes Pl\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6874\", \"email\":\"tcoop@ymail.com\" },\r\n"
                + "        { \"firstName\":\"Lily\", \"lastName\":\"Cooper\", \"address\":\"489 Manchester St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-9845\", \"email\":\"lily@email.com\" },\r\n"
                + "        { \"firstName\":\"Sophia\", \"lastName\":\"Zemicks\", \"address\":\"892 Downing Ct\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-7878\", \"email\":\"soph@email.com\" },\r\n"
                + "        { \"firstName\":\"Warren\", \"lastName\":\"Zemicks\", \"address\":\"892 Downing Ct\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-7512\", \"email\":\"ward@email.com\" },\r\n"
                + "        { \"firstName\":\"Zach\", \"lastName\":\"Zemicks\", \"address\":\"892 Downing Ct\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-7512\", \"email\":\"zarc@email.com\" },\r\n"
                + "        { \"firstName\":\"Reginold\", \"lastName\":\"Walker\", \"address\":\"908 73rd St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-8547\", \"email\":\"reg@email.com\" },\r\n"
                + "        { \"firstName\":\"Jamie\", \"lastName\":\"Peters\", \"address\":\"908 73rd St\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-7462\", \"email\":\"jpeter@email.com\" },\r\n"
                + "        { \"firstName\":\"Ron\", \"lastName\":\"Peters\", \"address\":\"112 Steppes Pl\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-8888\", \"email\":\"jpeter@email.com\" },\r\n"
                + "        { \"firstName\":\"Allison\", \"lastName\":\"Boyd\", \"address\":\"112 Steppes Pl\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-9888\", \"email\":\"aly@imail.com\" },\r\n"
                + "        { \"firstName\":\"Brian\", \"lastName\":\"Stelzer\", \"address\":\"947 E. Rose Dr\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-7784\", \"email\":\"bstel@email.com\" },\r\n"
                + "        { \"firstName\":\"Shawna\", \"lastName\":\"Stelzer\", \"address\":\"947 E. Rose Dr\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-7784\", \"email\":\"ssanw@email.com\" },\r\n"
                + "        { \"firstName\":\"Kendrik\", \"lastName\":\"Stelzer\", \"address\":\"947 E. Rose Dr\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-7784\", \"email\":\"bstel@email.com\" },\r\n"
                + "        { \"firstName\":\"Clive\", \"lastName\":\"Ferguson\", \"address\":\"748 Townings Dr\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-6741\", \"email\":\"clivfd@ymail.com\" },\r\n"
                + "        { \"firstName\":\"Eric\", \"lastName\":\"Cadigan\", \"address\":\"951 LoneTree Rd\", \"city\":\"Culver\", \"zip\":\"97451\", \"phone\":\"841-874-7458\", \"email\":\"gramps@email.com\" }\r\n"
                + "    ], \r\n" + "    \"firestations\": [\r\n" + "    { \"address\":\"1509 Culver St\", \"station\":\"3\" },\r\n"
                + "        { \"address\":\"29 15th St\", \"station\":\"2\" },\r\n" + "        { \"address\":\"834 Binoc Ave\", \"station\":\"3\" },\r\n"
                + "        { \"address\":\"644 Gershwin Cir\", \"station\":\"1\" },\r\n" + "        { \"address\":\"748 Townings Dr\", \"station\":\"3\" },\r\n"
                + "        { \"address\":\"112 Steppes Pl\", \"station\":\"3\" },\r\n" + "        { \"address\":\"489 Manchester St\", \"station\":\"4\" },\r\n"
                + "        { \"address\":\"892 Downing Ct\", \"station\":\"2\" },\r\n" + "        { \"address\":\"908 73rd St\", \"station\":\"1\" },\r\n"
                + "        { \"address\":\"112 Steppes Pl\", \"station\":\"4\" },\r\n" + "        { \"address\":\"947 E. Rose Dr\", \"station\":\"1\" },\r\n"
                + "        { \"address\":\"748 Townings Dr\", \"station\":\"3\" },\r\n" + "        { \"address\":\"951 LoneTree Rd\", \"station\":\"2\" }\r\n"
                + "    ],\r\n" + "    \"medicalrecords\": [\r\n"
                + "        { \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] },\r\n"
                + "        { \"firstName\":\"Jacob\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1989\", \"medications\":[\"pharmacol:5000mg\", \"terazine:10mg\", \"noznazol:250mg\"], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Tenley\", \"lastName\":\"Boyd\", \"birthdate\":\"02/18/2012\", \"medications\":[], \"allergies\":[\"peanut\"] },\r\n"
                + "        { \"firstName\":\"Roger\", \"lastName\":\"Boyd\", \"birthdate\":\"09/06/2017\", \"medications\":[], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Felicia\", \"lastName\":\"Boyd\",\"birthdate\":\"01/08/1986\", \"medications\":[\"tetracyclaz:650mg\"], \"allergies\":[\"xilliathal\"] },\r\n"
                + "        { \"firstName\":\"Jonanathan\", \"lastName\":\"Marrack\", \"birthdate\":\"01/03/1989\", \"medications\":[], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Tessa\", \"lastName\":\"Carman\", \"birthdate\":\"02/18/2012\", \"medications\":[], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Peter\", \"lastName\":\"Duncan\", \"birthdate\":\"09/06/2000\", \"medications\":[], \"allergies\":[\"shellfish\"] },\r\n"
                + "        { \"firstName\":\"Foster\", \"lastName\":\"Shepard\", \"birthdate\":\"01/08/1980\", \"medications\":[], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Tony\", \"lastName\":\"Cooper\", \"birthdate\":\"03/06/1994\", \"medications\":[\"hydrapermazol:300mg\", \"dodoxadin:30mg\"], \"allergies\":[\"shellfish\"] },\r\n"
                + "        { \"firstName\":\"Lily\", \"lastName\":\"Cooper\", \"birthdate\":\"03/06/1994\", \"medications\":[], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Sophia\", \"lastName\":\"Zemicks\", \"birthdate\":\"03/06/1988\", \"medications\":[\"aznol:60mg\", \"hydrapermazol:900mg\", \"pharmacol:5000mg\", \"terazine:500mg\"], \"allergies\":[\"peanut\", \"shellfish\", \"aznol\"] },\r\n"
                + "        { \"firstName\":\"Warren\", \"lastName\":\"Zemicks\", \"birthdate\":\"03/06/1985\", \"medications\":[], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Zach\", \"lastName\":\"Zemicks\", \"birthdate\":\"03/06/2017\", \"medications\":[], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Reginold\", \"lastName\":\"Walker\", \"birthdate\":\"08/30/1979\", \"medications\":[\"thradox:700mg\"], \"allergies\":[\"illisoxian\"] },\r\n"
                + "        { \"firstName\":\"Jamie\", \"lastName\":\"Peters\", \"birthdate\":\"03/06/1982\", \"medications\":[], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Ron\", \"lastName\":\"Peters\", \"birthdate\":\"04/06/1965\", \"medications\":[], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Allison\", \"lastName\":\"Boyd\", \"birthdate\":\"03/15/1965\", \"medications\":[\"aznol:200mg\"], \"allergies\":[\"nillacilan\"] },\r\n"
                + "        { \"firstName\":\"Brian\", \"lastName\":\"Stelzer\", \"birthdate\":\"12/06/1975\", \"medications\":[\"ibupurin:200mg\", \"hydrapermazol:400mg\"], \"allergies\":[\"nillacilan\"] },\r\n"
                + "        { \"firstName\":\"Shawna\", \"lastName\":\"Stelzer\", \"birthdate\":\"07/08/1980\", \"medications\":[], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Kendrik\", \"lastName\":\"Stelzer\", \"birthdate\":\"03/06/2014\", \"medications\":[\"noxidian:100mg\", \"pharmacol:2500mg\"], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Clive\", \"lastName\":\"Ferguson\", \"birthdate\":\"03/06/1994\", \"medications\":[], \"allergies\":[] },\r\n"
                + "        { \"firstName\":\"Eric\", \"lastName\":\"Cadigan\", \"birthdate\":\"08/06/1945\", \"medications\":[\"tradoxidine:400mg\"], \"allergies\":[] }\r\n"
                + "        ] \r\n" + "}";

        final JsonNode node = objectMapper.readTree(json);
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

    private void getAddressesFromStation(int stationNumber, ArrayList<String> addresses, JsonNode e) {
        if (e.get("station").asInt() == stationNumber) {
            addresses.add(e.get("address").asText());
        }
    }
}