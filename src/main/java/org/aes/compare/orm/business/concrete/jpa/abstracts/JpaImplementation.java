package org.aes.compare.orm.business.concrete.jpa.abstracts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.aes.compare.orm.business.concrete.orm.ORMImplementation;
import org.aes.compare.orm.model.enums.configfile.EnumJPAConfigFile;
import org.aes.compare.orm.model.enums.configfile.EnumORMConfigFile;
import org.aes.compare.orm.utility.ColorfulTextDesign;

public abstract class JpaImplementation<T> extends ORMImplementation {

    private static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    public static void setPersistanceUnit(EnumJPAConfigFile persistanceUnit) {
        enumORMConfigFile = EnumORMConfigFile.valueOf(persistanceUnit.getName());
        setConfigFile(enumORMConfigFile);
    }

    @Override
    protected void createFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory(enumORMConfigFile.getName());
    }

    @Override
    protected void createTransaction() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    @Override
    protected void initializeTransaction() {
        initEntityCounter++;
        createFactory();
        createTransaction();
        String text = initEntityCounter + "-) Entity is INITIALIZED: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorText(text));
    }

    @Override
    protected void commit() {
        closeEntityCounter++;
        entityManager.getTransaction().commit();
        entityManager.close();
        String text = closeEntityCounter + "-) Entity is CLOSED: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorText(text));
    }

}
