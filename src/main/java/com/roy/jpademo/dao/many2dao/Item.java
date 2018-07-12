package com.roy.jpademo.dao.many2dao;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "JPA_Item")
public class Item {
    private Integer id;
    private String itemName;
    private Set<Category> categories = new HashSet<Category>();

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "ITEM_NAME")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 使用ManyToMany映射多对多的关联关系
     * 使用JoinTable映射中间表
     * 1，name指向中间表的名字
     * 2，joinColumns 映射当前类所在的表在中间表的外键
     *    name：指定外键列的列名
     *    referencedColumnName：指定外键列关联当前表的哪一列
     * 3，inverseJoinColumns 映射关联的类坐在中间表的外键
     */
    @ManyToMany
    @JoinTable(name = "ITEM_CATEGORY",joinColumns = {@JoinColumn(name = "ITEM_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID",referencedColumnName = "ID")})
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
