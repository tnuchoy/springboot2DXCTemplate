package com.dxc.application.feature.usermaster.controller;

import com.dxc.application.constants.AppConstants;
import com.dxc.application.constants.MessagesConstants;
import com.dxc.application.feature.common.dto.RestJsonData;
import com.dxc.application.feature.usermaster.data.database.model.User;
import com.dxc.application.utils.JsonUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlConfig(dataSource = "myBatisDataSource", transactionManager = "mybastistx")
public class UserMasterControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Test search Personal Information but No Data Found")
    @Sql(value = {"/testdata/UserMasterControllerTest/testSearchUserData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testSearchUserButNoDataFound() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setSearchFirstName("xxx");
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.POST, request, RestJsonData.class);
        assertTrue(response.getBody().getMessage().contains(MessagesConstants.ERROR_MESSAGE_DATA_NOT_FOUND), "result must contain no data found error");
    }

    @Test
    @DisplayName("Test search Personal Information without Criteria must have 3 items")
    @Sql(value = {"/testdata/UserMasterControllerTest/testSearchUserData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testSearchUserWithoutCriteria() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.POST, request, RestJsonData.class);
        ObjectMapper mapper = new ObjectMapper();
        List<User> searchResultList = mapper.convertValue(response.getBody().getData(), new TypeReference<List<User>>() {
        });
        assertEquals(3, searchResultList.size(), "search without criteria size is 3");
    }

    @Test
    @DisplayName("Test search Personal Information with Citizen ID must have 1 item")
    @Sql(value = {"/testdata/UserMasterControllerTest/testSearchUserData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testSearchUserWithCitizenCriteria() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setSearchCitizenId("1111111111111");
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.POST, request, RestJsonData.class);
        ObjectMapper mapper = new ObjectMapper();
        List<User> searchResultList = mapper.convertValue(response.getBody().getData(), new TypeReference<List<User>>() {
        });
        assertEquals(1, searchResultList.size(), "search with citizen ID criteria size is 1");
    }

    @Test
    @DisplayName("Test search Personal Information with First Name Criteria must have 1 items")
    @Sql(value = {"/testdata/UserMasterControllerTest/testSearchUserData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testSearchUserWithFirstNameCriteria() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setSearchFirstName("sawaddee");
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.POST, request, RestJsonData.class);
        ObjectMapper mapper = new ObjectMapper();
        List<User> searchResultList = mapper.convertValue(response.getBody().getData(), new TypeReference<List<User>>() {
        });
        assertEquals(1, searchResultList.size(), "search with first name criteria size is 1");
    }

    @Test
    @DisplayName("Test search Personal Information with First Name Criteria must have 2 items")
    @Sql(value = {"/testdata/UserMasterControllerTest/testSearchUserData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testSearchUserWithFirstNameCriteriaWildcard() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setSearchFirstName("pra*");
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.POST, request, RestJsonData.class);
        ObjectMapper mapper = new ObjectMapper();
        List<User> searchResultList = mapper.convertValue(response.getBody().getData(), new TypeReference<List<User>>() {
        });
        assertEquals(2, searchResultList.size(), "search with first name criteria wildcard size is 2");
    }

    @Test
    @DisplayName("Test search Personal Information with Last Name Criteria must have 1 item")
    @Sql(value = {"/testdata/UserMasterControllerTest/testSearchUserData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testSearchUserWithLastNameCriteria() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setSearchLastName("freetime");
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.POST, request, RestJsonData.class);
        ObjectMapper mapper = new ObjectMapper();
        List<User> searchResultList = mapper.convertValue(response.getBody().getData(), new TypeReference<List<User>>() {
        });
        assertEquals(1, searchResultList.size(), "search with last name criteria size is 1");
    }

    @Test
    @DisplayName("Test search Personal Information with Last Name Criteria must have 2 items")
    @Sql(value = {"/testdata/UserMasterControllerTest/testSearchUserData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testSearchUserWithLastNameCriteriaWildcard() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setSearchLastName("f*");
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.POST, request, RestJsonData.class);
        ObjectMapper mapper = new ObjectMapper();
        List<User> searchResultList = mapper.convertValue(response.getBody().getData(), new TypeReference<List<User>>() {
        });
        assertEquals(2, searchResultList.size(), "search with last name criteria wildcard size is 2");
    }

    @Test
    @DisplayName("Test search Personal Information with All Criteria must have 1 items")
    @Sql(value = {"/testdata/UserMasterControllerTest/testSearchUserData.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testSearchUserWithAllCriteria() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setSearchCitizenId("3333333333333");
        user.setSearchFirstName("s*");
        user.setSearchLastName("f*");
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.POST, request, RestJsonData.class);
        ObjectMapper mapper = new ObjectMapper();
        List<User> searchResultList = mapper.convertValue(response.getBody().getData(), new TypeReference<List<User>>() {
        });
        assertEquals(1, searchResultList.size(), "search with all criteria size is 1");
    }

    @Test
    @DisplayName("Test add Personal Information successfully")
    @Sql(value = {"/testdata/UserMasterControllerTest/testAddUser.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testAddUserSuccess() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setCitizenId(new BigDecimal("4444444444444"));
        user.setFirstName("Sabai");
        user.setLastName("Fulltime");
        user.setDateOfBirth(new Date());
        user.setAddress("Bannok, Thailand");
        user.setPictureId(new BigDecimal("4"));
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.PUT, request, RestJsonData.class);
        ObjectMapper mapper = new ObjectMapper();
        BigDecimal rowCount = mapper.convertValue(response.getBody().getRowCount(), new TypeReference<BigDecimal>() {
        });
        assertEquals(1, rowCount.intValue(), "add personal information successfully");
    }

    @Test
    @DisplayName("Test add Personal Information but duplication error")
    @Sql(value = {"/testdata/UserMasterControllerTest/testUpdateUser.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testAddUserDuplicate() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setCitizenId(new BigDecimal("4444444444444"));
        user.setFirstName("Sabaisabai");
        user.setLastName("Parttime");
        user.setDateOfBirth(new Date());
        user.setAddress("Bangkok, Thailand");
        user.setPictureId(new BigDecimal("5"));
        GregorianCalendar modifiedDt = new GregorianCalendar(2021, Calendar.AUGUST,25, 16,1,37);
        user.setModifiedDt(modifiedDt.getTime());
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.PUT, request, RestJsonData.class);
        assertTrue(response.getBody().getMessage().contains(MessagesConstants.DATA_DUPLICATED), "result must contain dupplication error");
    }

    @Test
    @DisplayName("Test update Personal Information")
    @Sql(value = {"/testdata/UserMasterControllerTest/testUpdateUser.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testUpdateUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setCitizenId(new BigDecimal("4444444444444"));
        user.setFirstName("Sabaidee");
        user.setLastName("Parttime");
        user.setDateOfBirth(new Date());
        user.setAddress("Bangkok, Thailand");
        user.setPictureId(new BigDecimal("5"));
        GregorianCalendar modifiedDt = new GregorianCalendar(2021, Calendar.AUGUST,25, 16,1,37);
        user.setModifiedDt(modifiedDt.getTime());
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.PATCH, request, RestJsonData.class);
        ObjectMapper mapper = new ObjectMapper();
        BigDecimal rowCount = mapper.convertValue(response.getBody().getRowCount(), new TypeReference<BigDecimal>() {
        });
        assertEquals(1, rowCount.intValue(), "update personal information successfully");
    }

    @Test
    @DisplayName("Test update Personal Information but other user had just changed data")
    @Sql(value = {"/testdata/UserMasterControllerTest/testUpdateUser.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testUpdateUserConcurrencyError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setCitizenId(new BigDecimal("4444444444444"));
        user.setFirstName("Sabaisabai");
        user.setLastName("Parttime");
        user.setDateOfBirth(new Date());
        user.setAddress("Bangkok, Thailand");
        user.setPictureId(new BigDecimal("5"));
        GregorianCalendar modifiedDt = new GregorianCalendar(2021, Calendar.AUGUST,26, 17,1,37);
        user.setModifiedDt(modifiedDt.getTime());
        String jsonString = JsonUtil.toJsonString(user);
        HttpEntity<String> request = new HttpEntity<>(jsonString, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.PATCH, request, RestJsonData.class);
        assertTrue(response.getBody().getMessage().contains(MessagesConstants.CONCURRENCY_ERROR), "result must contain concurrency error");
    }

    @Test
    @DisplayName("Test delete Personal Information successfully")
    @Sql(value = {"/testdata/UserMasterControllerTest/testUpdateUser.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testDeleteUserSuccess() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setCitizenId(new BigDecimal("4444444444444"));
        GregorianCalendar modifiedDt = new GregorianCalendar(2021,Calendar.AUGUST,25, 16,1,37);
        user.setModifiedDt(modifiedDt.getTime());
        List<User> users = new ArrayList<>();
        users.add(user);
        HttpEntity<List<User>> request = new HttpEntity<>(users, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.DELETE, request, RestJsonData.class);
        ObjectMapper mapper = new ObjectMapper();
        BigDecimal rowCount = mapper.convertValue(response.getBody().getRowCount(), new TypeReference<BigDecimal>() {
        });
        assertEquals(1, rowCount.intValue(), "update personal information successfully");
    }

    @Test
    @DisplayName("Test delete Personal Information but other user had just changed data")
    @Sql(value = {"/testdata/UserMasterControllerTest/testUpdateUser.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testDeleteUserConcurrencyError() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        User user = new User();
        user.setCitizenId(new BigDecimal("4444444444444"));
        GregorianCalendar modifiedDt = new GregorianCalendar(2021, Calendar.AUGUST,26, 17,1,37);
        user.setModifiedDt(modifiedDt.getTime());
        List<User> users = new ArrayList<>();
        users.add(user);
        HttpEntity<List<User>> request = new HttpEntity<>(users, headers);
        ResponseEntity<RestJsonData> response = restTemplate.exchange("/usermaster", HttpMethod.DELETE, request, RestJsonData.class);
        ObjectMapper mapper = new ObjectMapper();
        BigDecimal rowCount = mapper.convertValue(response.getBody().getRowCount(), new TypeReference<BigDecimal>() {
        });
        assertTrue(response.getBody().getMessage().contains(MessagesConstants.CONCURRENCY_ERROR), "result must contain concurrency error");
    }
}
