package com.safetynet.alerts.resource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

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

import com.safetynet.alerts.dto.resource.FloodDTO;
import com.safetynet.alerts.service.resource.FloodService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class FloodIntegrationTest {

    @Autowired
    FloodService service;

    @Autowired
    Flood flood;

    private HttpHeaders httpHeaders;

    @BeforeAll
    public void setup() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void givenKnownStationNumbers_whenGetInfoFromPersonsCoveredByStations_thenListIsReturned() {
        // given
        final List<Integer> stations = Arrays.asList(1, 2);
        // when
        final ResponseEntity<FloodDTO> response = flood.getInfoFromPersonsCoveredByStations(stations);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCoveredHouses()).anyMatch(c -> c.getAddress().matches("644 Gershwin Cir"));
        assertThat(response.getBody().getCoveredHouses()).hasSize(6);
    }

    @Test
    public void givenUnknownStationNumbers_whenGetInfoFromPersonsCoveredByStations_thenEmptyListIsReturned() {
        // given
        final List<Integer> stations = Arrays.asList(6, 10);
        // when
        final ResponseEntity<FloodDTO> response = flood.getInfoFromPersonsCoveredByStations(stations);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(response.getBody().getCoveredHouses().isEmpty());
    }
}
