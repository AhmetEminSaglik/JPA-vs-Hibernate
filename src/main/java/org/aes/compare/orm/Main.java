package org.aes.compare.orm;

import org.aes.compare.orm.business.abstracts.UserService;
import org.aes.compare.orm.business.concrete.hibernate.UserManagerHibernate;
import org.aes.compare.orm.business.concrete.jpa.UserManagerJpa;
import org.aes.compare.orm.model.User;

public class Main {

    public static void main(String[] args) {
     /* NOTE :If the table is not created in DB, then JPA will cause an error about the table not existing,
       But hibernate will create a table if the table is not created.*/
        processForHibernate();
        processForJpa();
    }

    public static void processForHibernate() {
        User user = new User();
        user.setUsername("Hibernate username");
        user.setPassword("Hibernate password");
        UserService userService = new UserManagerHibernate();
        userService.save(user);
        System.out.println("Saved User : " + user);
    }

    public static void processForJpa() {
        User user = new User();
        user.setUsername("JPA user");
        user.setPassword("JPA pass");
        UserService userService = new UserManagerJpa();
        userService.save(user);
        System.out.println("Saved User : " + user);
    }
}