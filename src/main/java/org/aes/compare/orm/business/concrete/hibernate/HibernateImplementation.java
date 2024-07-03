/*
package org.aes.compare.orm.business.concrete.hibernate;

import org.aes.compare.orm.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateImplementation<T> {
    private SessionFactory factory;
    private Session session;

    private void createFactory() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    private void createTransaction() {
        session = factory.getCurrentSession();
        session.beginTransaction();
    }

    private void commit() {
        session.getTransaction().commit();
        factory.close();
    }

    public void save(T t) {
        createFactory();
        createTransaction();
        try {
            session.persist(t);
            commit();
        } catch (Exception e) {
            System.out.println("Error Occurred: " + e.getMessage());
        }
    }

    public T find(Class<T> clazz, int id) {
        createFactory();
        createTransaction();
        T t = session.get(clazz, id);
        commit();
        return t;
    }


}
*/
