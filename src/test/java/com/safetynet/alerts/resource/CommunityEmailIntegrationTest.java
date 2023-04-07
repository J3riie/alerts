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

import com.safetynet.alerts.dto.resource.CommunityEmailDTO;
import com.safetynet.alerts.service.resource.CommunityEmailService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class CommunityEmailIntegrationTest {

    @Autowired
    CommunityEmailService service;

    @Autowired
    CommunityEmail communityEmail;

    private HttpHeaders httpHeaders;

    @BeforeAll
    public void setup() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void givenKnownCity_whenGetEmailsFromCity_thenEmailListIsReturned() {
        // given
        final String city = "Culver";
        // when
        final ResponseEntity<CommunityEmailDTO> response = communityEmail.getEmailsFromCity(city);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmailAddresses()).anyMatch(c -> c.contains("jaboyd@email.com"));
    }

    @Test
    public void givenUnknownCity_whenGetEmailsFromCity_thenEmptyListIsReturned() {
        // given
        final String city = "an unknown city";
        // when
        final ResponseEntity<CommunityEmailDTO> response = communityEmail.getEmailsFromCity(city);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(response.getBody().getEmailAddresses().isEmpty());
    }
}
