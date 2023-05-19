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

import com.safetynet.alerts.service.PersonInfoService;
import com.safetynet.alerts.util.NodeConstructorTestUtil;

@WebMvcTest(PersonInfoEndpoint.class)
public class PersonInfoIntegrationTest {

    @MockBean
    PersonInfoService service;

    @Autowired
    private MockMvc mockMvc;

    private static NodeConstructorTestUtil nodeConstructor = new NodeConstructorTestUtil();

    @Test
    public void givenKnownPersonNames_whenGetPersonsWithNames_thenListIsReturned() throws Exception {
        // given
        when(service.getPersonsWithNames(anyString(), anyString())).thenReturn(nodeConstructor.createInitialisedPersonInfoDTO());
        // when then
        this.mockMvc.perform(get("/personInfo?firstName=any&lastName=any").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.persons").isNotEmpty());
    }

    @Test
    public void givenUnknownPersonNames_whenGetPersonsWithNames_thenEmptyListIsReturned() throws Exception {
        // given
        when(service.getPersonsWithNames(anyString(), anyString())).thenReturn(nodeConstructor.createEmptyPersonInfoDTO());
        // when then
        this.mockMvc.perform(get("/personInfo?firstName=any&lastName=any").contentType(MediaType.APPLICATION_JSON_VALUE)).andExpectAll(status().isOk(),
                jsonPath("$.persons").isEmpty());
    }
}
