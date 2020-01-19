package com.statflo.challenge.rest_api.controller;

import com.statflo.challenge.rest_api.domains.User;
import com.statflo.challenge.rest_api.domains.UserRequest;
import com.statflo.challenge.rest_api.service_interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE})
@RequestMapping("/v1/users")
public class V1Controller {

    private UserServiceInterface userService;

    @Autowired
    public V1Controller(UserServiceInterface userService){
        this.userService = userService;
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<User> fetch(@PathVariable("id") String id) {

        User user = userService.getUserByID(id);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @GetMapping()
    public ResponseEntity<List<User>> find(@RequestParam Map<String, String> criteria) {

        List<User> listOfUsers = userService.findUsers(criteria);
        return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UserRequest userRequest) {

            User newUser = userService.createUser(userRequest);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    @PatchMapping(value = "/{id}")
    public ResponseEntity<?> patch(@PathVariable("id") String id, @RequestBody Map<String, String> changes) {

        User user = userService.getUserByID(id);

        if (changes.containsKey("id")){
            return new ResponseEntity<>("Can't update id property.", HttpStatus.CONFLICT);
        }
        else if (user == null){
                return new ResponseEntity<>("User doesn't exist.", HttpStatus.CONFLICT);
        }
        else {
            User changed_user = userService.updateUser(id, changes);
            return new ResponseEntity<>(changed_user, HttpStatus.OK);
        }
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") String id){

        User user = userService.getUserByID(id);

        if (user == null){
            return new ResponseEntity<>("User does not exist.", HttpStatus.NOT_FOUND);
        }

        int deleteResult = userService.deleteUser(id);

        if (deleteResult > 0){
            return new ResponseEntity<>("User successfully deleted.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("User was not deleted.", HttpStatus.BAD_REQUEST);
        }
    }
}
