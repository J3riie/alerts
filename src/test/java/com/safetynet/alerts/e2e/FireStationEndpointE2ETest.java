package com.safetynet.alerts.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
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
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FireStationEndpointE2ETest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    FireStationService service;

    @Autowired
    private TestRestTemplate restTemplate;

    private NodeConstructorTestUtil nodeConstructor;

    private HttpHeaders httpHeaders;

    @BeforeAll
    public void setup() {
        nodeConstructor = new NodeConstructorTestUtil();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    @Order(1)
    public void givenValidStationNumber_whenGetPersonsCoveredByStation_thenResponseStatusIsOk() throws Exception {
        // given
        final HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        // when
        final ResponseEntity<FireStationDTO> response = this.restTemplate.exchange("http://localhost:" + port + "/api/firestation?stationNumber=1",
                HttpMethod.GET, request, FireStationDTO.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getCoveredPersons()).hasSize(1);
        assertThat(response.getBody().getNumberOfAdults()).isEqualTo(1);
        assertThat(response.getBody().getNumberOfChildren()).isZero();
    }

    @Test
    @Order(2)
    public void givenValidFireStationAsJson_whenAddFireStation_thenResponseStatusIsCreated() throws Exception {
        // given
        final String payload = nodeConstructor.createValidFireStationAsJson();
        final HttpEntity<String> request = new HttpEntity<String>(payload, httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/firestation", request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(3)
    public void givenInvalidFireStationAsJson_whenAddFireStation_thenReponseStatusIsBadRequest() throws Exception {
        // given
        final String payload = nodeConstructor.createInvalidFireStationAsJson();
        final HttpEntity<String> request = new HttpEntity<String>(payload, httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/firestation", request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(4)
    public void givenValidParameters_whenModifyFireStation_thenResponseStatusIsNoContent() {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/firestation?address=1509 Culver St&station=2",
                HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Order(5)
    public void givenMissingParameter_whenModifyFireStation_thenResponseStatusIsBadRequest() {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/firestation?address=an address", HttpMethod.PUT,
                request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(6)
    public void givenUnknownAddressParameter_whenModifyFireStation_thenResponseStatusIsNotFound() {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/firestation?address=an address&station=1",
                HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(7)
    public void givenValidAddress_whenDeleteFireStation_thenResponseStatusIsOk() {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/firestation?address=1509 Culver St",
                HttpMethod.DELETE, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(8)
    public void givenValidStationNumber_whenDeleteFireStation_thenResponseStatusIsOk() {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/firestation?station=2", HttpMethod.DELETE, request,
                Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(9)
    public void givenInvalidParameters_whenDeleteFireStation_thenResponseStatusIsBadRequest() {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/firestation?address=1509 Culver St&station=1",
                HttpMethod.DELETE, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(10)
    public void givenUnknownAddressParameter_whenDeleteFireStation_thenResponseStatusIsNotFound() {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/firestation?address=an address", HttpMethod.DELETE,
                request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
