package com.roy.jpademo.dao.many2dao;

import javax.persistence.*;

@Entity
@Table(name = "JPA_Department")
public class Department {

    private Integer id;
    private String deptName;
    private Manager manager;

    @Id
    @GeneratedValue
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "DEPT_NAME")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * 使用OneToOne映射一对一关联关系
     * 若需要在当前数据表中添加主键，则需要使用JoinColumn来进行映射，1-1的关联关系，需要添加unique=true
     */
    @JoinColumn(name = "MANAGER_ID", unique = true)
    @OneToOne(fetch = FetchType.LAZY)
    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
}
