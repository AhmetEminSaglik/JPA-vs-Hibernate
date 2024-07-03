package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.LiteratureCourse;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.ScienceCourse;
import org.aes.compare.orm.model.courses.concretes.programming.FlutterCourse;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;
import org.hibernate.PropertyValueException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentTestJPA {
    private StudentService studentService = new StudentServiceImpJPA();
    private Random random = new Random();

    @Test
    @Order(1)
    public void test_throwException_WhileSavingStudentWithoutAddress() {
        Student student = new Student();
        student.setName("Ahmet");
        student.setGrade(1);
        Assertions.assertThrows(PropertyValueException.class, () -> {
            studentService.save(student);
            System.out.println("Student is saved : " + student);

        });
    }

    @Test
    @Order(1)
    public void testSaveStudentWithAddress() {
        Address address = new Address("Street abc", "Ankara", "Spain");

        Student student = new Student();
        student.setName("Emin");
        student.setGrade(1);
        student.setAddress(address);
        studentService.save(student);
        System.out.println("Student is saved : " + student);

    }


    @Test
    @Order(3)
    public void testSaveStudentWithAddressCourse() {
        Address address = new Address("Street abc", "Ankara", "Spain");

        Student student = new Student();
        student.setName("Saglik");
        student.setGrade(1);
        student.setAddress(address);

        Course courseMath = new MathCourse();
        Course courseScience = new ScienceCourse();
        Course courseLiterature = new LiteratureCourse();
        Course courseJava = new JavaCourse();
        Course courseFlutter = new FlutterCourse();

        student.addCourse(courseMath);
        student.addCourse(courseScience);
        student.addCourse(courseLiterature);
        student.addCourse(courseJava);
        student.addCourse(courseFlutter);

        Assertions.assertThrows(Exception.class, () -> {
            studentService.save(student);
            System.out.println("Student is saved : " + student);
        });

    }

    private double getRandomExamResult() {
        return (random.nextInt(10000) / 100.0);
    }

}
