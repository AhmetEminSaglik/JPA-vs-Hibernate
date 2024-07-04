package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.EnumCourse;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.*;

import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentTestJPA {
    private StudentService studentService = new StudentServiceImpJPA();
    private CourseService courseService = new CourseServiceImplJPA();
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
    public void test_throwException_SaveStudentWithCourse() {
        Address address = new Address("Street abc", "Ankara", "Spain");

        Student student = new Student();
        student.setName("Saglik");
        student.setGrade(1);
        student.setAddress(address);

        Course courseMath = new MathCourse();

        student.addCourse(courseMath);

        Assertions.assertThrows(Exception.class, () -> {
            studentService.save(student);
            System.out.println("Student is saved : " + student);
        });
    }

    @Test
    @Order(4)
    public void test_SaveStudent_SaveCourse_AddCourseToStudent() {
        Address address = new Address("Street abc", "Ankara", "Spain");

        Student student = new Student();
        student.setName("Emin");
        student.setGrade(1);
        student.setAddress(address);
        studentService.save(student);
        System.out.println("Student is saved : " + student);


        Course courseMath = new MathCourse();
        courseService.save(courseMath);
        System.out.println("Savelenen Course :"+courseMath);

        student.addCourse(courseMath);
        studentService.update(student);
        System.out.println("Student son : "+student);


    }

    private double getRandomExamResult() {
        return (random.nextInt(10000) / 100.0);
    }

}
