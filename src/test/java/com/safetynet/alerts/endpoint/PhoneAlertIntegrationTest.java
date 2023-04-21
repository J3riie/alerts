package com.safetynet.alerts.endpoint;

import static org.assertj.core.api.Assertions.assertThat;

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

import com.safetynet.alerts.dto.resource.PhoneAlertDTO;
import com.safetynet.alerts.endpoint.PhoneAlertEndpoint;
import com.safetynet.alerts.service.PhoneAlertService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class PhoneAlertIntegrationTest {

    @Autowired
    PhoneAlertService service;

    @Autowired
    PhoneAlertEndpoint phoneAlert;

    private HttpHeaders httpHeaders;

    @BeforeAll
    public void setup() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void givenKnownStationNumber_whenGetPhoneNumbersFromPersonsCoveredByStation_thenListIsReturned() {
        // given
        final int stationNumber = 1;
        // when
        final ResponseEntity<PhoneAlertDTO> response = phoneAlert.getPhoneNumbersFromPersonsCoveredByStation(stationNumber);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getPhones()).anyMatch(c -> c.matches("841-874-6512"));
    }

    @Test
    public void givenUnknownStationNumber_whenGetPhoneNumbersFromPersonsCoveredByStation_thenEmptyListIsReturned() {
        // given
        final int stationNumber = 10;
        // when
        final ResponseEntity<PhoneAlertDTO> response = phoneAlert.getPhoneNumbersFromPersonsCoveredByStation(stationNumber);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getPhones()).isEmpty();
    }
}
