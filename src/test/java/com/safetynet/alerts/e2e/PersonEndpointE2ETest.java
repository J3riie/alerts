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
import com.safetynet.alerts.endpoint.PersonEndpoint;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PersonEndpointE2ETest {

    @Value(value = "${local.server.port}")
    private int port;

    @Autowired
    PersonService service;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PersonEndpoint personEndpoint;

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
    public void givenValidPersonAsJson_whenAddPerson_thenResponseStatusIsCreated() throws Exception {
        // given
        final String payload = nodeConstructor.createValidPersonAsJson();
        final HttpEntity<String> request = new HttpEntity<String>(payload, httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/person", request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @Order(2)
    public void givenInvalidPersonAsJson_whenAddPerson_thenResponseStatusIsBadRequest() throws Exception {
        // given
        final String payload = nodeConstructor.createInvalidPersonAsJson();
        final HttpEntity<String> request = new HttpEntity<String>(payload, httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/person", request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @Order(3)
    public void givenValidParameters_whenModifyPerson_thenResponseStatusIsNoContent() throws Exception {
        // given
        final HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate
                .exchange("http://localhost:" + port + "/api/person?firstName=John&lastName=Boyd&address=new address", HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Order(4)
    public void givenInvalidParameters_whenModifyPerson_thenResponseStatusIsNotFound() throws Exception {
        // given
        final String payload = nodeConstructor.createInvalidPersonAsJson();
        final HttpEntity<String> request = new HttpEntity<String>(payload, httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate
                .exchange("http://localhost:" + port + "/api/person?firstName=Invalid&lastName=Name&address=new address", HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @Order(5)
    public void givenValidParameters_whenDeletePerson_thenResponseStatusIsOk() throws Exception {
        // given
        final HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/person?firstName=Little&lastName=Child",
                HttpMethod.DELETE, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @Order(6)
    public void givenInvalidParameters_whenDeletePerson_thenResponseStatusIsNotFound() throws Exception {
        // given
        final String payload = nodeConstructor.createInvalidPersonAsJson();
        final HttpEntity<String> request = new HttpEntity<String>(payload, httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/person?firstName=Invalid&lastName=Name",
                HttpMethod.DELETE, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
