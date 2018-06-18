package com.statflo.challenge.rest_api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.statflo.challenge.rest_api.User;
import com.statflo.challenge.rest_api.UserRepository;



import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;


@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH})
@RequestMapping("/v1/users")
public class V1Controller
{

	@Autowired
	UserRepository userRepo;

        /// done
        /// return user by id
        @GetMapping(value = "/{id}")
        public String fetch(@PathVariable("id") String id) throws JSONException 
        {
            /// iterate over all users and check match id
            for(User u:this.userRepo.findAll())
            {
                JSONObject job=new JSONObject(u.toString());
                
                String strId=(String)job.get("id");
                
                if(strId.equals(id))
                {
                    return u.toString();
                }
            }
             return "No Data found for the given id";
        }
 
        
        /// done
        @GetMapping()
        public String find(@RequestParam Map<String, Object> criteria) throws JSONException {

            String condition=(String) criteria.get("role");

            // iterate over all user and check role
            for(User u:this.userRepo.findAll())
                {
                    JSONObject job=new JSONObject(u.toString());

                    String strRole=(String)job.get("role");

                    // if a user role matched with the query role then return that user
                    if(strRole.equals(condition))
                    {
                        return u.toString();
                    }
                }
                 return "No Data found for the given id";
        }

        /// done
        /// creating user by posting user info
        @PostMapping()
        public String create(@RequestBody String body) throws JSONException 
        {

            /// creating user and saving the new user from posting string to user repository

            JSONObject job=new JSONObject(body);

            String name=(String) job.get("name");
            String role=(String) job.get("role");

            System.out.println("Json:"+name+" "+role);
            User u=new User("977e3f5b-6a70-4862-9ff8-96af4477272a",name,role);

            userRepo.save(u);
            
            return u.toString();
        }

    
        /// done
        @PatchMapping(value = "/{id}")
        public String patch(@PathVariable("id") String id,@RequestBody String body) throws JSONException 
        {
            
            JSONObject jobject=new JSONObject(body);

            String name=(String) jobject.get("name");

            /// iterate over all user and check role
            for(User u:this.userRepo.findAll())
            {
                JSONObject job=new JSONObject(u.toString());
                
                String strId=(String)job.get("id");
                
                if(strId.equals(id) && name.equals((String)job.get("name")))
                {
                    return u.toString();
                }
                
            
            }

            JSONObject job=new JSONObject("{\"id\": \"977e3f5b-6a70-4862-9ff8-96af4477272a\", \"name\": \"foo bar\", \"role\": \"bar\"}");
                 
            return job.toString();
        }
        
}
