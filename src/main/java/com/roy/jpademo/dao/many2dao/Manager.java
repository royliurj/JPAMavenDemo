package com.roy.jpademo.dao.many2dao;

import javax.persistence.*;

@Entity
@Table(name = "JPA_Manager")
public class Manager {

    private Integer id;
    private String managerName;
    private Department department;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "MANAGER_NAME")
    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    //对于不维护关联关系（没有外键的一方），需要使用mappedBy指定需要使用对方的哪个属性
    @OneToOne(mappedBy = "manager")
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
