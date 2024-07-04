package org.aes.compare.orm;

import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.jpa.CourseServiceImplJPA;
import org.aes.compare.orm.business.concrete.jpa.StudentServiceImpJPA;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.EnumCourse;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.LiteratureCourse;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.OtherCourse;
import org.aes.compare.orm.model.courses.concretes.ScienceCourse;
import org.aes.compare.orm.model.courses.concretes.programming.FlutterCourse;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static CourseService courseService = new CourseServiceImplJPA();
    private static StudentService studentService = new StudentServiceImpJPA();
    public static void main(String[] args) {

        saveStudent();
        saveCourses();
        addCoursesToStudent();
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
        System.out.println("Student is saved : " + student);


        address = new Address("kucuk cekmece", "Istanbul", "Turkey");

        student = new Student();
        student.setName("Alperen ");
        student.setGrade(3);
        student.setAddress(address);
        studentService.save(student);
        System.out.println("Student is saved : " + student);

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
        Student student = studentService.findStudentById(1);
        Course courseMath = courseService.findByName(EnumCourse.MATH.getName());
        Course courseScience = courseService.findByName(EnumCourse.SCIENCE.getName());
        student.addCourse(courseMath);
        student.addCourse(courseScience);

        System.out.println("first Student " +student);
        studentService.update(student);
        System.out.println("Assigned Courses to first student : " + student);
        System.out.println("-------------");

        student = studentService.findStudentById(2);
        Course coursePiano = courseService.findByName("Piano");
        Course courseFlutter = courseService.findByName(EnumCourse.FLUTTER.getName());

        student.addCourse(coursePiano);
        student.addCourse(courseFlutter);
        student.addCourse(courseMath);

        System.out.println("Second Student " +student);
        studentService.update(student);
        System.out.println("Assigned Courses to Second student : " + student);


    }

}