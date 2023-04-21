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

import com.safetynet.alerts.dto.response.ChildResponse;
import com.safetynet.alerts.dto.response.FamilyResponse;
import com.safetynet.alerts.repo.DataRepository;
import com.safetynet.alerts.service.ChildAlertService;
import com.safetynet.alerts.util.JsonDataRepositoryTestUtil;
import com.safetynet.alerts.util.TestRepository;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class ChildAlertServiceUnitTest {

    ChildAlertService service;

    DataRepository repo = new JsonDataRepositoryTestUtil();

    @BeforeEach
    public void setup() throws Exception {
        TestRepository.jsonDataInitializer();
        service = new ChildAlertService(repo);
    }

    @Test
    public void givenValidAddress_whenGetFamilyMembersFromAddress_thenFamilyIsReturned() {
        // given
        final String address = "1509 Culver St";
        final List<FamilyResponse> expectedFamily = new ArrayList<>();
        final FamilyResponse member1 = new FamilyResponse();
        member1.setFirstName("John");
        member1.setLastName("Boyd");
        expectedFamily.add(member1);
        final FamilyResponse member2 = new FamilyResponse();
        member2.setFirstName("Jacob");
        member2.setLastName("Boyd");
        expectedFamily.add(member2);
        final FamilyResponse member3 = new FamilyResponse();
        member3.setFirstName("Tenley");
        member3.setLastName("Boyd");
        expectedFamily.add(member3);
        final FamilyResponse member4 = new FamilyResponse();
        member4.setFirstName("Roger");
        member4.setLastName("Boyd");
        expectedFamily.add(member4);
        final FamilyResponse member5 = new FamilyResponse();
        member5.setFirstName("Felicia");
        member5.setLastName("Boyd");
        expectedFamily.add(member5);
        // when
        final List<FamilyResponse> actualFamily = service.getFamilyMembersFromAddress(address);
        // then
        assertThat(actualFamily).usingRecursiveComparison().isEqualTo(expectedFamily);
    }

    @Test
    public void givenUnknownAddress_whenGetFamilyMembersFromAddress_thenEmptyListIsReturned() {
        // given
        final String unknownAddress = "an unknown address";
        // when
        final List<FamilyResponse> family = service.getFamilyMembersFromAddress(unknownAddress);
        // then
        assertThat(family).isEmpty();
    }

    @Test
    public void givenValidFamily_whenGetChildrenFromFamily_thenChildrenAreReturned() {
        // given
        final String address = "1509 Culver St";
        final List<FamilyResponse> family = service.getFamilyMembersFromAddress(address);
        final List<ChildResponse> expectedChildren = new ArrayList<>();
        final ChildResponse child1 = new ChildResponse();
        child1.setFirstName("Tenley");
        child1.setLastName("Boyd");
        child1.setAge(11);
        child1.setFamily(family);
        expectedChildren.add(child1);
        final ChildResponse child2 = new ChildResponse();
        child2.setFirstName("Roger");
        child2.setLastName("Boyd");
        child2.setAge(5);
        child2.setFamily(family);
        expectedChildren.add(child2);
        // when
        final List<ChildResponse> actualChildren = service.getChildrenFromFamily(family);
        // then
        assertThat(actualChildren).usingRecursiveComparison().isEqualTo(expectedChildren);
    }

}
