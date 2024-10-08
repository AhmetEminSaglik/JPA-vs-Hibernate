package org.ahmeteminsaglik.orm.business.concrete.hibernate.abstracts;

import org.ahmeteminsaglik.orm.business.concrete.orm.ORMImplementation;
import org.ahmeteminsaglik.orm.model.enums.configfile.EnumHibernateConfigFile;
import org.ahmeteminsaglik.orm.model.enums.configfile.EnumORMConfigFile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class HibernateImplementation<T> extends ORMImplementation {

    protected SessionFactory factory;
    protected Session session;

    public static void setHibernateConfigFile(EnumHibernateConfigFile configFile) {
        enumORMConfigFile = EnumORMConfigFile.valueOf(configFile.getName());
        setConfigFile(enumORMConfigFile);
    }

    @Override
    protected void createFactory() {
        factory = new Configuration()
                .configure(enumORMConfigFile.getFileName())
                .addAnnotatedClass(getClass())
                .buildSessionFactory();
    }

    @Override
    protected void createTransaction() {
        session = factory.getCurrentSession();
        session.beginTransaction();
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
        session.getTransaction().commit();
        close();
    }

    @Override
    protected void close() {
        if (session.isOpen()) {
            factory.close();
            printClosedSuccessfully();
        }
    }
}
