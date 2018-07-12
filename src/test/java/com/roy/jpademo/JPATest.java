package com.roy.jpademo;

import com.roy.jpademo.dao.many2dao.*;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.hibernate.jpa.QueryHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

public class JPATest {

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    EntityTransaction entityTransaction;

    @Test
    public void testJPQLHello(){
        String jpql = "select c from Customer c where c.email = ?";

        Query query = entityManager.createQuery(jpql);
        query.setParameter(1,"email2");

        List<Customer> customers = query.getResultList();
        System.out.println(customers.size());

        for (Customer c:customers) {
            System.out.println(c);
        }
    }

    @Test
    public void test123(){
        String jpql = "select c from Customer c left outer join fetch c.orders where c.id = ?";
        Query query = entityManager.createQuery(jpql);
        Customer customer = (Customer) query.setParameter(1,1).getSingleResult();

        System.out.println(customer.getName());
        System.out.println(customer.getOrders().size());
    }

    /**
     * 查询所有Id=1的Customer的Order
     */
    @Test
    public void testSubQuery(){
        String jpql = "select o from Order o where o.customer = (select c from Customer c where c.id = ?)";
        Query query = entityManager.createQuery(jpql);
        List<Order> orders = query.setParameter(1,1).getResultList();

        System.out.println(orders);
    }
    @Test
    public void testJQPLFunction(){
        String jpql = "select upper(c.email) from Customer c where c.id = ?";
        Query query = entityManager.createQuery(jpql);
        String email = (String) query.setParameter(1,1).getSingleResult();

        System.out.println(email);
    }

    @Test
    public void testJPQLExecuteUpdate(){
        String jpql = "update Customer c set c.email = ? Where c.id = ?";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1,"1232@123.com").setParameter(2,1).executeUpdate();

        System.out.println("success");
    }

    @Test
    public void testJPQLPartlyProperties(){
        String jpql = "select new Customer(c.id, c.email) from Customer c";

        List result = entityManager.createQuery(jpql).getResultList();

        System.out.println(result);
    }

    @Test
    public void testJPQLNamedQuery(){
        Query query = entityManager.createNamedQuery("testNamedQuery").setParameter(1,1);
        Customer result = (Customer) query.getSingleResult();
        System.out.println(result);
    }

    @Test
    public void testJPQLNativeQuery(){
        String sql = "select name from jpa_customer where id = ?";
        Query query = entityManager.createNativeQuery(sql).setParameter(1,1);

        Object result = query.getSingleResult();
        System.out.println(result);
    }

    @Test
    public void testJPQLOrderBy(){
        String jpql = "select new Customer(c.id, c.email) from Customer c order by c.id desc";
        Query query = entityManager.createQuery(jpql);

        List result = query.getResultList();

        System.out.println(result);
    }

    @Test
    public void testJPQLQueryCache(){

        String jpql = "select new Customer(c.id, c.email) from Customer c";
        Query query = entityManager.createQuery(jpql).setHint(org.hibernate.jpa.QueryHints.HINT_CACHEABLE,true);

        List result = query.getResultList();

        System.out.println(result);
        query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE,true);
        result = query.getResultList();

        System.out.println(result);
    }

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
    public void testMerge(){
        Customer customer = entityManager.find(Customer.class,1);
        customer.setName("123123");

        entityManager.merge(customer);
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

    @Test
    public void testMany2Many(){

        Item item1 = new Item();
        item1.setItemName("Item1");

        Item item2 = new Item();
        item2.setItemName("item2");

        Category c1 = new Category();
        c1.setCategoryName("C1");
        Category c2 = new Category();
        c2.setCategoryName("C2");

        //设置关联关系
        item1.getCategories().add(c1);
        item1.getCategories().add(c2);

        item2.getCategories().add(c1);
        item2.getCategories().add(c2);

//        c1.getItems().add(item1);
//        c1.getItems().add(item2);
//
//        c2.getItems().add(item1);
//        c2.getItems().add(item2);

        entityManager.persist(item1);
        entityManager.persist(item2);
        entityManager.persist(c1);
        entityManager.persist(c2);

        System.out.println("Success");
    }

    @Test
    public void testMany2ManyFind(){
//        Item item = entityManager.find(Item.class,3);
//
//        System.out.println(item.getItemName());
//
//        System.out.println(item.getCategories().size());

        Category category = entityManager.find(Category.class,3);
        System.out.println(category.getCategoryName());

        System.out.println(category.getItems().size());
    }

    @Test
    public void testSecondLevelCache(){
        Customer customer = entityManager.find(Customer.class,1);

        entityTransaction.commit();
        entityManager.close();

        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer2 = entityManager.find(Customer.class,1);
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
