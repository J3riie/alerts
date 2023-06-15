package com.safetynet.alerts.controller;

import static org.mockito.ArgumentMatchers.anyInt;
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

import com.safetynet.alerts.controller.PhoneAlertController;
import com.safetynet.alerts.service.PhoneAlertService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@WebMvcTest(PhoneAlertController.class)
public class PhoneAlertIntegrationTest {

    @MockBean
    PhoneAlertService service;

    @Autowired
    private MockMvc mockMvc;

    private static NodeConstructorTestUtil nodeConstructor = new NodeConstructorTestUtil();

    @Test
    public void givenKnownStationNumber_whenGetPhoneNumbersFromPersonsCoveredByStation_thenListIsReturned() throws Exception {
        // given
        when(service.getPhoneNumbersFromPersonsCoveredByStation(anyInt())).thenReturn(nodeConstructor.createInitialisedPhoneAlertDTO());
        // when then
        this.mockMvc.perform(get("/phoneAlert?firestation=1").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.phones").isNotEmpty());
    }

    @Test
    public void givenUnknownStationNumber_whenGetPhoneNumbersFromPersonsCoveredByStation_thenEmptyListIsReturned() throws Exception {
        // given
        when(service.getPhoneNumbersFromPersonsCoveredByStation(anyInt())).thenReturn(nodeConstructor.createEmptyPhoneAlertDTO());
        // when then
        this.mockMvc.perform(get("/phoneAlert?firestation=10").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.phones").isEmpty());
    }
}
