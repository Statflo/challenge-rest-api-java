package com.statflo.challenge.rest_api.service;

import com.statflo.challenge.rest_api.Exceptions.InvalidUserCriteriaException;
import com.statflo.challenge.rest_api.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    /**
     * Gets the user for Id.
     *
     * @return Optional of User
     */
    Optional<User> findUserById(String id);

    /**
     * Saved User.
     *
     * @return Saved User
     */
    User saveUser(User user);

    /**
     * Gets the user for criteria.
     *
     * @return User for matching criteria
     * @throws InvalidUserCriteriaException if criteria doesn't match enum UserConstants values
     */
    List<User> findByCriteria(Map<String, Object> criteria);
}
