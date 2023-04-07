package com.safetynet.alerts.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.dto.resource.PersonInfoDTO;
import com.safetynet.alerts.service.resource.PersonInfoService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class PersonInfoIntegrationTest {

    @Autowired
    PersonInfoService service;

    @Autowired
    PersonInfo personInfo;

    private HttpHeaders httpHeaders;

    @BeforeAll
    public void setup() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void givenKnownPersonNames_whenGetPersonsWithNames_thenListIsReturned() {
        // given
        final String firstName = "John";
        final String lastName = "Boyd";
        // when
        final ResponseEntity<PersonInfoDTO> response = personInfo.getPersonsWithNames(firstName, lastName);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getPersons()).anyMatch(c -> c.getAddress().matches("1509 Culver St"));
        assertThat(response.getBody().getPersons()).anyMatch(c -> c.getMedicalHistory().getAllergies().contains("nillacilan"));
    }

    @Test
    public void givenUnknownPersonNames_whenGetPersonsWithNames_thenEmptyListIsReturned() {
        // given
        final String firstName = "an unknown name";
        final String lastName = "another one";
        // when
        final ResponseEntity<PersonInfoDTO> response = personInfo.getPersonsWithNames(firstName, lastName);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(response.getBody().getPersons().isEmpty());
    }
}
