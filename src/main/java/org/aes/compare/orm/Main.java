package org.aes.compare.orm;

import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.jpa.StudentServiceImpJPA;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.Course;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.model.Student;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static StudentService studentService;
    public static void main(String[] args) {
     /* NOTE :If the table is not created in DB, then JPA will cause an error about the table not existing,
       But hibernate will create a table if the table is not created.*/
//        processForHibernate();
        saveStudentJPA();
    }


    public static void saveStudentJPA() {
        List<Course> courseList = new ArrayList<>();
        Course c1 = new Course("Math", 4);
        Course c2 = new Course("Java Android", 2);

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
//        System.out.println("Address : " + student.getAddress());
//        System.out.println("Courses : " + student.getCourses());
//        System.out.println("grades : " + student.getGrade());

        /*
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("Math", 4));
        courseList.add(new Course("Java Android", 2));

        Address address = new Address("street 1 ", "city 1", "country 1");

        Student student = new Student("Ahmet Emin", 5, courseList, address);
        studentService = new StudentServiceImpJPA();

        studentService.save(student);
        System.out.println("Saved Student : " + student);
    */
    }
}