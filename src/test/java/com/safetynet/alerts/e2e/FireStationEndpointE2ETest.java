package com.safetynet.alerts.e2e;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class FireStationEndpointE2ETest {

    @Value(value = "${local.server.port}")
    private int port;

    @MockBean
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
    public void givenValidParameters_whenModifyFireStation_thenResponseStatusIsNoContent() {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/firestation?address=1509 Culver St&station=1",
                HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
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
    public void givenValidStationNumber_whenDeleteFireStation_thenResponseStatusIsOk() {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/firestation?station=1", HttpMethod.DELETE, request,
                Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
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
