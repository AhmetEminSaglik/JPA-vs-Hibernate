package org.aes.compare.orm.business.concrete;

import org.aes.compare.orm.business.abstracts.UserService;
import org.aes.compare.orm.model.User;

public class UserManagerHibernate implements UserService {

    @Override
    public User save(User user) {
        return saveUser(user);
    }

    private User saveUser(User user) {
        HibernateImplementation<User> impl = new HibernateImplementation<>();
        impl.saveData(user);
        return impl.getData(User.class, user.getId());
    }
}
