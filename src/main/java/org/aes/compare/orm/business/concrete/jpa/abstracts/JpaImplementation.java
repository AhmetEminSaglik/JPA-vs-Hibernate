package org.ahmeteminsaglik.orm.business.concrete.jpa.abstracts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.ahmeteminsaglik.orm.business.concrete.orm.ORMImplementation;
import org.ahmeteminsaglik.orm.model.enums.configfile.EnumJPAConfigFile;
import org.ahmeteminsaglik.orm.model.enums.configfile.EnumORMConfigFile;

public abstract class JpaImplementation<T> extends ORMImplementation {

    private static EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;

    public static void setPersistanceUnit(EnumJPAConfigFile persistanceUnit) {
        enumORMConfigFile = EnumORMConfigFile.valueOf(persistanceUnit.getName());
        setConfigFile(enumORMConfigFile);
    }

    @Override
    protected void createFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory(enumORMConfigFile.getFileName());
    }

    @Override
    protected void createTransaction() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
    }

    @Override
    protected void initializeTransaction() {
        printInitializingInfo();

        createFactory();
        createTransaction();

        printInitializedSuccessfully();
    }

    @Override
    protected void commit() {
        printClosingInfo();
        entityManager.getTransaction().commit();
        close();
    }

    @Override
    protected void close() {
        entityManager.close();
        printClosedSuccessfully();
    }
}
