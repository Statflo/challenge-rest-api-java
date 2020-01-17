package com.statflo.challenge.rest_api.controller;

import com.statflo.challenge.rest_api.domains.User;
import com.statflo.challenge.rest_api.service_interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH})
@RequestMapping("/v1/users")
public class V1Controller {

    private UserServiceInterface userService;

    @Autowired
    public V1Controller(UserServiceInterface userService){
        this.userService = userService;
    }

    /**
     * @TODO: Fix me
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> fetch(@PathVariable("id") String id) {

        User user = userService.getUserByID(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);

//        return "{\"id\": \"977e3f5b-6a70-4862-9ff8-96af4477272a\", \"name\": \"foo\", \"role\": \"bar\"}";
    }

    /**
     * @TODO: Fix me
     */
    @GetMapping()
    public String find(@RequestParam Map<String, Object> criteria) {
        return "[{\"id\": \"977e3f5b-6a70-4862-9ff8-96af4477272a\", \"name\": \"foo\", \"role\": \"bar\"}]";
    }

    /**
     * @TODO: Fix me
     */
    @PostMapping()
    public ResponseEntity<String> create(@RequestBody User user) {

        if (userService.getUserByID(user.getId()) != null){
            return new ResponseEntity<>("Error: User already exists.", HttpStatus.CONFLICT);
        }
        else {
            User newUser = userService.createUser(user);
            return new ResponseEntity<>("User has been successfully added.", HttpStatus.CREATED);
        }
//        return "{\"id\": \"977e3f5b-6a70-4862-9ff8-96af4477272a\", \"name\": \"foo\", \"role\": \"bar\"}";
    }

    /**
     * @TODO: Fix me
     */
    @PatchMapping(value = "/{id}")
    public String patch(@PathVariable("id") String id, String body) {
        return "{\"id\": \"977e3f5b-6a70-4862-9ff8-96af4477272a\", \"name\": \"foo bar\", \"role\": \"bar\"}";
    }
}
