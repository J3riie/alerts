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

import com.safetynet.alerts.controller.CommunityEmailController;
import com.safetynet.alerts.service.CommunityEmailService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@WebMvcTest(CommunityEmailController.class)
public class CommunityEmailIntegrationTest {

    @MockBean
    CommunityEmailService service;

    @Autowired
    private MockMvc mockMvc;

    private static NodeConstructorTestUtil nodeConstructor = new NodeConstructorTestUtil();

    @Test
    public void givenKnownCity_whenGetEmailsFromCity_thenEmailListIsReturned() throws Exception {
        // given
        when(service.getEmailAddressesFromCity(anyString())).thenReturn(nodeConstructor.createInitialisedCommunityEmailDTO());
        // when then
        this.mockMvc.perform(get("/communityEmail?city=anything").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.emails").isNotEmpty());
    }

    @Test
    public void givenUnknownCity_whenGetEmailsFromCity_thenEmptyListIsReturned() throws Exception {
        // given
        when(service.getEmailAddressesFromCity(anyString())).thenReturn(nodeConstructor.createEmptyCommunityEmailDTO());
        // when then
        this.mockMvc.perform(get("/communityEmail?city=anything").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.emails").isEmpty());
    }
}
