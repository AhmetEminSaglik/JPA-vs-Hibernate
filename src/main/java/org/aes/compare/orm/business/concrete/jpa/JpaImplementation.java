package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaImplementation<T> {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    void createFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
    }

    private void createTransaction() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    public void commit() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void save(T t) {
        createFactory();
        createTransaction();
        entityManager.persist(t);
        commit();
    }

    public T find(Class<T> clazz, int id) {
        createFactory();
        createTransaction();
        T t = entityManager.find(clazz, id);
        commit();
        return t;
    }


}
