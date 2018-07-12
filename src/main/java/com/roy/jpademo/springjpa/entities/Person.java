package com.roy.jpademo.springjpa.entities;

import javax.persistence.*;

@Entity
@Table(name = "JPA_Person")
public class Person {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "name")
    private String name;

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
}
