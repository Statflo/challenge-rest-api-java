package com.statflo.challenge.rest_api;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.json.JSONException;
import org.json.JSONObject;

@Entity 
public class User {
    
    private String id;
    private String name;
    private String role;
    
    //TODO for use in a currently unimplemented DB
    @Id @GeneratedValue
    private long uID;
    private static long count_uID = 1;
    
    public User() {
    	this.uID = -1;
    	this.id = "";
    	this.name = "";
    	this.role = "";
    }
    public User(String id, String name, String role) {
    	super();
    	this.uID = count_uID;
    	count_uID++;
    	
    	this.id = id;	
    	this.name = name;
    	this.role = role;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
    public long getUID() {
        return uID;
    }

    
	@Override
	public String toString() {                    
                JSONObject job=new JSONObject();
                try {
					job.put("id",id);
					job.put("name", name);
	                job.put("role",role);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
		return job.toString();
	}


}
