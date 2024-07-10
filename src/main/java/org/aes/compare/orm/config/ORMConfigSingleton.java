package org.aes.compare.orm.config;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.hibernate.AddressServiceImplHibernate;
import org.aes.compare.orm.business.concrete.hibernate.CourseServiceImplHibernate;
import org.aes.compare.orm.business.concrete.hibernate.ExamResultServiceImplHibernate;
import org.aes.compare.orm.business.concrete.hibernate.StudentServiceImplHibernate;
import org.aes.compare.orm.business.concrete.hibernate.abstracts.HibernateImplementation;
import org.aes.compare.orm.business.concrete.jpa.AddressServiceImplJPA;
import org.aes.compare.orm.business.concrete.jpa.CourseServiceImplJPA;
import org.aes.compare.orm.business.concrete.jpa.ExamResultServiceImplJPA;
import org.aes.compare.orm.business.concrete.jpa.StudentServiceImpJPA;
import org.aes.compare.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.aes.compare.orm.model.enums.configfile.EnumHibernateConfigFile;
import org.aes.compare.orm.model.enums.configfile.EnumJPAConfigFile;
import org.aes.compare.orm.model.enums.configfile.EnumORMConfigFile;

public class ORMConfigSingleton {
    private static AddressService addressService;
    private static StudentService studentService;
    private static CourseService courseService;
    private static ExamResultService examResultService;

    public static void enableJPA() {
        enableJPA(EnumJPAConfigFile.REAL_PRODUCT);
    }

    public static void enableJPA(EnumJPAConfigFile persistanceUnit) {
        JpaImplementation.setPersistanceUnit(persistanceUnit);
        addressService = new AddressServiceImplJPA();
        studentService = new StudentServiceImpJPA();
        courseService = new CourseServiceImplJPA();
        examResultService = new ExamResultServiceImplJPA();
        System.out.println("examResult : "+examResultService);
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
