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

    // Entity manager used to make changes to database (JPA)
    private EntityManager entityManager;

    @Autowired
    public UserDaoJpaImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    /**
     * @param id
     * @return
     * Returns the user found using the unique user id.
     */
    @Override
    public User getUserByID(String id) {
        return entityManager.find(User.class, id);
    }

    /**
     * @param userRequest
     * @return
     * Returns the user created, after passing in a UserRequest domain. The UserRequest domain excludes the id parameter.
     */
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

    /**
     * @param criteria
     * @return
     * Returns all the users based of parameter criteria.
     */
    @Override
    public List<User> findUsers(Map<String, String> criteria) {

        if (criteria.containsKey("role")){
            // If the criteria is just role
            return entityManager.createQuery("select u from User u where u.role = :role", User.class)
                    .setParameter("role", criteria.get("role"))
                    .getResultList();
        }

        else {
            // If the criteria is just name
            return entityManager.createQuery("select u from User u where u.name = :name", User.class)
                    .setParameter("name", criteria.get("name"))
                    .getResultList();
        }

    }

    /**
     * @param id
     * @param changes
     * @return
     * User with the updated changes either to role, name or both.
     */
    @Override
    public User updateUser(String id, Map<String, String> changes) {

        boolean role_exists = changes.containsKey("role");
        boolean name_exists = changes.containsKey("name");

        // Case where just the role information needs to be updated
        if (role_exists && !name_exists){
            entityManager.createQuery("update User set role=:role where id=:id")
                    .setParameter("role", changes.get("role"))
                    .setParameter("id", id)
                    .executeUpdate();
        }

        // Case where only name needs to be updated
        else if (name_exists && !role_exists){
            entityManager.createQuery("update User set name=:name where id=:id")
                    .setParameter("name", changes.get("name"))
                    .setParameter("id", id)
                    .executeUpdate();
        }

        // Case where both fields need to be updated.
        else {
            entityManager.createQuery("update User set name=:name, role=:role where id=:id")
                    .setParameter("name", changes.get("name"))
                    .setParameter("role", changes.get("role"))
                    .setParameter("id", id)
                    .executeUpdate();
        }
        return getUserByID(id);
    }

    /**
     * @param id
     * @return
     * Deletes user and returns an int which indicates if the user has been deleted. If the value returned is >0 then
     * the user has been deleted.
     */
    @Override
    public int deleteUser(String id) {

        String query = "delete from User where id=:id";
        Query deleteQuery = entityManager.createQuery(query);
        deleteQuery.setParameter("id", id);
        return deleteQuery.executeUpdate();
    }


    /**
     * Used when creating the user.
     * @return
     * Returns a random hex string for the user id. Based off readme from the challenge.
     */
    private String getRandomHexString(){
        Random random = new Random();
        int val = random.nextInt();
        String Hex = new String();
        return Hex = Integer.toHexString(val);
    }
}
