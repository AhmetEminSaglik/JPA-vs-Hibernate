package org.aes.compare.orm.business.concrete.jpa;

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
    public  void initializeTransaction(){
       createFactoryJPA();
       createTransactionJPA();
    }

    public void commit() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public void saveJPA(T t) {
        initializeTransaction();
        entityManager.persist(t);
        commit();
    }

    public T find(Class<T> clazz, int id) {
        initializeTransaction();
        T t = entityManager.find(clazz, id);
        commit();
        return t;
    }

    public EntityManager getEntityManager() {
       if(entityManager==null){
           initializeTransaction();
       }
        return entityManager;
    }
}
