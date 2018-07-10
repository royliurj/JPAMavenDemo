package com.roy.jpademo.dao.many2dao;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "JPA_Customer")
public class Customer {
    private int id;
    private String name;
    private String lastName;
    private String email;
    private Set<Order> orders = new HashSet<Order>();

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Customer() {
    }

    public Customer(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     *使用OneToMany映射单向一对多的关系
     */
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.REMOVE}, mappedBy = "customer")
    //@JoinColumn(name = "CUSTOMER_ID")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}
