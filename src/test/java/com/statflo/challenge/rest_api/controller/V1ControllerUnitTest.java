package com.statflo.challenge.rest_api.controller;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * @TODO: Fix me
 */
public class V1ControllerUnitTest {
    V1Controller userController;

//    @Before
//    public void setUp()
//    {
//        this.userController = new V1Controller();
//    }

//    @Test
//    public void testShouldRetrieveUserByGivenId() {
//        final String response = this.userController.fetch("977e3f5b-6a70-4862-9ff8-96af4477272a");
//
//        assertEquals(this.getUserStub(), response);
//    }
//
//    @Test
//    public void testShouldFindUserByGivenCriteria() {
//        Map<String, Object> criteria = new HashMap<>();
//
//        criteria.put("role", "bar");
//
//        final String response = this.userController.find(criteria);
//        final String userStub = "[" + this.getUserStub() + "]";
//
//        assertEquals(userStub, response);
//    }
//
//    @Test
//    public void testShouldCreateUser() {
//        String requestBody = "{\"name\": \"foo\", \"role\": \"bar\"}";
//
//        final String response = this.userController.create(requestBody);
//
//        assertEquals(this.getUserStub(), response);
//    }
//
//    @Test
//    public void testShouldPatchUser() {
//        String requestBody = "{\"name\": \"foo bar\"}";
//
//        final String response = this.userController.patch("977e3f5b-6a70-4862-9ff8-96af4477272a", requestBody);
//        assertEquals(
//                "{\"id\": \"977e3f5b-6a70-4862-9ff8-96af4477272a\", \"name\": \"foo bar\", \"role\": \"bar\"}",
//                response
//        );
//    }

    private String getUserStub() {
        return "{\"id\": \"977e3f5b-6a70-4862-9ff8-96af4477272a\", \"name\": \"foo\", \"role\": \"bar\"}";
    }
}
