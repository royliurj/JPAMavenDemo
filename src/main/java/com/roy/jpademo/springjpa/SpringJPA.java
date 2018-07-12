package com.roy.jpademo.springjpa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.SQLException;

public class SpringJPA {


    public static void main(String[] args) throws SQLException {
        ApplicationContext ctx = null;
        ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        DataSource dataSource = ctx.getBean(DataSource.class);
        System.out.println(dataSource);
    }

}
