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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.endpoint.MedicalRecordEndpoint;
import com.safetynet.alerts.service.MedicalRecordService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MedicalRecordEndpointE2ETest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    MedicalRecordService service;

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
    @Order(1)
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
    @Order(2)
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
    @Order(3)
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
    @Order(4)
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
    @Order(5)
    public void givenValidParameters_whenDeleteMedicalRecord_thenResponseStatusIsOk() throws Exception {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/medicalRecord?firstName=John&lastName=Boyd",
                HttpMethod.DELETE, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(6)
    public void givenInvalidParameters_whenDeleteMedicalRecord_thenResponseStatusIsNotFound() throws Exception {
        // given
        final HttpEntity<Void> request = new HttpEntity<Void>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/medicalRecordfirstName=Robin&lastName=Hugues",
                HttpMethod.DELETE, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
