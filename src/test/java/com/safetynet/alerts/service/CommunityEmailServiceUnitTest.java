package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.safetynet.alerts.dto.resource.CommunityEmailDTO;
import com.safetynet.alerts.repo.DataRepository;
import com.safetynet.alerts.util.JsonDataRepositoryTestUtil;
import com.safetynet.alerts.util.TestRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class CommunityEmailServiceUnitTest {

    CommunityEmailService service;

    DataRepository repo = new JsonDataRepositoryTestUtil();

    @BeforeEach
    public void setup() throws Exception {
        TestRepository.jsonDataInitializer();
        service = new CommunityEmailService(repo);
    }

    @Test
    public void givenExistingCity_whenGetEmailAddressesFromCity_thenDTOIsAsExpected() {
        // given
        final String city = "Culver";
        final CommunityEmailDTO expectedDTO = new CommunityEmailDTO();
        final List<String> emails = new ArrayList<>();
        emails.add("jaboyd@email.com");
        emails.add("lichild@email.com");
        expectedDTO.setEmailAddresses(emails);
        // when
        final CommunityEmailDTO actualDTO = service.getEmailAddressesFromCity(city);
        // then
        assertThat(actualDTO).usingRecursiveComparison().isEqualTo(expectedDTO);
    }

    @Test
    public void givenUnknownCity_whenGetEmailAddressesFromCity_thenDTOIsEmpty() {
        // given
        final String unknownCity = "an unknown city";
        // when
        final CommunityEmailDTO actualDTO = service.getEmailAddressesFromCity(unknownCity);
        // then
        assertThat(actualDTO.getEmailAddresses()).isEmpty();
    }
}
