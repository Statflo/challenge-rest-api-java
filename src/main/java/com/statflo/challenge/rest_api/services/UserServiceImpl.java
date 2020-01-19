package com.statflo.challenge.rest_api.services;
import com.statflo.challenge.rest_api.dao_interfaces.UserDaoInterface;
import com.statflo.challenge.rest_api.domains.User;
import com.statflo.challenge.rest_api.domains.UserRequest;
import com.statflo.challenge.rest_api.service_interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class UserServiceImpl implements UserServiceInterface {

    UserDaoInterface userDao;

    @Autowired
    public UserServiceImpl(@Qualifier("userDaoJpaImpl") UserDaoInterface userDao){
        this.userDao = userDao;
    }

    /**
     * @param id
     * @return
     * Returns user given the unique id.
     */
    @Override
    @Transactional
    public User getUserByID(String id) {
        return userDao.getUserByID(id);
    }

    /**
     * @param userRequest
     * @return
     * Returns created user. User Request is the specific body passed in without id.
     */
    @Override
    @Transactional
    public User createUser(UserRequest userRequest) {
        return userDao.createUser(userRequest);
    }

    /**
     * @param criteria
     * @return Returns a list of users based of parameter criteria (role, name or both)
     */
    @Override
    @Transactional
    public List<User> findUsers(Map<String, String> criteria) {
        return userDao.findUsers(criteria);
    }

    /**
     * @param id
     * @param changes
     * @return Returns user with updated changes
     */
    @Override
    @Transactional
    public User updateUser(String id, Map<String, String> changes) {
        return userDao.updateUser(id, changes);
    }

    /**
     * @param id
     * @return
     * Returns int indicating user deletion. >0 then the user has been successfully deleted.
     */
    @Override
    @Transactional
    public int deleteUser(String id){
        return userDao.deleteUser(id);
    }



}
