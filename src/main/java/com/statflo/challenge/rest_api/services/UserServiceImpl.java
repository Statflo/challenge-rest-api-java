package com.statflo.challenge.rest_api.services;

import com.statflo.challenge.rest_api.dao_interfaces.UserDaoInterface;
import com.statflo.challenge.rest_api.domains.User;
import com.statflo.challenge.rest_api.service_interfaces.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserServiceImpl implements UserServiceInterface {

    UserDaoInterface userDao;

    @Autowired
    public UserServiceImpl(@Qualifier("userDaoJpaImpl") UserDaoInterface userDao){
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public User getUserByID(String id) {
        return userDao.getUserByID(id);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return userDao.createUser(user);
    }
}
