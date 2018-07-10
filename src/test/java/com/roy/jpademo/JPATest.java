package com.roy.jpademo;

import com.roy.jpademo.dao.many2dao.Customer;
import com.roy.jpademo.dao.many2dao.Department;
import com.roy.jpademo.dao.many2dao.Manager;
import com.roy.jpademo.dao.many2dao.Order;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;

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
    public void test(){

        System.out.println("success");
    }

    @Test
    public void testPersist() {
        Customer customer = new Customer("Roy", "last name", "email");

        Order order1 = new Order();
        order1.setOrderName("Order 1");

        Order order2 = new Order();
        order2.setOrderName("order 2");

        //设置关联关系
        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        order1.setCustomer(customer);
        order2.setCustomer(customer);

        entityManager.persist(customer);
        entityManager.persist(order1);
        entityManager.persist(order2);

        System.out.println("success");
    }

    @Test
    public void testOne2One(){

        Manager manager = new Manager();
        manager.setManagerName("AA");

        Department department = new Department();
        department.setDeptName("DEPT AA");

        manager.setDepartment(department);
        department.setManager(manager);

        entityManager.persist(manager);
        entityManager.persist(department);

        System.out.println("Success");
    }

    @Test
    public void testOne2OneFind(){
        Manager manager = entityManager.find(Manager.class,1);
        System.out.println(manager.getManagerName());

        System.out.println(manager.getDepartment().getClass());
    }
    @Test
    public void testOne2OneFind2(){
       Department department = entityManager.find(Department.class,1);
        System.out.println(department.getDeptName());

        System.out.println(department.getManager().getClass());
    }

//    @Test
//    public void testPersist() {
//        Customer customer = new Customer("Roy", "last name", "email");
//
//        Order order1 = new Order();
//        order1.setOrderName("Order 1");
//
//        Order order2 = new Order();
//        order2.setOrderName("order 2");
//
//        //设置关联关系
//        customer.getOrders().add(order1);
//        customer.getOrders().add(order2);
//
//        entityManager.persist(customer);
//        entityManager.persist(order1);
//        entityManager.persist(order2);
//
//        System.out.println("success");
//    }
//
//    @Test
//    public void testOne2ManyFind(){
//        Customer customer = entityManager.find(Customer.class,1);
//        System.out.println(customer.getName());
//
//        System.out.println(customer.getOrders().size());
//    }
//
//    @Test
//    public void testOne2ManyRemove(){
//        Customer customer = entityManager.find(Customer.class,1);
//        entityManager.remove(customer);
//        System.out.println("SUCCESS");
//    }

//    @Test
//    public void testMany2OnePersist(){
//        Customer customer = new Customer("Roy","last name","email");
//
//        Order order1 = new Order();
//        order1.setOrderName("Order 1");
//
//        Order order2 = new Order();
//        order2.setOrderName("order 2");
//
//        //设置关联关系
//        order1.setCustomer(customer);
//        order2.setCustomer(customer);
//
//        entityManager.persist(customer);
//        entityManager.persist(order1);
//        entityManager.persist(order2);
//
//        System.out.println("success");
//    }
//
//    @Test
//    public void testMany2OneFind(){
//        Order order = entityManager.find(Order.class,1);
//        System.out.println(order.getOrderName());
//
//        System.out.println(order.getCustomer().getName());
//    }
//
//    @Test
//    public void testMany2OneRemove(){
//        Order order = entityManager.find(Order.class,1);
//        entityManager.remove(order);
//    }
//
//    @Test
//    public void testMany2OneUpdate(){
//        Order order = entityManager.find(Order.class,2);
//
//        order.getCustomer().setEmail("roy@123.com");
//    }
}
