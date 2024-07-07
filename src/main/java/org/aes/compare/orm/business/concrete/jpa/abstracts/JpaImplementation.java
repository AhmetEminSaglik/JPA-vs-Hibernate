package org.aes.compare.orm.business.concrete.jpa.abstracts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.aes.compare.orm.model.EnumPersistanceType;
import org.aes.compare.orm.utility.ColorfulTextDesign;

public abstract class JpaImplementation<T> {
    private static EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;

    private static EnumPersistanceType persistanceUnit = EnumPersistanceType.REAL_PRODUCT;

    private static void createFactoryJPA() {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistanceUnit.getName());
    }

    public static void setPersistanceUnit(EnumPersistanceType enumPersistanceType) {
        persistanceUnit = enumPersistanceType;
    }


    private void createTransactionJPA() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    static int initCounter = 0;
    static int closeCounter = 0;

    public void initializeTransaction() {
        initCounter++;
        createFactoryJPA();
        createTransactionJPA();
        String text = initCounter + "-) Entity is INITIALIZED: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorText(text));
    }

    public synchronized void commit() {
        closeCounter++;
        entityManager.getTransaction().commit();
        entityManager.close();
        String text = closeCounter + "-) Entity is CLOSED: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorText(text));
    }


    public synchronized EntityManager getEntityManager() {
        return entityManager;
    }
}
