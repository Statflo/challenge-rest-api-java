package com.statflo.challenge.rest_api.controller;

import com.statflo.challenge.rest_api.entity.User;
import com.statflo.challenge.rest_api.service.MessageHelperService;
import com.statflo.challenge.rest_api.service.UserService;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

import static com.statflo.challenge.rest_api.constants.MessageConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class V1ControllerIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageHelperService messageHelperService;
    private RestTemplate patchRestTemplate;
    private String id = UUID.randomUUID().toString();
    private String name = UUID.randomUUID().toString();
    private String role = UUID.randomUUID().toString();
    private User user = new User(id, name, role);

    @Before
    public void setup() {
        this.patchRestTemplate = restTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.patchRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
        this.userService.saveUser(user);
    }

    @Test
    public void testFindUserById() {
        final String response = this.restTemplate.getForObject(
                "http://localhost:" + port + "/v1/users/" + user.getId(),
                String.class
        );
        assertEquals(this.userToJson(user), response);
    }

    @Test
    public void testFindUserById_NoRecord() {
        String id = UUID.randomUUID().toString();
        final String response = this.restTemplate.getForObject(
                "http://localhost:" + port + "/v1/users/" + id,
                String.class
        );
        assertEquals(messageHelperService.getMessage(RECORD_NOT_FOUND_ID, id), response);
    }

    @Test
    public void testFind() {
        final String response = this.restTemplate.getForObject(
                "http://localhost:" + port + "/v1/users?role=" + user.getRole(),
                String.class
        );
        assertEquals("[" + this.userToJson(user) + "]", response);
    }

    @Test
    public void testFind_InvalidCriteria() {
        final String response = this.restTemplate.getForObject(
                "http://localhost:" + port + "/v1/users?address=" + user.getRole(),
                String.class
        );
        assertEquals(messageHelperService.getMessage(INVALID_USER_CRITERIA), response);
    }

    @Test
    public void testFind_RecordNotFound() {
        final String response = this.restTemplate.getForObject(
                "http://localhost:" + port + "/v1/users?role=" + UUID.randomUUID().toString(),
                String.class
        );
        assertEquals(messageHelperService.getMessage(RECORD_NOT_FOUND_CRITERIA), response);
    }

    @Test
    public void testSaveUser() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = "{\"name\": \"foo\", \"role\": \"bar\"}";
        String url = "http://localhost:" + port + "/v1/users";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject(url, entity, String.class);
        JSONObject jsonObject = new JSONObject(response);
        String id = jsonObject.getString("id");
        Optional<User> userOptional = userService.findUserById(id);
        assertTrue(userOptional.isPresent());
    }

    @Test
    public void testSaveUser_WhenUserAlreadyExist() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String requestBody = userToJson(user);
        String url = "http://localhost:" + port + "/v1/users";
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        String response = restTemplate.postForObject(url, entity, String.class);
        assertEquals(messageHelperService.getMessage(USER_ALREADY_EXIST, user.getId()), response);
    }

    private String userToJson(User user) {
        return "{\"id\":\"" + user.getId() + "\",\"name\":\"" + user.getName() + "\",\"role\":\"" + user.getRole() + "\"}";
    }
}
