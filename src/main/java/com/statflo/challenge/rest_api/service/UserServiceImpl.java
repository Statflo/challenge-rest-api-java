package com.statflo.challenge.rest_api.service;

import com.statflo.challenge.rest_api.Exceptions.InvalidUserCriteriaException;
import com.statflo.challenge.rest_api.Exceptions.UserAlreadyExistException;
import com.statflo.challenge.rest_api.entity.User;
import com.statflo.challenge.rest_api.enums.UserConstants;
import com.statflo.challenge.rest_api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.statflo.challenge.rest_api.constants.LoggerConstant.*;
import static com.statflo.challenge.rest_api.enums.UserConstants.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    /**
     * Gets the user for Id.
     *
     * @return Optional of User
     */
    @Override
    public Optional<User> findUserById(String id) {
        log.debug(LOGGER_SERVICE_STATEMENT_1001, id);
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            log.debug(LOGGER_SERVICE_STATEMENT_1002, userOptional.toString());
        } else {
            log.debug(LOGGER_SERVICE_STATEMENT_1003, id);
        }
        return userOptional;
    }

    /**
     * Saved User.
     *
     * @return Saved User
     */
    @Override
    public User saveUser(User user) {
        if (user.getId() != null) {
            Optional<User> userOptional = userRepository.findById(user.getId());
            if (userOptional.isPresent()) {
                throw new UserAlreadyExistException();
            }
        }
        user.setId(UUID.randomUUID().toString());
        log.debug(LOGGER_SERVICE_STATEMENT_1005, user.toString());
        return userRepository.save(user);
    }

    /**
     * Gets the user for criteria.
     *
     * @return User for matching criteria
     * @throws InvalidUserCriteriaException if criteria doesn't match enum UserConstants values
     */
    @Override
    public List<User> findByCriteria(Map<String, Object> criteria) {
        if (!isUserCriteriaValid(criteria)) {
            throw new InvalidUserCriteriaException();
        }
        if (criteria.containsKey(ID.getValue()) && criteria.containsKey(NAME.getValue()) && criteria.containsKey(ROLE.getValue())) {
            return userRepository.findByIdAndNameAndRole(criteria.get(ID.getValue()).toString(), criteria.get(NAME.getValue()).toString(), criteria.get(ROLE.getValue()).toString());
        }
        if (criteria.containsKey(ID.getValue()) && criteria.containsKey(NAME.getValue())) {
            return userRepository.findByIdAndName(criteria.get(ID.getValue()).toString(), criteria.get(NAME.getValue()).toString());
        }
        if (criteria.containsKey(ID.getValue()) && criteria.containsKey(ROLE.getValue())) {
            return userRepository.findByIdAndRole(criteria.get(ID.getValue()).toString(), criteria.get(ROLE.getValue()).toString());
        }
        if (criteria.containsKey(NAME.getValue()) && criteria.containsKey(ROLE.getValue())) {
            return userRepository.findByNameAndRole(criteria.get(NAME.getValue()).toString(), criteria.get(ROLE.getValue()).toString());
        }
        if (criteria.containsKey(NAME.getValue())) {
            return userRepository.findByName(criteria.get(NAME.getValue()).toString());
        }
        if (criteria.containsKey(ID.getValue())) {
            Optional<User> userOptional = userRepository.findById(criteria.get(ID.getValue()).toString());
            return userOptional.map(Collections::singletonList).orElse(Collections.emptyList());
        }
        if (criteria.containsKey(ROLE.getValue())) {
            return userRepository.findByRole(criteria.get(ROLE.getValue()).toString());
        }
        return Collections.emptyList();
    }

    private boolean isUserCriteriaValid(Map<String, Object> criteria) {
        if (criteria.isEmpty()) {
            return false;
        }
        return criteria.entrySet().stream().allMatch(c -> UserConstants.isValidValue(c.getKey()));
    }
}
