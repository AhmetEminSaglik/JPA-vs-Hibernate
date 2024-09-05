package org.ahmeteminsaglik.orm.config;

import org.ahmeteminsaglik.orm.business.abstracts.AddressService;
import org.ahmeteminsaglik.orm.business.abstracts.CourseService;
import org.ahmeteminsaglik.orm.business.abstracts.ExamResultService;
import org.ahmeteminsaglik.orm.business.abstracts.StudentService;
import org.ahmeteminsaglik.orm.business.concrete.hibernate.AddressServiceImplHibernate;
import org.ahmeteminsaglik.orm.business.concrete.hibernate.CourseServiceImplHibernate;
import org.ahmeteminsaglik.orm.business.concrete.hibernate.ExamResultServiceImplHibernate;
import org.ahmeteminsaglik.orm.business.concrete.hibernate.StudentServiceImplHibernate;
import org.ahmeteminsaglik.orm.business.concrete.hibernate.abstracts.HibernateImplementation;
import org.ahmeteminsaglik.orm.business.concrete.jpa.AddressServiceImplJPA;
import org.ahmeteminsaglik.orm.business.concrete.jpa.CourseServiceImplJPA;
import org.ahmeteminsaglik.orm.business.concrete.jpa.ExamResultServiceImplJPA;
import org.ahmeteminsaglik.orm.business.concrete.jpa.StudentServiceImpJPA;
import org.ahmeteminsaglik.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.ahmeteminsaglik.orm.business.concrete.orm.ORMImplementation;
import org.ahmeteminsaglik.orm.model.enums.configfile.EnumHibernateConfigFile;
import org.ahmeteminsaglik.orm.model.enums.configfile.EnumJPAConfigFile;

public class ORMConfigSingleton {
    private static AddressService addressService;
    private static StudentService studentService;
    private static CourseService courseService;
    private static ExamResultService examResultService;

    public static String getCurrentORMName() {
        return ORMImplementation.getCurrentORMToolName();
    }

    public static void enableJPA() {
        enableJPA(EnumJPAConfigFile.REAL_PRODUCT);
    }

    public static void enableJPA(EnumJPAConfigFile persistanceUnit) {
        JpaImplementation.setPersistanceUnit(persistanceUnit);
        addressService = new AddressServiceImplJPA();
        studentService = new StudentServiceImpJPA();
        courseService = new CourseServiceImplJPA();
        examResultService = new ExamResultServiceImplJPA();
    }

    public static void enableHibernate() {
        enableHibernate(EnumHibernateConfigFile.REAL_PRODUCT);
    }

    public static void enableHibernate(EnumHibernateConfigFile configFile) {
        HibernateImplementation.setHibernateConfigFile(configFile);
        addressService = new AddressServiceImplHibernate();
        studentService = new StudentServiceImplHibernate();
        courseService = new CourseServiceImplHibernate();
        examResultService = new ExamResultServiceImplHibernate();
    }

    public AddressService getAddressService() {
        return addressService;
    }

    public StudentService getStudentService() {
        return studentService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public ExamResultService getExamResultService() {
        return examResultService;
    }
}
