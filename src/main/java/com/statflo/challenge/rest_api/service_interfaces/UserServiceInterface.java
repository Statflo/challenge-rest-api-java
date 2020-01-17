package com.statflo.challenge.rest_api.service_interfaces;

import com.statflo.challenge.rest_api.domains.User;

public interface UserServiceInterface {

    User getUserByID(String id);

    User createUser(User user);

}
