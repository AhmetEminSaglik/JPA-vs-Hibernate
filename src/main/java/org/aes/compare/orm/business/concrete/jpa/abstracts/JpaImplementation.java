package org.aes.compare.orm.business.concrete.jpa.abstracts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.aes.compare.orm.business.concrete.orm.ORMImplementation;
import org.aes.compare.orm.model.EnumHibernateConfigFile;
import org.aes.compare.orm.model.EnumORMConfigFile;
import org.aes.compare.orm.model.EnumJPAConfigFile;
import org.aes.compare.orm.utility.ColorfulTextDesign;

public abstract class JpaImplementation<T> extends ORMImplementation<T> {
    private static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    static int initCounter = 0;
    static int closeCounter = 0;

    public JpaImplementation() {
//        enumORMConfigFile = EnumORMConfigFile.REAL_PRODUCT_JPA;
//        setPersistanceUnit(EnumJPAConfigFile.REAL_PRODUCT);
    }

    //    private static void createFactoryJPA() {
//        entityManagerFactory = Persistence.createEntityManagerFactory(enumORMConfigFile.getName());
//    }
    public static void setPersistanceUnit(EnumJPAConfigFile persistanceUnit) {
//        EnumORMConfigFile.valueOf(persistanceUnit.getName()).getName();
        enumORMConfigFile = EnumORMConfigFile.valueOf(persistanceUnit.getName());
        setConfigFile(enumORMConfigFile);
    }

    @Override
    protected void createFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory(enumORMConfigFile.getName());
    }

  /*  @Override
    public void setConfigFile(EnumORMConfigFile configFile) throws InvalidORMConfigFileMatch{
        if (configFile.name().endsWith("JPA")) {
            enumORMConfigFile=configFile;
        } else {
            throw new InvalidORMConfigFileMatch(configFile, this);
        }

    }*/

    @Override
    protected void createTransaction() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    @Override
    protected void initializeTransaction() {
        initCounter++;
        createFactory();
        createTransaction();
        String text = initCounter + "-) Entity is INITIALIZED: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorText(text));
    }

    protected void commit() {
        closeCounter++;
        entityManager.getTransaction().commit();
        entityManager.close();
        String text = closeCounter + "-) Entity is CLOSED: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorText(text));
    }


}
