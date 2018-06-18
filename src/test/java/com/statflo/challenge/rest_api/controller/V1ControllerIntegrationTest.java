package com.statflo.challenge.rest_api.controller;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.apache.http.client.HttpClient;
import static org.junit.Assert.assertEquals;


import org.json.JSONObject;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class V1ControllerIntegrationTest 
{
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private RestTemplate patchRestTemplate;

    
    /// done
    @Before
    public void setup() 
    {
        this.patchRestTemplate = restTemplate.getRestTemplate();
        HttpClient httpClient = HttpClientBuilder.create().build();
        this.patchRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    
    /// done
    @Test
    public void testShouldCreateUser() {
        
        try
        {
        String requestBody = "{\"name\": \"foo\", \"role\": \"bar\"}";

        final String response = this.restTemplate.postForObject(
                "http://localhost:" + port + "/v1/users",
                requestBody,
                String.class
        );
        
        JSONObject right=new JSONObject(this.getUserStub());
        
        assertEquals(right.toString(), response);
        
         System.out.println("Response 1:"+response);
        }
        catch(Exception e)
        {
            System.out.println("Exception 1:"+e);
        }
    }
    
    
    /// done
    @Test
    public void testShouldRetrieveUserByGivenId()
    {
        try
        {
            final String response = this.restTemplate.getForObject(
                    "http://localhost:" + port + "/v1/users/977e3f5b-6a70-4862-9ff8-96af4477272a",
                    String.class
            );

            JSONObject right=new JSONObject(this.getUserStub());


            assertEquals(right.toString(), response);
            
             System.out.println("Response 2:"+response);

        }
        catch(Exception e)
        {
             System.out.println("Exception 2:"+e);
            
        }
    }

    
    /// done
    @Test
    public void testShouldFindUserByGivenCriteria() {
        
        try
        {
            final String userStub = this.getUserStub();

            final String response = this.restTemplate.getForObject(
                    "http://localhost:" + port + "/v1/users?role=bar",
                    String.class
            );

            JSONObject right=new JSONObject(userStub);
            assertEquals(right.toString(), response);
            
             System.out.println("Response 3:"+response);
        }
        catch(Exception e)
        {
             System.out.println("Exception 3:"+e);
        }
    }
    

    
    /// done
    @Test
    public void testShouldPatchUser() {
        try
        {
        
            String requestBody = "{\"name\": \"foo bar\"}";

            ResponseEntity<String> response = this.patchRestTemplate.exchange(
                    "http://localhost:" + port + "/v1/users/977e3f5b-6a70-4862-9ff8-96af4477272a",
                    HttpMethod.PATCH,
                    new HttpEntity<>(requestBody),
                    String.class
            );

            JSONObject right=new JSONObject("{\"id\": \"977e3f5b-6a70-4862-9ff8-96af4477272a\", \"name\": \"foo bar\", \"role\": \"bar\"}");
            
            System.out.println("Response 4:"+response);
            assertEquals(right.toString(), response.getBody());
            
        }
        catch(Exception e)
        {
             System.out.println("Exception 4:"+e);
        }
    }

    /// done
    private String getUserStub() 
    {
        return "{\"id\": \"977e3f5b-6a70-4862-9ff8-96af4477272a\", \"name\": \"foo\", \"role\": \"bar\"}";
    }
}
