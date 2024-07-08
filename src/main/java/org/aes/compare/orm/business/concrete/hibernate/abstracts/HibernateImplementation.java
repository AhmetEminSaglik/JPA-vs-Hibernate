package org.aes.compare.orm.business.concrete.hibernate.abstracts;

import org.aes.compare.orm.business.concrete.orm.ORMImplementation;
import org.aes.compare.orm.model.EnumHibernateConfigFile;
import org.aes.compare.orm.model.EnumORMConfigFile;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class HibernateImplementation<T> extends ORMImplementation<T> {
    static int initCounter = 0;
    static int closeCounter = 0;
    protected SessionFactory factory;
    protected Session session;

    public HibernateImplementation() {
//        setHibernateConfigFile(EnumHibernateConfigFile.REAL_PRODUCT);
    }
    @Override
    protected void createFactory() {
        factory = new Configuration()
                .configure(enumORMConfigFile.getName())
                .addAnnotatedClass(getClass())
                .buildSessionFactory();
    }
//    protected abstract void createFactory();

//    protected static EnumHibernateConfigFile enumConfigFile = EnumHibernateConfigFile.REAL_PRODUCT;

    public static void setHibernateConfigFile(EnumHibernateConfigFile configFile) {
        enumORMConfigFile = EnumORMConfigFile.valueOf(configFile.getName());
        setConfigFile(enumORMConfigFile);
    }


 /*   @Override
    public void setConfigFile(EnumORMConfigFile configFile) throws InvalidORMConfigFileMatch {
        if (configFile.name().endsWith("HIBERNATE")) {
            enumORMConfigFile = configFile;
        } else {
            throw new InvalidORMConfigFileMatch(configFile, this);
        }
    }*/

    @Override
    protected void createTransaction() {
        session = factory.getCurrentSession();
        session.beginTransaction();
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
        session.getTransaction().commit();
        factory.close();
        String text = closeCounter + "-) Entity is CLOSED: " + getClass().getSimpleName();
        System.out.println(ColorfulTextDesign.getInfoColorText(text));
    }

    /*public void save(T t) {
        createFactory();
        createTransaction();
        try {
            session.persist(t);
            commit();
        } catch (Exception e) {
            System.out.println("Error Occurred: " + e.getMessage());
        }
    }

    public T find(Class<T> clazz, int id) {
        createFactory();
        createTransaction();
        T t = session.get(clazz, id);
        commit();
        return t;
    }*/


}
