package com.statflo.challenge.rest_api.domains;

import javax.persistence.*;


public class UserRequest {


    @Column(name = "user")
    private String name;

    @Column(name = "role")
    private String role;

    public UserRequest(){
    }

    public UserRequest(String name, String role){
        this.name = name;
        this.role = role;
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

    @Override
    public String toString() {
        return "User [name= " + name + ", role= " + role + "]";
    }
}
