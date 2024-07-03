package org.aes.compare.orm;

import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.jpa.StudentServiceImpJPA;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.LiteratureCourse;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.ScienceCourse;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static StudentService studentService;
    public static void main(String[] args) {
     /* NOTE :If the table is not created in DB, then JPA will cause an error about the table not existing,
       But hibernate will create a table if the table is not created.*/
//        processForHibernate();
        Scanner scanner = new Scanner(System.in);
        saveStudent1JPA();
        scanner.nextLine();
//        scanner.nextLine();
//        while (true){
//
//        }
    }


    public static void saveStudent1JPA() {
        List<Course> courseList = new ArrayList<>();
        Course c1 = new MathCourse();
        Course c2 = new ScienceCourse();

        Address address = new Address("street 1 ", "city 1", "country 1");

        ExamResult examResult1 = new ExamResult(10, c1);
        ExamResult examResult2 = new ExamResult(15, c2);


        Student student = new Student("Ahmet Emin", 5, address);
        c1.addStudent(student);
        c2.addStudent(student);
        student.addExamResult(examResult1);
        student.addExamResult(examResult2);

        studentService = new StudentServiceImpJPA();
        studentService.save(student);
        System.out.println("--------------------------------Student is Saved --------------------------------");
        System.out.println(student);
        /*
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("MathCourse", 4));
        courseList.add(new Course("Java Android", 2));

        Address address = new Address("street 1 ", "city 1", "country 1");

        Student student = new Student("Ahmet Emin", 5, courseList, address);
        studentService = new StudentServiceImpJPA();

        studentService.save(student);
        System.out.println("Saved Student : " + student);
    */
    }

    public static void saveStudent2JPA() {
        List<Course> courseList = new ArrayList<>();
        Course c1 = new LiteratureCourse();
        Course c2 = new ScienceCourse();
        courseList.add(c1);
        courseList.add(c2);


        Address address = new Address("street 1 ", "city 1", "country 1");

        ExamResult examResult1 = new ExamResult(10, c1);
        ExamResult examResult2 = new ExamResult(15, c2);


        Student student = new Student("Ahmet Emin", 5, address);
        c1.addStudent(student);
        c2.addStudent(student);
        student.addExamResult(examResult1);
        student.addExamResult(examResult2);

        studentService = new StudentServiceImpJPA();
        studentService.save(student);
        System.out.println("--------------------------------Student is Saved --------------------------------");
        System.out.println(student);
        /*
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("MathCourse", 4));
        courseList.add(new Course("Java Android", 2));

        Address address = new Address("street 1 ", "city 1", "country 1");

        Student student = new Student("Ahmet Emin", 5, courseList, address);
        studentService = new StudentServiceImpJPA();

        studentService.save(student);
        System.out.println("Saved Student : " + student);
    */
    }
}