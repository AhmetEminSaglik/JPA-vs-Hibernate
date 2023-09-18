package org.aes.compare.orm.business.concrete.hibernate;

import org.aes.compare.orm.business.abstracts.UserService;
import org.aes.compare.orm.model.User;

public class UserManagerHibernate implements UserService {
    public UserManagerHibernate() {
        System.out.println("Hibernate is activated");
    }

    @Override
    public User save(User user) {
        return saveUser(user);
    }

    private User saveUser(User user) {
        HibernateImplementation<User> impl = new HibernateImplementation<>();
        impl.save(user);
        return impl.find(User.class, user.getId());
    }
}
