package com.safetynet.alerts.endpoint;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.dto.response.APIResponse;
import com.safetynet.alerts.service.endpoint.FireStationService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@ExtendWith(MockitoExtension.class)
public class FireStationEndpointIntegrationTest {

    @MockBean
    FireStationService fireStationService;

    NodeConstructorTestUtil nodeConstructor;

    FireStationEndpoint fireStationEndpoint;

    @BeforeEach
    void initService() {
        nodeConstructor = new NodeConstructorTestUtil();
        fireStationEndpoint = new FireStationEndpoint();
    }

    @Test
    public void givenValidFireStation_whenAddFireStation_thenResponseStatusIs201() {
        // given
        final FireStationsDTO validFireStation = nodeConstructor.createValidFireStationsDTO();
        // when
        final APIResponse<Void> response = fireStationEndpoint.addFireStation(validFireStation);
        // then
        assertEquals(response.getStatusCode(), HttpStatus.CREATED.value());
    }

    @Test
    public void givenFireStationWithInvalidParameters_whenAddFireStation_thenReponseStatusIs400() {
        // given
        final FireStationsDTO validFireStation = nodeConstructor.createInvalidFireStationsDTO();
        // when
        final APIResponse<Void> response = fireStationEndpoint.addFireStation(validFireStation);
        // then
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST.value());
    }
}
