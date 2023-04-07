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

import com.safetynet.alerts.dto.resource.FireStationDTO;
import com.safetynet.alerts.service.resource.FireService;

import jakarta.validation.Validator;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class FireStationIntegrationTest {

    @Autowired
    FireService service;

    @Autowired
    FireStation fireStation;

    @Autowired
    Validator validator;

    private HttpHeaders httpHeaders;

    @BeforeAll
    public void setup() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void givenKnownStationNumber_whenGetPersonsCoveredByStation_thenListIsReturned() {
        // given
        final int stationNumber = 1;
        // when
        final ResponseEntity<FireStationDTO> response = fireStation.getPersonsCoveredByStation(stationNumber);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCoveredPersons()).anyMatch(c -> c.getFirstName().matches("Peter"));
        assertThat(response.getBody().getNumberOfAdults()).isEqualTo(5);
    }

    @Test
    public void givenUnknownStationNumber_whenGetPersonsCoveredByStation_thenEmptyListIsReturned() {
        // given
        final int stationNumber = 10;
        // when
        final ResponseEntity<FireStationDTO> response = fireStation.getPersonsCoveredByStation(stationNumber);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(response.getBody().getCoveredPersons().isEmpty());
    }

    @Test
    public void givenInvalidStationNumber_whenGetPersonsCoveredByStation_thenExceptionIsThrown() {
        // given
        final int stationNumber = 0;
        // when
        // TODO : find a way to check the validation throwing error
        // then
        // TODO : assert the error is thrown
    }

}
