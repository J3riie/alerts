package com.safetynet.alerts.endpoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.dto.resource.FireStationDTO;
import com.safetynet.alerts.service.FireStationService;

import jakarta.validation.Validator;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class FireStationIntegrationTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Mock
    FireStationService service;

    @Autowired
    private TestRestTemplate restTemplate;

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
        final HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        // when
        final ResponseEntity<FireStationDTO> response = restTemplate.exchange("http://localhost:" + port + "/api/firestation?stationNumber=1", HttpMethod.GET,
                request, FireStationDTO.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCoveredPersons()).anyMatch(c -> c.getFirstName().matches("Peter"));
        assertThat(response.getBody().getNumberOfAdults()).isEqualTo(5);
    }

    @Test
    public void givenUnknownStationNumber_whenGetPersonsCoveredByStation_thenEmptyListIsReturned() {
        // given
        final HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        // when
        final ResponseEntity<FireStationDTO> response = restTemplate.exchange("http://localhost:" + port + "/api/firestation?stationNumber=10", HttpMethod.GET,
                request, FireStationDTO.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(response.getBody().getCoveredPersons().isEmpty());
    }

}
