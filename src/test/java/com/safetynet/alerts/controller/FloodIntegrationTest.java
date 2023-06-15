package com.safetynet.alerts.controller;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.safetynet.alerts.controller.FloodController;
import com.safetynet.alerts.service.FloodService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@WebMvcTest(FloodController.class)
public class FloodIntegrationTest {

    @MockBean
    FloodService service;

    @Autowired
    private MockMvc mockMvc;

    private static NodeConstructorTestUtil nodeConstructor = new NodeConstructorTestUtil();

    @Test
    public void givenKnownStationNumbers_whenGetInfoFromPersonsCoveredByStations_thenListIsReturned() throws Exception {
        // given
        when(service.getInfoFromPersonsCoveredByStations(anyList())).thenReturn(nodeConstructor.createInitialisedFloodDTO());
        // when then
        this.mockMvc.perform(get("/flood/stations?stations=1,2,3").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.covered_houses").isNotEmpty());
    }

    @Test
    public void givenUnknownStationNumbers_whenGetInfoFromPersonsCoveredByStations_thenEmptyListIsReturned() throws Exception {
        // given
        when(service.getInfoFromPersonsCoveredByStations(anyList())).thenReturn(nodeConstructor.createEmptyFloodDTO());
        // when then
        this.mockMvc.perform(get("/flood/stations?stations=0").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.covered_houses").isEmpty());
    }
}
