package org.aes.compare.orm;

import org.aes.compare.orm.business.abstracts.UserService;
import org.aes.compare.orm.business.concrete.UserManagerHibernate;
import org.aes.compare.orm.model.User;

public class Main {
    static UserService userService = new UserManagerHibernate();


    public static void main(String[] args) {
        User user = initUser();
        user = userService.save(user);
        System.out.println("Saved User : " + user);
    }

    public static User initUser() {
        int counter = 3;
        User user = new User();
        user.setId(counter);
        user.setUsername("user" + counter);
        user.setPassword("pass" + counter);
        return user;
    }
}