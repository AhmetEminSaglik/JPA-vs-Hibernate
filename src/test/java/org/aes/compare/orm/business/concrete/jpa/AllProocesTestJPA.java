package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.LiteratureCourse;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.ScienceCourse;
import org.aes.compare.orm.model.courses.concretes.programming.FlutterCourse;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AllProocesTestJPA {

/*
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
    }*/
}
