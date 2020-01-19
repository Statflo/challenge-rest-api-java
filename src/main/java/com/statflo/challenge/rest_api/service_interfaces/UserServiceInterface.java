package com.statflo.challenge.rest_api.service_interfaces;

import com.statflo.challenge.rest_api.domains.User;
import com.statflo.challenge.rest_api.domains.UserRequest;

import java.util.List;
import java.util.Map;

/**
 * Service interface which has all the methods that interacts with controller.
 */
public interface UserServiceInterface {

    User getUserByID(String id);

    User createUser(UserRequest userRequest);

    List<User> findUsers(Map<String, String> criteria);

    User updateUser(String id, Map<String, String> changes);

    int deleteUser(String id);

}
