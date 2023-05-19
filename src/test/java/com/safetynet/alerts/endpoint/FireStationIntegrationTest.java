package com.safetynet.alerts.endpoint;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alerts.dto.node.FireStationsDTO;
import com.safetynet.alerts.service.FireStationService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@WebMvcTest(FireStationEndpoint.class)
public class FireStationIntegrationTest {

    @MockBean
    FireStationService service;

    @Autowired
    private MockMvc mockMvc;

    private static NodeConstructorTestUtil nodeConstructor = new NodeConstructorTestUtil();

    @Test
    public void givenKnownStationNumber_whenGetPersonsCoveredByStation_thenListIsReturned() throws Exception {
        // given
        when(service.getPersonsCoveredByStation(anyInt())).thenReturn(nodeConstructor.createInitialisedFireStationDTO());
        // when then
        this.mockMvc.perform(get("/firestation?stationNumber=1").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.person").isNotEmpty());
    }

    @Test
    public void givenUnknownStationNumber_whenGetPersonsCoveredByStation_thenEmptyListIsReturned() throws Exception {
        // given
        when(service.getPersonsCoveredByStation(anyInt())).thenReturn(nodeConstructor.createEmptyFireStationDTO());
        // when then
        this.mockMvc.perform(get("/firestation?stationNumber=10").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.person").isEmpty());
    }

    @Test
    public void givenValidFireStationAsJson_whenAddFireStation_thenResponseStatusIsCreated() throws Exception {
        // given
        doNothing().when(service).addFireStation(any());
        // when then
        this.mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON_VALUE).content(nodeConstructor.createValidFireStationAsJson()))
                .andExpect(status().isCreated());
    }

    @Test
    public void givenInvalidFireStationAsJson_whenAddFireStation_thenReponseStatusIsBadRequest() throws Exception {
        // given
        doNothing().when(service).addFireStation(any());
        // when then
        this.mockMvc.perform(post("/firestation").contentType(MediaType.APPLICATION_JSON_VALUE).content(nodeConstructor.createInvalidFireStationAsJson()))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidParameters_whenModifyFireStation_thenResponseStatusIsNoContent() throws Exception {
        // given
        when(service.modifyFireStation(anyString(), anyInt())).thenReturn(new FireStationsDTO());
        // when then
        this.mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON_VALUE).param("address", "1509 Culver St").param("station", "1"))
        .andExpect(status().isNoContent());
    }

    @Test
    public void givenMissingParameter_whenModifyFireStation_thenResponseStatusIsBadRequest() throws Exception {
        // given
        when(service.modifyFireStation(anyString(), anyInt())).thenReturn(new FireStationsDTO());
        // when then
        this.mockMvc.perform(put("/firestation").contentType(MediaType.APPLICATION_JSON_VALUE).param("address", "1509 Culver St")).andExpect(status().isBadRequest());
    }

    @Test
    public void givenValidAddress_whenDeleteFireStation_thenResponseStatusIsOk() throws Exception {
        // given
        when(service.deleteFireStation(anyString())).thenReturn(Optional.of(new FireStationsDTO()));
        // when then
        this.mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON_VALUE).param("address", "1509 Culver St")).andExpect(status().isOk());
    }

    @Test
    public void givenValidStationNumber_whenDeleteFireStation_thenResponseStatusIsOk() throws Exception {
        // given
        when(service.deleteFireStation(anyInt())).thenReturn(true);
        // when then
        this.mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON_VALUE).param("station", "1")).andExpect(status().isOk());
    }

    @Test
    public void givenInvalidParameters_whenDeleteFireStation_thenResponseStatusIsBadRequest() throws Exception {
        // given when then
        this.mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON_VALUE).param("address", "1509 Culver St").param("station", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenUnknownAddressParameter_whenDeleteFireStation_thenResponseStatusIsNotFound() throws Exception {
        // given
        when(service.deleteFireStation(anyString())).thenReturn(Optional.empty());
        // when then
        this.mockMvc.perform(delete("/firestation").contentType(MediaType.APPLICATION_JSON_VALUE).param("address", "unknown")).andExpect(status().isNotFound());
    }
}
