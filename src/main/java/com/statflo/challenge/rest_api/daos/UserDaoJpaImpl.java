package com.statflo.challenge.rest_api.daos;

import com.statflo.challenge.rest_api.dao_interfaces.UserDaoInterface;
import com.statflo.challenge.rest_api.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class UserDaoJpaImpl implements UserDaoInterface {


    private EntityManager entityManager;

    @Autowired
    public UserDaoJpaImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public User getUserByID(String id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User createUser(User user) {
        entityManager.merge(user);
        User newUser = getUserByID(user.getId());

        return newUser;
    }
}
