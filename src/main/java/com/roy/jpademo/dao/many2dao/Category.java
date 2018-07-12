package com.roy.jpademo.dao.many2dao;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "JPA_Category")
public class Category {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @ManyToMany(mappedBy = "categories")
    private Set<Item> items = new HashSet<Item>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
