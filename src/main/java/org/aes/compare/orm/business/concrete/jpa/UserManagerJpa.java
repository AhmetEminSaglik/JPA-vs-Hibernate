package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.UserService;
import org.aes.compare.orm.model.User;

public class UserManagerJpa implements UserService {

    public UserManagerJpa() {
        System.out.println("JPA is activated");
    }

    @Override
    public User save(User user) {
        return saveUser(user);
    }

    private User saveUser(User user) {
        JpaImplementation<User> impl = new JpaImplementation<>();
        impl.save(user);
        return impl.find(User.class, user.getId());
    }
}
