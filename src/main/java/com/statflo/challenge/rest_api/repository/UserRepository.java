package com.statflo.challenge.rest_api.repository;

import com.statflo.challenge.rest_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String id);

    List<User> findByRole(String role);

    List<User> findByName(String name);

    List<User> findByNameAndRole(String name, String role);

    List<User> findByIdAndRole(String id, String role);

    List<User> findByIdAndName(String id, String name);

    List<User> findByIdAndNameAndRole(String id, String name, String role);
}