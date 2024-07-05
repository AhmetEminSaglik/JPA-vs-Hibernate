package org.aes.compare.orm.business.concrete.jpa.abstracts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public abstract  class JpaImplementation<T> {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

   private void createFactoryJPA() {
        entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
    }

    private void createTransactionJPA() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    static int initCounter = 0;
    static int closeCounter = 0;
    public  void initializeTransaction(){
        initCounter++;
       createFactoryJPA();
       createTransactionJPA();
        System.out.println(initCounter + "-)Entity is INITIALIZED: " + getClass().getSimpleName());
    }

    public synchronized void commit() {
        closeCounter++;
        entityManager.getTransaction().commit();
        entityManager.close();
        System.out.println(closeCounter + "-) Entity is CLOSED: " + getClass().getSimpleName());
    }


    public synchronized EntityManager getEntityManager() {
        return entityManager;
    }
}
