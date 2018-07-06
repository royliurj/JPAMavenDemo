package com.roy.jpademo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPATest {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction entityTransaction;

    @Before
    public void init(){
        entityManagerFactory = Persistence.createEntityManagerFactory("com.jege.jpa");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
    }

    @After
    public void destroy(){
        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void testFind(){
    }

    @Test
    public void testManyToOne(){

    }
}
