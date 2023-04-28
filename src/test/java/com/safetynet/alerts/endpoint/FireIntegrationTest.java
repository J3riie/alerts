package com.safetynet.alerts.endpoint;

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

import com.safetynet.alerts.service.FireService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@WebMvcTest(FireEndpoint.class)
public class FireIntegrationTest {

    @MockBean
    FireService service;

    @Autowired
    private MockMvc mockMvc;

    private static NodeConstructorTestUtil nodeConstructor = new NodeConstructorTestUtil();

    @Test
    public void givenKnownAddress_whenGetPersonsInfoAtAddress_thenListIsReturned() throws Exception {
        // given
        when(service.getPersonsInfoAtAddress(anyString())).thenReturn(nodeConstructor.createInitialisedFireDTO());
        // when then
        this.mockMvc.perform(get("/fire?address=anything").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.inhabitants").isNotEmpty());
    }

    @Test
    public void givenUnknownAddress_whenGetPersonsInfoAtAddress_thenEmptyListIsReturned() throws Exception {
        // given
        when(service.getPersonsInfoAtAddress(anyString())).thenReturn(nodeConstructor.createEmptyFireDTO());
        // when then
        this.mockMvc.perform(get("/fire?address=anything").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.inhabitants").isEmpty());
    }

}
