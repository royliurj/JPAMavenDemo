package com.roy.jpademo.springjpa.dao;

import com.roy.jpademo.springjpa.entities.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

@Repository
public class PersonDao {

    //如何获取到和当前事务关联的EntityManager对象呢
    //通过PersistenceContext标记成员变量
    @PersistenceContext
    private EntityManager entityManager;
    public void save(Person person){
        entityManager.persist(person);
    }
}
