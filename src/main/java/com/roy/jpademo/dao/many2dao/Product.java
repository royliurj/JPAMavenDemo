//package com.roy.jpademo.dao.many2dao;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "t_product")
//public class Product {
//  @Id
//  @GeneratedValue
//  private Long id;
//  private String name;
//  // 多对一：optional=false表示外键type_id不能为空
//  @ManyToOne(optional = true)
//  @JoinColumn(name = "type_id")
//  private ProductType type;
//
//  public Product() {
//  }
//
//  public Product(String name, ProductType type) {
//    this.name = name;
//    this.type = type;
//  }
//
//  public Long getId() {
//    return id;
//  }
//
//  public void setId(Long id) {
//    this.id = id;
//  }
//
//  public String getName() {
//    return name;
//  }
//
//  public void setName(String name) {
//    this.name = name;
//  }
//
//  public ProductType getType() {
//    return type;
//  }
//
//  public void setType(ProductType type) {
//    this.type = type;
//  }
//
//  @Override
//  public String toString() {
//    return "Product [id=" + id + ", name=" + name + "]";
//  }
//
//}

