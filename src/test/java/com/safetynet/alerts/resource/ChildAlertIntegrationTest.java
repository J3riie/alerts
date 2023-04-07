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

import com.safetynet.alerts.dto.resource.ChildAlertDTO;
import com.safetynet.alerts.service.resource.ChildAlertService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class ChildAlertIntegrationTest {

    @Autowired
    ChildAlertService service;

    @Autowired
    ChildAlert childAlert;

    private HttpHeaders httpHeaders;

    @BeforeAll
    public void setup() {
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void givenKnownAddress_whenGetChildrenListAtAddress_thenListIsReturned() {
        // given
        final String address = "834 Binoc Ave";
        // when
        final ResponseEntity<ChildAlertDTO> response = childAlert.getChildrenListAtAddress(address);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getChildren()).anyMatch(c -> c.getFirstName().matches("Tessa"));
    }

    @Test
    public void givenUnknownAddress_whenGetChildrenListAtAddress_thenEmptyListIsReturned() {
        // given
        final String address = "an unknown address";
        // when
        final ResponseEntity<ChildAlertDTO> response = childAlert.getChildrenListAtAddress(address);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertTrue(response.getBody().getChildren().isEmpty());
    }

}
