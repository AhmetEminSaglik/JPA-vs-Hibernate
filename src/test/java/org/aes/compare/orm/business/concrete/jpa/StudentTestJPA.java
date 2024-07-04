package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.OtherCourse;
import org.aes.compare.orm.model.courses.concretes.ScienceCourse;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentTestJPA {
    private StudentService studentService = new StudentServiceImpJPA();
    private CourseService courseService = new CourseServiceImplJPA();
    private AddressService addressService = new AddressServiceImplJPA();
    private Random random = new Random();

    @BeforeEach
    public void resetTables() {
        courseService.resetTable();
        studentService.resetTable();
        addressService.resetTable();
    }
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
        addressService.save(address);

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
        addressService.save(address);

        Student student = new Student();
        student.setName("Emin");
        student.setGrade(1);
        student.setAddress(address);
        studentService.save(student);
        System.out.println("Student is saved : " + student);


        List<Course> courses = new ArrayList<>();
        Course courseMath = new MathCourse();
        Course courseScience = new ScienceCourse();
        Course courseJava = new JavaCourse();
        Course coursePiano = new OtherCourse("Piano", 2);

        courses.add(courseMath);
        courses.add(courseScience);
        courses.add(courseJava);
        courses.add(coursePiano);

        student.addCourses(courses);
        
        courseService.save(courseMath);
        System.out.println("Savelenen Course :"+courseMath);

        student.addCourse(courseMath);
        studentService.update(student);
        System.out.println("Student son : "+student);

    }
    @Test
    @Order(5)
    public  void test_deleteStudent_ThatWithOnlyAddress(){
        Address address = new Address("Street abc", "Ankara", "Spain");
        Address address2 = new Address("kucuk cekmece", "Istanbul", "Turkey");
        addressService.save(address);
        addressService.save(address2);

        Student student = new Student();
        student.setName("Ahmet Emin");
        student.setGrade(1);
        student.setAddress(address);
        studentService.save(student);
        System.out.println("Student is saved : " + student);


        Student student2 = new Student();
        student2.setName("Alperen");
        student2.setGrade(3);
        student2.setAddress(address2);
        studentService.save(student2);
        System.out.println("Student is saved : " + student2);

        studentService.deleteById(1);

    }

    private double getRandomExamResult() {
        return (random.nextInt(10000) / 100.0);
    }


}
