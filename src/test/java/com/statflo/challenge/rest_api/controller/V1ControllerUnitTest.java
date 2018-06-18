package com.statflo.challenge.rest_api.controller;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import static org.junit.Assert.assertEquals;


public class V1ControllerUnitTest {
    V1Controller userController;

    @Before
    public void setUp()
    {
        this.userController = new V1Controller();
    }

    /// done
    @Test
    public void testShouldCreateUser() {
        try
        {
            String requestBody = "{\"name\": \"foo\", \"role\": \"bar\"}";

            final String response = this.userController.create(requestBody);

            JSONObject job=new JSONObject(this.getUserStub());
            assertEquals(job.toString(), response);
        }
        catch(Exception e)
        {
             
        }
    }
    
   
    @Test
    public void testShouldRetrieveUserByGivenId() 
    {
        try
        {
            final String response = this.userController.fetch("977e3f5b-6a70-4862-9ff8-96af4477272a");

            JSONObject job=new JSONObject(this.getUserStub());
            assertEquals(job.toString(), response);
        
        }
        catch(Exception e)
        {
             
        }
    }

    @Test
    public void testShouldFindUserByGivenCriteria() {
        
        try
        {
        Map<String, Object> criteria = new HashMap<>();

        criteria.put("role", "bar");

        final String response = this.userController.find(criteria);
        final String userStub = "[" + this.getUserStub() + "]";

        
            JSONObject job=new JSONObject(userStub);
            assertEquals(job.toString(), response);
        }
        catch(Exception e)
        {
        
        }
    }

    

    @Test
    public void testShouldPatchUser() {
       try
       {
        String requestBody = "{\"name\": \"foo bar\"}";

        final String response = this.userController.patch("977e3f5b-6a70-4862-9ff8-96af4477272a", requestBody);
        
        
            JSONObject job=new JSONObject("{\"id\": \"977e3f5b-6a70-4862-9ff8-96af4477272a\", \"name\": \"foo bar\", \"role\": \"bar\"}");
            assertEquals(job.toString(), response);
        
       }
       catch(Exception e)
       {
       
       }
    }

    private String getUserStub() {
        return "{\"id\": \"977e3f5b-6a70-4862-9ff8-96af4477272a\", \"name\": \"foo\", \"role\": \"bar\"}";
    }
}
