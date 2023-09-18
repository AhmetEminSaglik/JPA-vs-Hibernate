package org.aes.compare.orm.business.concrete;

import org.aes.compare.orm.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateImplementation<T> {
    private SessionFactory factory;


    private void createSessionFactory() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
    }

    public void saveData(T t) {
        createSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            System.out.println("Data will be saved : " + t);
            session.beginTransaction();
            session.persist(t);
            session.getTransaction().commit();
        } catch (
                Exception e) {
            System.out.println("Entered Catch");
            System.out.println("Error Ocured: " + e.getMessage());
        } finally {
            System.out.println("Entered Finally ");
            closeSessionFactory();
        }
    }

    public T getData(Class<T> clazz, int id) {
        createSessionFactory();
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        T t = session.get(clazz, id);
        session.getTransaction().commit();
        closeSessionFactory();
        return t;

    }

    private void closeSessionFactory() {
        factory.close();
    }

}
