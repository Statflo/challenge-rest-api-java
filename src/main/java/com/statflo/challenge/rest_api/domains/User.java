package com.statflo.challenge.rest_api.domains;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name = "id", updatable = false , nullable = false)
    private String id;

    @Column(name = "user")
    private String name;

    @Column(name = "role")
    private String role;

    public User(){
    }

    public User(String name, String role){
        this.name = name;
        this.role = role;
    }


    public String getId() {
        return id;
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
        return "User [id= " + id + ", name= " + name + ", role= " + role + "]";
    }

    public void setId(String id) {
        this.id = id;
    }
}
