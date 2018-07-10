package com.roy.jpademo.dao.many2dao;

import javax.persistence.*;

@Entity
@Table(name = "JPA_Order")
public class Order {

    private Integer id;
    private String orderName;
    private Customer customer;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "ORDER_NAME")
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    /**
     * 使用ManyToOne来映射多对一的关系, fetch加载模式，默认是FetchType.EAGER
     * 使用JoinColumn来映射外键， name属性是外键的键名
     */
    @JoinColumn(name = "CUSTOMER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
