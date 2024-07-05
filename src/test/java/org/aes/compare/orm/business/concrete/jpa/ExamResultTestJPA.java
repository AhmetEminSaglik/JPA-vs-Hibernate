package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;
import org.junit.jupiter.api.*;

import java.util.Random;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExamResultTestJPA {
    private static StudentService studentService = new StudentServiceImpJPA();
    private static CourseService courseService = new CourseServiceImplJPA();
    private static AddressService addressService = new AddressServiceImplJPA();
    private static ExamResultService examResultService = new ExamResultServiceImplJPA();

    @BeforeEach
    public void resetTablesBeforeEach() {
        ResetAllTables.resetAll();
    }

    @AfterAll
    public static void resetTablesAfterAll() {
        ResetAllTables.resetAll();
    }

    @Test
    @Order(1)
    public void testSaveExamResult() {

        Student student = new Student("Ahmet Emin", 6, null);

        Course courseMath = new MathCourse();

        saveCourse(courseMath);

        student.addCourse(courseMath);
        saveStudent(student);


        ExamResult examResult = new ExamResult(76, courseMath, student);
        examResult.setScore(15.15);

        try {
            examResultService.save(examResult);
        } catch (InvalidStudentCourseMatchForExamResult e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @Order(2)
    public void testInvalidStudentCourseMatchExamResult() {

        Student student = new Student("Ahmet Emin", 6, null);

        Course courseMath = new MathCourse();
        Course courseJava = new JavaCourse();

        saveCourse(courseJava);
        saveCourse(courseMath);

        student.addCourse(courseMath);
        saveStudent(student);


        ExamResult examResult = new ExamResult(76, courseJava, student);
        examResult.setScore(15.15);
        Assertions.assertThrows(InvalidStudentCourseMatchForExamResult.class, () -> examResultService.save(examResult));
    }

    void saveCourse(Course course) {
        courseService.save(course);
    }

    Student saveStudent(Student student) {
        Address address = saveAddress();
        student.setAddress(address);
        studentService.save(student);
        return student;
    }

    Address saveAddress() {
        Address address = new Address("1882. street", "Ankara", "Turkey");
        addressService.save(address);
        return address;
    }

   /* @Test
    @Order(2)
    public void testFindById() {

    }
*/

}
