package com.roy.jpademo;

import com.roy.jpademo.springjpa.entities.Person;
import com.roy.jpademo.springjpa.service.PersonService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class SpringJpaTest {

    private ApplicationContext ctx = null;
    private PersonService personService = null;

    {
        ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        personService = ctx.getBean(PersonService.class);
    }
    @Test
    public void testSavePersons(){
        Person p1 = new Person();
        p1.setName("111");

        Person p2 = new Person();
        p2.setName("222");

        personService.savePsersons(p1,p2);
    }

    @Test
    public void  testDataSource() throws SQLException {
        DataSource dataSource = ctx.getBean(DataSource.class);

        System.out.println(dataSource.getConnection());

    }


}
