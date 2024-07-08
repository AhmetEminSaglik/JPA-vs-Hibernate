package org.aes.compare.orm;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.jpa.AddressServiceImplJPA;
import org.aes.compare.orm.business.concrete.jpa.CourseServiceImplJPA;
import org.aes.compare.orm.business.concrete.jpa.StudentServiceImpJPA;
import org.aes.compare.orm.model.*;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.LiteratureCourse;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.OtherCourse;
import org.aes.compare.orm.model.courses.concretes.ScienceCourse;
import org.aes.compare.orm.model.courses.concretes.programming.FlutterCourse;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;
import org.aes.compare.orm.model.enums.course.EnumCourse;

public class Main {

    private static StudentService studentService = new StudentServiceImpJPA();
    private static CourseService courseService = new CourseServiceImplJPA();
    private static AddressService addressService = new AddressServiceImplJPA();

    public static void main(String[] args) {

        /*System.out.println(EnumORMConfigFile.REAL_PRODUCT_JPA.getName()); //persistenceUnitRealProject
        System.out.println(EnumORMConfigFile.REAL_PRODUCT_JPA.name()); //REAL_PRODUCT_JPA*/
//        JpaImplementation.setPersistanceUnit(EnumORMConfigFile.REAL_PRODUCT_JPA);

        Address address = new Address("Street abc", "Ankara", "Spain");
        Address address2 = new Address("kucuk cekmece", "Istanbul", "Turkey");
        addressService.save(address);
        addressService.save(address2);

        Student student = new Student();
        student.setName("Ahmet Emin");
        student.setGrade(1);
        student.setAddress(address);
        studentService.save(student);

        Student student2 = new Student();
        student2.setName("Alperen");
        student2.setGrade(3);
        student2.setAddress(address2);
        studentService.save(student2);

//        studentService.deleteById(1);


//        saveStudent();
//        saveCourses();
//        addCoursesToStudent();
    }

    public static void saveStudent() {
        studentService.resetTable();
        courseService.resetTable();
        Address address = new Address("Street abc", "Ankara", "Spain");

        Student student = new Student();
        student.setName("Ahmet Emin");
        student.setGrade(1);
        student.setAddress(address);
        studentService.save(student);


        address = new Address("kucuk cekmece", "Istanbul", "Turkey");

        student = new Student();
        student.setName("Alperen ");
        student.setGrade(3);
        student.setAddress(address);
        studentService.save(student);

    }

    public static void saveCourses() {
        Course courseMath = new MathCourse();
        Course courseScience = new ScienceCourse();
        Course courseLiterature = new LiteratureCourse();

        Course courseJava = new JavaCourse();
        Course courseFlutter = new FlutterCourse();
        Course courseOtherPiano = new OtherCourse("Piano");
        Course courseUnity = new OtherCourse("Unity");

        courseService.save(courseMath);
        courseService.save(courseScience);
        courseService.save(courseLiterature);
        courseService.save(courseJava);
        courseService.save(courseFlutter);
        courseService.save(courseOtherPiano);
        courseService.save(courseUnity);
    }

    public static void addCoursesToStudent() {
        Student student = studentService.findById(1);
        Course courseMath = courseService.findByName(EnumCourse.MATH.getName());
        Course courseScience = courseService.findByName(EnumCourse.SCIENCE.getName());
        student.addCourse(courseMath);
        student.addCourse(courseScience);

        studentService.update(student);

        student = studentService.findById(2);
        Course coursePiano = courseService.findByName("Piano");
        Course courseFlutter = courseService.findByName(EnumCourse.FLUTTER.getName());

        student.addCourse(coursePiano);
        student.addCourse(courseFlutter);
        student.addCourse(courseMath);

        studentService.update(student);
    }

}