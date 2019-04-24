package com.statflo.challenge.rest_api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.statflo.challenge.rest_api.Exceptions.InvalidUserCriteriaException;
import com.statflo.challenge.rest_api.Exceptions.UserAlreadyExistException;
import com.statflo.challenge.rest_api.entity.User;
import com.statflo.challenge.rest_api.enums.UserConstants;
import com.statflo.challenge.rest_api.service.MessageHelperService;
import com.statflo.challenge.rest_api.service.UserServiceImpl;
import org.hibernate.TransactionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static com.statflo.challenge.rest_api.constants.ConfigConstant.REST_API_URI_USER;
import static com.statflo.challenge.rest_api.constants.ConfigConstant.REST_API_URI_USER_ID;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class V1ControllerUnitTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean(reset = MockReset.NONE)
    private UserServiceImpl userService;
    @MockBean(reset = MockReset.NONE)
    private MessageHelperService messageHelperService;


    @Test
    public void testFindUserById() throws Exception {
        User user = getMockUser();
        doReturn(Optional.of(user)).when(userService).findUserById(user.getId());
        ResultActions resultActions = mvc
                .perform(get(REST_API_URI_USER + REST_API_URI_USER_ID
                        .replace("{id}", user.getId())));
        resultActions.andExpect(status().isOk()).andExpect(content().string(userToJson(user)));
    }

    @Test
    public void testFindUserById_WhenRecord_NotFound() throws Exception {
        User user = getMockUser();
        when(userService.findUserById(user.getId())).thenReturn(Optional.empty());
        ResultActions resultActions = mvc
                .perform(get(REST_API_URI_USER + REST_API_URI_USER_ID
                        .replace("{id}", user.getId())));
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void testFindUserById_WhenException() throws Exception {
        User user = getMockUser();
        doThrow(new TransactionException("Exception")).when(userService).findUserById(user.getId());
        ResultActions resultActions = mvc
                .perform(get(REST_API_URI_USER + REST_API_URI_USER_ID
                        .replace("{id}", user.getId())));
        resultActions.andExpect(status().is5xxServerError());
    }

    @Test
    public void testSaveUser() throws Exception {
        User user = getMockUser();
        doReturn(user).when(userService).saveUser(user);
        ResultActions resultActions = mvc
                .perform(post(REST_API_URI_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)));
        resultActions.andExpect(status().isOk()).andExpect(content().string(userToJson(user)));
        verify(userService, times(1)).saveUser(user);
    }

    @Test
    public void testSaveUser_WhenException() throws Exception {
        User user = getMockUser();
        doThrow(new TransactionException("Exception")).when(userService).saveUser(user);
        ResultActions resultActions = mvc
                .perform(post(REST_API_URI_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)));
        resultActions.andExpect(status().is5xxServerError());
    }

    @Test
    public void testSaveUser_UserAlreadyExist() throws Exception {
        User user = getMockUser();
        doThrow(new UserAlreadyExistException()).when(userService).saveUser(user);
        ResultActions resultActions = mvc
                .perform(post(REST_API_URI_USER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)));
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void testFind() throws Exception {
        User user = getMockUser();
        List<User> users = Collections.singletonList(user);
        Map<String, Object> columnVsValues = new LinkedHashMap<>();
        columnVsValues.put(UserConstants.ID.getValue(), user.getId());
        when(userService.findByCriteria(columnVsValues)).thenReturn(users);
        ResultActions resultActions = mvc
                .perform(get(REST_API_URI_USER).param(UserConstants.ID.getValue(), user.getId()));
        resultActions.andExpect(status().isOk()).andExpect(content().string("[" + userToJson(user) + "]"));
    }


    @Test
    public void testFind_WhenNoRecords() throws Exception {
        User user = getMockUser();
        List<User> users = Collections.emptyList();
        Map<String, Object> columnVsValues = new LinkedHashMap<>();
        columnVsValues.put(UserConstants.ID.getValue(), user.getId());
        when(userService.findByCriteria(columnVsValues)).thenReturn(users);
        ResultActions resultActions = mvc
                .perform(get(REST_API_URI_USER).param(UserConstants.ID.getValue(), user.getId()));
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void testFind_InvalidCriteria() throws Exception {
        User user = getMockUser();
        Map<String, Object> columnVsValues = new LinkedHashMap<>();
        columnVsValues.put("address", user.getId());
        when(userService.findByCriteria(columnVsValues)).thenThrow(new InvalidUserCriteriaException());
        ResultActions resultActions = mvc
                .perform(get(REST_API_URI_USER).param("address", user.getId()));
        resultActions.andExpect(status().is4xxClientError());
    }

    @Test
    public void testFind_Exception() throws Exception {
        User user = getMockUser();
        Map<String, Object> columnVsValues = new LinkedHashMap<>();
        columnVsValues.put(UserConstants.ID.getValue(), user.getId());
        when(userService.findByCriteria(columnVsValues)).thenThrow(new TransactionException("Exception"));
        ResultActions resultActions = mvc
                .perform(get(REST_API_URI_USER).param(UserConstants.ID.getValue(), user.getId()));
        resultActions.andExpect(status().is5xxServerError());
    }


    private User getMockUser() {
        return new User("1", "ABC", "Manager");
    }

    private String userToJson(User user) {
        return "{\"id\":\"" + user.getId() + "\",\"name\":\"" + user.getName() + "\",\"role\":\"" + user.getRole() + "\"}";
    }
}
