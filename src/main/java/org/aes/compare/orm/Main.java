package org.aes.compare.orm;

import org.aes.compare.orm.business.abstracts.UserService;
import org.aes.compare.orm.business.concrete.hibernate.UserManagerHibernate;
import org.aes.compare.orm.business.concrete.jpa.UserManagerJpa;
import org.aes.compare.orm.model.User;

public class Main {
    //    static UserService userService = new UserManagerHibernate();
    static UserService userService = new UserManagerJpa();


    public static void main(String[] args) {
        processForHibernate();
        processForJpa();
    }

    public static void processForHibernate() {
        int id = 10;
        User user = new User();
        user.setId(id);
        user.setUsername("Hibernate user" + id);
        user.setPassword("pass" + id);
        userService = new UserManagerHibernate();
        userService.save(user);
        System.out.println("Saved User : " + user);
    }

    public static void processForJpa() {
        int id = 20;
        User user = new User();
        user.setId(id);
        user.setUsername("JPA user" + id);
        user.setPassword("pass" + id);
        userService = new UserManagerHibernate();
        userService.save(user);
        System.out.println("Saved User : " + user);
    }
}