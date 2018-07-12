package com.roy.jpademo.springjpa.service;

import com.roy.jpademo.springjpa.dao.PersonDao;
import com.roy.jpademo.springjpa.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    @Transactional
    public void savePsersons(Person p1, Person p2){
        personDao.save(p1);

        int i = 10/0;
        personDao.save(p2);
    }
}
