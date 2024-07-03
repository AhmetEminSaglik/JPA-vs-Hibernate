package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.LiteratureCourse;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.OtherCourse;
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
public class JpaImplementationTest {
    private StudentService studentService = new StudentServiceImpJPA();
    private CourseService courseService = new CourseServiceImplJPA();

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
//        studentService.save(student);
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

    @Test
    @Order(4)
    public void testSaveCourse() {
        Course courseMath = new MathCourse();
        Course courseScience = new ScienceCourse();
        Course courseLiterature = new LiteratureCourse();
        Course courseJava = new JavaCourse();
        Course courseFlutter = new FlutterCourse();
        Course courseOtherPiano = new OtherCourse("Piano");
        Course courseUnity = new OtherCourse("Unity");

        courseService.save(courseMath);
        System.out.println("new Course is saved : " + courseMath);

        courseService.save(courseScience);
        System.out.println("new Course is saved : " + courseScience);

        courseService.save(courseLiterature);
        System.out.println("new Course is saved : " + courseLiterature);

        courseService.save(courseJava);
        System.out.println("new Course is saved : " + courseJava);

        courseService.save(courseFlutter);
        System.out.println("new Course is saved : " + courseFlutter);

        courseService.save(courseOtherPiano);
        System.out.println("new Course is saved : " + courseOtherPiano);

        courseService.save(courseUnity);
        System.out.println("new Course is saved : " + courseUnity);

    }


    @Test
    @Order(4)
    public void testSaveStudentWithAddressCourseAndExamResult() {

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

        ExamResult examResultMath = new ExamResult(getRandomExamResult(), courseMath);
        ExamResult examResultScience = new ExamResult(getRandomExamResult(), courseScience);
        ExamResult examResultLiterature = new ExamResult(getRandomExamResult(), courseLiterature);
        ExamResult examResultJava = new ExamResult(getRandomExamResult(), courseJava);
        ExamResult examResultFlutter = new ExamResult(getRandomExamResult(), courseFlutter);

        student.addCourse(courseMath);
        student.addCourse(courseScience);
        student.addCourse(courseLiterature);
        student.addCourse(courseJava);
        student.addCourse(courseFlutter);


        student.addExamResult(examResultMath);
        student.addExamResult(examResultScience);
        student.addExamResult(examResultLiterature);
        student.addExamResult(examResultJava);
        student.addExamResult(examResultFlutter);


        studentService.save(student);
        System.out.println("Student is saved : " + student);
    }


    Random random = new Random();

    private double getRandomExamResult() {
        return (random.nextInt(10000) / 100.0);
    }

}