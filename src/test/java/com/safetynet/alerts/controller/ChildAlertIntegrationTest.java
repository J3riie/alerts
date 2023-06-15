package com.safetynet.alerts.controller;

import static org.mockito.ArgumentMatchers.anyString;
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

import com.safetynet.alerts.controller.ChildAlertController;
import com.safetynet.alerts.service.ChildAlertService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@WebMvcTest(ChildAlertController.class)
public class ChildAlertIntegrationTest {

    @MockBean
    ChildAlertService service;

    @Autowired
    private MockMvc mockMvc;

    private static NodeConstructorTestUtil nodeConstructor = new NodeConstructorTestUtil();

    @Test
    public void givenKnownAddress_whenGetChildrenListAtAddress_thenListIsReturned() throws Exception {
        // given
        when(service.getChildrenAtAddress(anyString())).thenReturn(nodeConstructor.createInitialisedChildAlertDTO());
        // when then
        this.mockMvc.perform(get("/childAlert?address=anything").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.children").isNotEmpty());
    }

    @Test
    public void givenUnknownAddress_whenGetChildrenListAtAddress_thenEmptyListIsReturned() throws Exception {
        // given
        when(service.getChildrenAtAddress(anyString())).thenReturn(nodeConstructor.createEmptyChildAlertDTO());
        // when then
        this.mockMvc.perform(get("/childAlert?address=anything").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.children").isEmpty());
    }

}
