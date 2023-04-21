package com.safetynet.alerts.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    //
    // @Test
    // public void givenValidCity_whenGetEmailAddressesFromCity_thenEmailListIsReturned() {
    // // given
    // final String address = "Culver";
    // final List<String> expectedEmails = new ArrayList<>();
    // expectedEmails.add("jaboyd@email.com");
    // // when
    // final List<String> actualEmails = service.getEmailAddressesFromCity(address);
    // // then
    // assertThat(actualEmails).usingRecursiveComparison().isEqualTo(expectedEmails);
    //
    // }

    @Test
    public void givenUnknownCity_whenGetEmailAddressesFromCity_thenEmptyListIsReturned() {
        // given
        final String unknownCity = "an unknown city";
        // when
        final List<String> emails = service.getEmailAddressesFromCity(unknownCity);
        // then
        assertThat(emails).isEmpty();

    }
}
