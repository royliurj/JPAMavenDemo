JPA：Java Persistence API 用于对象持久化的API

JPA和Hibernate的关系：JPA是Hibernate的一个抽象,类似于JDBC和JDBC驱动的关系

3方面的技术：
1. ORM映射元数据
2. JPA的API
3. 查询语言JPQL

# HelloWorld
1. persistence.xml配置
```
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="http://java.sun.com/xml/ns/persistence"  version="2.0">
    <persistence-unit name="NewPersistenceUnit">
        <!--
            配置使用什么ORM产品为JPA的实现
            1，实际上配置的是javax.persistence.spi.PersistenceProvider接口的实现类
            2，若JPA项目只有一个JPA的实现产品，则可以不配置该节点
        -->
        <!--<provider>org.hibernate.ejb.HibernatePersistence</provider>-->
        <properties>
            <!--数据库的基本配置-->
            <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
            <property name="javax.persistence.jdbc.url" value="jdbc:mysql:///jpa"/>
            <property name="javax.persistence.jdbc.user" value="root"/>
            <property name="javax.persistence.jdbc.password" value="root"/>
            <!--配置JPA产品的基本属性-->
            <property name="hibernate.format_sql" value="true"/>
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.hbm2ddl.auto" value="update"/>
        </properties>
    </persistence-unit>
</persistence>
```
2. 实体类
```
@Entity(name = "JPA_Customer")
public class Customer {
    private int id;
    private String name;
    private String lastName;
    private String email;

    @Column(name = "ID")//若与属性名相同，则不用写
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
```
3. 测试
```
    public static void main(String[] args) {
        //1，创建EntityManagerFactory
        //这里的参数为配置文件中定义的name
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("NewPersistenceUnit");

        //2，创建EntityManager
        EntityManager entityManager = factory.createEntityManager();

        //3，开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        //4，持久化操作
        Customer customer = new Customer();
        customer.setName("Roy");
        customer.setLastName("Liu");
        customer.setEmail("11@11");

        entityManager.persist(customer);

        //5,提交事务
        transaction.commit();

        //6,关闭资源
        entityManager.close();
        factory.close();
    }
```

# 注解

1. @Entity  
映射表和类的关系：不指定name属性，表名和类名一样，指定则用namne命名表
2. @Table （同@Entity）
3. @Id (主键)
4. @GeneratedValue  
生成主键的方式：
- IDENTITY： 数据库ID自增长的方式，Oracle不支持
- AUTO： JPA自动选择合适的策略
- SEQUENCE：通过序列产生主键，通过@SequenceGenerator指定序列名，Mysql不支持
- TABLE：通过表产生主键，框架借由表模拟序列产生主键，使用该策略可以使应用更易于数据库移植
```
@GeneratedValue(strategy = GenerationType.AUTO)
@Id
public int getId(){
    return id;
}
```
5. @Basic (默认没有加注解，使用的就是@Basic)
6. @Column (映射列)
```
    @Column(name = "LAST_NAME",length = 50,nullable = false)
    public String getLastName() {
        return lastName;
    }
```
7. @Transient  
表示该属性并非一个到数据库表的字段的映射
```
    @Transient
    public String getInfo(){
        return "lastName: " + lastName + ", name: " + name;
    }
```
8. @Temporal
```
    //TemporalType.Time 时间
     
    //时间戳
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return createTime;
    }
    
    //日期格式
    @Temporal(TemporalType.DATE)
    public Date getBirth() {
        return birth;
    }
```

# Table主键生成策略
1. 数据表   
说明：PK_Name：主键名， PK_Value：主键值  
![image](https://note.youdao.com/yws/public/resource/a5ac814a8685730fc2a92360e1f2c032/xmlnote/C3CE1B264AB1461D83EF92DEBA211B80/28791)
2. 注解  
说明：
- 首先采取表策略模式
- 设置注解TableGenerator注解
- name：与generator定义相同
- table：与数据库中的表名匹配
- pkColumnName：指向表中的列名“PK_Name”
- pkCOlumnValue: 指向行中定义的主键值
- valueColumnName: 指向表中的列名“PK_Value”
- allocationSize: 新增记录时增长的种子
```
    @TableGenerator(name = "ID_GENERATOR",
            table = "jpa_id_generators",
            pkColumnName = "PK_Name",
            pkColumnValue = "CUSTOMER_ID",
            valueColumnName = "PK_Value",
            allocationSize = 100
    )
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "ID_GENERATOR")
    @Id
    public int getId() {
        return id;
    }
```
# EntityManager
### find 
类似于Hibernate的get方法  
执行find的同时，就已经发送了sql语句
```
    @Test
    public void testFind(){
        Customer customer = entityManager.find(Customer.class,1);
        System.out.println("-----------------");
        System.out.println(customer);
    }
```

### getReference
相当于Hibernate的load方法（懒加载）
```
    @Test
    public void testGetReference(){
        //返回Customer的一个代理，为真正的执行sql脚本
        Customer customer = entityManager.getReference(Customer.class,1);
        System.out.println("-----------------");
        //用到customer的时候在去执行sql脚本
        System.out.println(customer);
    }
```

### persist
类似于Hibernate的save方法，使对象的临时状态变为持久化状态  
与Hibernate中save的不同，如果对象有ID则不会执行insert操作，会抛出异常
```
        Customer customer = new Customer();
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setEmail("111");
        customer.setLastName("22");
        customer.setName("Roy");

        entityManager.persist(customer);
        System.out.println(customer.getId());
```

### remove
类似于Hibernate的delete方法，注意该方法只能移除持久化对象，而Hibernate的delete方法还可以移除游离对象
```
    @Test
    public void  testRemove(){

        //不能删除游离对象
        Customer customer1 = new Customer();
        customer1.setId(1);
        entityManager.remove(customer1);

        //可以删除持久化对象
        Customer customer = entityManager.find(Customer.class,1);
        entityManager.remove(customer);
    }
```

### merge
saveOrUpdate
1. 临时对象  
不对临时对象进行处理，返回新的对象（新的对象有id）
2. 游离对象  
    - id在数据库中不存在，则新建
    - id在数据库中存在，则更新

### 其它方法
1. flush  
发送语句到数据库，使数据库与缓存中的数据保持一致
2. setFlushMode
3. getFlushMode
4. refresh  
用数据库中实体的值更新到实体对象
5. contains
6. isOpen


# EntityTransaction
1. getTransaction()
2. begion
3. commit/rollback
4. isActive 当前是否是否活动，活动的话，可以调用commit或者rollback


# 映射关联关系
### 单向多对一(ManyToOne)
1. 插入时，先插入导航属性（一方），在插入多方，生成的sql，效率比较高
2. 默认使用饿汗模式（全部加载出来）加载数据
```
    /**
     * 使用ManyToOne来映射多对一的关系, fetch加载模式，默认是FetchType.EAGER
     * 使用JoinColumn来映射外键， name属性是外键的键名
     */
    @JoinColumn(name = "CUSTOMER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    public Customer getCustomer() {
        return customer;
    }
```

### 单向一对多(OneToMany)  
1. 一定会产生更新语句
2. 默认使用懒加载的方式（先获取一的数据，用到多的时候，在重新加载），也可以修改加载策略
3. 删除一的时候，会把多的外键置空。也可以设置cascade设置默认的删除策略
```
    private Set<Order> orders = new HashSet<Order>();
    
    /**
     *使用OneToMany映射单向一对多的关系
     */
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "CUSTOMER_ID")
    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
```

### 双向多对一
单向多对一和一对多的组合  
在一的一端使用mappedBy可以有效的提高sql执行效率
```
    //若使用mappedBy（属性指向多的一端的名称）， 则不可以使用JoinColumn
    @OneToMany(fetch = FetchType.EAGER,cascade = {CascadeType.REMOVE}, mappedBy = "customer")
    public Set<Order> getOrders() {
        return orders;
    }
    
    
    @JoinColumn(name = "CUSTOMER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    public Customer getCustomer() {
        return customer;
    }
```

### 双向一对一
1. 添加对象时，先保存没有关联关系的一方（此例先保存Manager）
2. 获取有关联关系的对象（Department）时，默认是饿汗加载模式，可以修改成LAZY模式
3. 获取没有关联关系的对象（Manager）时，默认也是饿汗模式，修改成Lazy模式后，依然会发送sql语句，获取Department的对象（默认一条left join 的sql，懒汉模式两条sql）
```
    //Department类
    private Manager manager;
    /**
     * 使用OneToOne映射一对一关联关系
     * 若需要在当前数据表中添加主键，则需要使用JoinColumn来进行映射，1-1的关联关系，需要添加unique=true（维护关联关系的一方）
     */
    @JoinColumn(name = "MANAGER_ID", unique = true)
    @OneToOne
    public Manager getManager() {
        return manager;
    }
    
    
    //Manager类
    //对于不维护关联关系（没有外键的一方），需要使用mappedBy指定需要使用对方的哪个属性
    @OneToOne(mappedBy = "manager")
    public Department getDepartment() {
        return department;
    }
```

### 双向多对多  
1. 必须由一方放弃关联关系
2. 
