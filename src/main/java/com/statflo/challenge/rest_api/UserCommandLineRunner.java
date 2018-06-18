package com.statflo.challenge.rest_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class UserCommandLineRunner implements CommandLineRunner{
	
	@Override
	public void run(String... args) throws Exception 
        {
            for(User u:this.userRepo.findAll())
			System.out.println(u.toString());
            
	}
	
	@Autowired
	UserRepository userRepo;
}
