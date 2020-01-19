package com.statflo.challenge.rest_api.dao_interfaces;

import com.statflo.challenge.rest_api.domains.User;
import com.statflo.challenge.rest_api.domains.UserRequest;

import java.util.*;

/**
 * Interface for all the methods that interact with the local database.
 */
public interface UserDaoInterface {

    User getUserByID(String id);

    User createUser(UserRequest userRequest);

    List<User> findUsers(Map<String, String> criteria);

    User updateUser(String id, Map<String, String> changes);

    int deleteUser(String id);

}

