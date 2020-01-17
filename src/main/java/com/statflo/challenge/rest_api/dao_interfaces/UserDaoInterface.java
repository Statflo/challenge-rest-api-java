package com.statflo.challenge.rest_api.dao_interfaces;

import com.statflo.challenge.rest_api.domains.User;

public interface UserDaoInterface {

    User getUserByID(String id);

    User createUser(User user);


}
