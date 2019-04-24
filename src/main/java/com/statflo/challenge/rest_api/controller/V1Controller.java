package com.statflo.challenge.rest_api.controller;

import com.statflo.challenge.rest_api.Exceptions.InvalidUserCriteriaException;
import com.statflo.challenge.rest_api.Exceptions.UserAlreadyExistException;
import com.statflo.challenge.rest_api.entity.User;
import com.statflo.challenge.rest_api.service.MessageHelperService;
import com.statflo.challenge.rest_api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.statflo.challenge.rest_api.constants.ConfigConstant.REST_API_URI_USER;
import static com.statflo.challenge.rest_api.constants.ConfigConstant.REST_API_URI_USER_ID;
import static com.statflo.challenge.rest_api.constants.LoggerConstant.*;
import static com.statflo.challenge.rest_api.constants.MessageConstants.*;
import static org.springframework.http.ResponseEntity.status;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH})
@RequestMapping(REST_API_URI_USER)
public class V1Controller {
    @Autowired
    private UserService userService;
    @Autowired
    private MessageHelperService messageHelperService;
    private Logger log = LoggerFactory.getLogger(V1Controller.class);

    /**
     * Saves the user.
     *
     * @return ResponseEntity with saved user
     */
    @GetMapping(value = REST_API_URI_USER_ID)
    public ResponseEntity findUserById(@PathVariable("id") String id) {
        log.debug(LOGGER_SERVICE_STATEMENT_1004, id);
        try {
            Optional<User> userOptional = userService.findUserById(id);
            if (userOptional.isPresent()) {
                return status(HttpStatus.OK).body(userOptional.get());
            }
            return status(HttpStatus.NOT_FOUND).body(messageHelperService.getMessage(RECORD_NOT_FOUND_ID, id));
        } catch (Exception e) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageHelperService.getMessage(SERVER_INTERNAL_ERROR));
        }
    }

    /**
     * Save the user.
     *
     * @return ResponseEntity with Saved user
     */
    @PostMapping()
    public ResponseEntity saveUser(@RequestBody User user) {
        log.debug(LOGGER_SERVICE_STATEMENT_1006, user.toString());
        try {
            user = userService.saveUser(user);
            return status(HttpStatus.OK).body(user);
        } catch (UserAlreadyExistException e) {
            return status(HttpStatus.BAD_REQUEST).body(messageHelperService.getMessage(USER_ALREADY_EXIST, user.getId()));
        } catch (Exception e) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageHelperService.getMessage(SERVER_INTERNAL_ERROR));
        }
    }

    /**
     * Gets the user for criteria.
     *
     * @return ResponseEntity with User for criteria
     */
    @GetMapping()
    public ResponseEntity find(@RequestParam Map<String, Object> criteria) {
        log.debug(LOGGER_SERVICE_STATEMENT_1007);
        try {
            List<User> users = userService.findByCriteria(criteria);
            if (!CollectionUtils.isEmpty(users)) {
                return status(HttpStatus.OK).body(users);
            }
            return status(HttpStatus.NOT_FOUND).body(messageHelperService.getMessage(RECORD_NOT_FOUND_CRITERIA));
        } catch (InvalidUserCriteriaException e) {
            return status(HttpStatus.BAD_REQUEST).body(messageHelperService.getMessage(INVALID_USER_CRITERIA));
        } catch (Exception e) {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageHelperService.getMessage(SERVER_INTERNAL_ERROR));
        }
    }

}
