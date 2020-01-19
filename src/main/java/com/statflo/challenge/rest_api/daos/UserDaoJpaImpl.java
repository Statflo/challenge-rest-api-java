package com.statflo.challenge.rest_api.daos;

import com.statflo.challenge.rest_api.dao_interfaces.UserDaoInterface;
import com.statflo.challenge.rest_api.domains.User;
import com.statflo.challenge.rest_api.domains.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    public User createUser(UserRequest userRequest) {
        User user = new User();
        user.setRole(userRequest.getRole());
        user.setName(userRequest.getName());
        user.setId(getRandomHexString());
        entityManager.merge(user);
        User newUser = getUserByID(user.getId());

        return newUser;
    }

    @Override
    public List<User> findUsers(Map<String, String> criteria) {

        if (criteria.containsKey("role")){

            return entityManager.createQuery("select u from User u where u.role = :role", User.class)
                    .setParameter("role", criteria.get("role"))
                    .getResultList();
        }

        else {
            return entityManager.createQuery("select u from User u where u.name = :name", User.class)
                    .setParameter("name", criteria.get("name"))
                    .getResultList();
        }

    }

    @Override
    public User updateUser(String id, Map<String, String> changes) {

        boolean role_exists = changes.containsKey("role");
        boolean name_exists = changes.containsKey("name");

        if (role_exists && !name_exists){
            entityManager.createQuery("update User set role=:role where id=:id")
                    .setParameter("role", changes.get("role"))
                    .setParameter("id", id)
                    .executeUpdate();
        }

        else if (name_exists && !role_exists){
            entityManager.createQuery("update User set name=:name where id=:id")
                    .setParameter("name", changes.get("name"))
                    .setParameter("id", id)
                    .executeUpdate();
        }
        else {
            entityManager.createQuery("update User set name=:name, role=:role where id=:id")
                    .setParameter("name", changes.get("name"))
                    .setParameter("role", changes.get("role"))
                    .setParameter("id", id)
                    .executeUpdate();
        }
        return getUserByID(id);
    }

    @Override
    public int deleteUser(String id) {

        String query = "delete from User where id=:id";
        Query deleteQuery = entityManager.createQuery(query);
        deleteQuery.setParameter("id", id);
        return deleteQuery.executeUpdate();
    }


    private String getRandomHexString(){
        Random random = new Random();
        int val = random.nextInt();
        String Hex = new String();
        return Hex = Integer.toHexString(val);
    }
}
