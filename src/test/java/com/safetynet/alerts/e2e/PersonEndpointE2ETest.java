package com.safetynet.alerts.e2e;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.Optional;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.dto.node.PersonsDTO;
import com.safetynet.alerts.endpoint.PersonEndpoint;
import com.safetynet.alerts.service.PersonService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class PersonEndpointE2ETest {

    @Value(value = "${local.server.port}")
    private int port;

    @MockBean
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
    public void givenValidPersonAsJson_whenAddPerson_thenResponseStatusIsCreated() throws Exception {
        // given
        doNothing().when(service).addPerson(any());
        final String payload = nodeConstructor.createValidPersonAsJson();
        final HttpEntity<String> request = new HttpEntity<String>(payload, httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.postForEntity("http://localhost:" + port + "/api/person", request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
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
    public void givenValidParameters_whenModifyPerson_thenResponseStatusIsNoContent() throws Exception {
        // given
        when(service.modifyPerson(anyString(), anyString(), anyString(), anyString(), anyInt(), any(), any())).thenReturn(new PersonsDTO());
        final HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate
                .exchange("http://localhost:" + port + "/api/person?firstName=John&lastName=Boyd&address=new address", HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void givenInvalidParameters_whenModifyPerson_thenResponseStatusIsNotFound() throws Exception {
        // given
        final String payload = nodeConstructor.createInvalidPersonAsJson();
        final HttpEntity<String> request = new HttpEntity<String>(payload, httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate
                .exchange("http://localhost:" + port + "/api/person?firstName=Robin&lastName=Hugues&address=new address", HttpMethod.PUT, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void givenValidParameters_whenDeletePerson_thenResponseStatusIsOk() throws Exception {
        // given
        when(service.deletePerson(anyString(), anyString())).thenReturn(Optional.empty());
        final HttpEntity<String> request = new HttpEntity<String>(httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/person?firstName=John&lastName=Boyd",
                HttpMethod.DELETE, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void givenInvalidParameters_whenDeletePerson_thenResponseStatusIsNotFound() throws Exception {
        // given
        final String payload = nodeConstructor.createInvalidPersonAsJson();
        final HttpEntity<String> request = new HttpEntity<String>(payload, httpHeaders);
        // when
        final ResponseEntity<Void> response = this.restTemplate.exchange("http://localhost:" + port + "/api/person?firstName=Robin&lastName=Hugues",
                HttpMethod.DELETE, request, Void.class);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
