package com.statflo.challenge.rest_api;

//import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
}
/*
{
    Collection<User> findByRoleCode(String role);
	User findByIDCode(String id);
}
*/