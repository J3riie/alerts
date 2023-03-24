package com.safetynet.alerts.endpoint;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class MedicalRecordEndpointIntegrationTest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MedicalRecordEndpoint medicalRecordEndpoint;

    private NodeConstructorTestUtil nodeConstructor;

    private HttpHeaders httpHeaders;

    @BeforeAll
    public void setup() {
        nodeConstructor = new NodeConstructorTestUtil();
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void givenValidMedicalRecordAsJson_whenAddMedicalRecord_thenResponseStatusIsCreated() throws Exception {
        // given
        final String payload = nodeConstructor.createValidMedicalRecordAsJson();
        final HttpEntity<String> request = new HttpEntity<String>(payload, httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/medicalRecord", request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void givenInvalidMedicalRecordAsJson_whenAddMedicalRecord_thenResponseStatusIsBadRequest() throws Exception {
        // given
        final String payload = nodeConstructor.createInvalidMedicalRecordAsJson();
        final HttpEntity<String> request = new HttpEntity<String>(payload, httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/medicalRecord", request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void givenValidParameters_whenModifyMedicalRecord_thenResponseStatusIsNoContent() throws Exception {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate
                .exchange("http://localhost:" + port + "/api/medicalRecord?firstName=John&lastName=Boyd&allergies=pollen", HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void givenInvalidParameters_whenModifyMedicalRecord_thenResponseStatusIsNotFound() throws Exception {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange(
                "http://localhost:" + port + "/api/medicalRecordfirstName=Robin&lastName=Hugues&allergies=pollen", HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenValidParameters_whenDeleteMedicalRecord_thenResponseStatusIsOk() throws Exception {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/medicalRecord?firstName=John&lastName=Boyd",
                HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void givenInvalidParameters_whenDeleteMedicalRecord_thenResponseStatusIsNotFound() throws Exception {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/medicalRecordfirstName=Robin&lastName=Hugues",
                HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
