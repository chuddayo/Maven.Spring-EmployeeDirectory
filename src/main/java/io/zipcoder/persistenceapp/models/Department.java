package io.zipcoder.persistenceapp.models;

import javax.persistence.*;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Integer managerID;

    public Department() {
    }

    public Department(String name, Integer managerID) {
        this.name = name;
        this.managerID = managerID;
    }

    public Department(Integer id, String name, Integer managerID) {
        this.id = id;
        this.name = name;
        this.managerID = managerID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getManagerID() {
        return managerID;
    }

    public void setManagerID(Integer managerID) {
        this.managerID = managerID;
    }
}
