package org.aes.compare.orm.business.concrete.hibernate.abstracts;

import org.aes.compare.orm.business.concrete.orm.ORMImplementation;
import org.aes.compare.orm.model.enums.configfile.EnumHibernateConfigFile;
import org.aes.compare.orm.model.enums.configfile.EnumORMConfigFile;
import org.aes.compare.orm.utility.ColorfulTextDesign;
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
                .configure(enumORMConfigFile.getName())
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

        initEntityCounter++;
        createFactory();
        createTransaction();

        printInitializedSuccessfully();
    }

    @Override
    protected void commit() {
        printClosingInfo();

        String explanation = initEntityCounter + "-) Entity is closing: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix(explanation));

        closeEntityCounter++;
        session.getTransaction().commit();
        factory.close();
        explanation = closeEntityCounter + "-) Entity is closed successfully : " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorTextWithPrefix(explanation));

        printClosedSuccessfully();
    }

}
