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


    /**
     * This endpoint retrieves user given an unique user id.
     * @param id
     * @return
     * Returns a response entity with the user retrieved, and the the status of the call.
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> fetch(@PathVariable("id") String id) {

        User user = userService.getUserByID(id);
        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    /**
     * This endpoint retrieves users by specific parameter criteria. Only one criteria can be passed at a time.
     * @param criteria
     * @return
     * Returns all users after filtering by given criteria. The criteria's that can be used are "role" and "name".
     */
    @GetMapping()
    public ResponseEntity<List<User>> find(@RequestParam Map<String, String> criteria) {

        List<User> listOfUsers = userService.findUsers(criteria);
        return new ResponseEntity<>(listOfUsers, HttpStatus.OK);
    }


    /**
     * POST call to create a new user.
     * @param userRequest
     * @return
     * Returns the new user created given a request body with the role, and name. The id is randomly generated within
     * the service.
     */
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody UserRequest userRequest) {

            User newUser = userService.createUser(userRequest);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }


    /**
     * Patch call to update user info.
     * @param id
     * @param changes - map of the new role, and name information. Can either change both, or one of them.
     * @return
     * Updates user role, and/or name information, given the user id. Role and name is passed in through the body.
     * Returns new user information.
     */
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


    /**
     * Deletes a user given specific user id.
     * @param id
     * @return
     * Returns a response message indicating that the user has been deleted.
     */
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
