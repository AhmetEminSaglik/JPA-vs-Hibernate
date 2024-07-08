package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.*;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.LiteratureCourse;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.OtherCourse;
import org.aes.compare.orm.model.courses.concretes.ScienceCourse;
import org.aes.compare.orm.model.courses.concretes.programming.FlutterCourse;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;
import org.aes.compare.orm.model.enums.course.EnumCourse;
import org.aes.compare.orm.model.enums.configfile.EnumJPAConfigFile;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JPATest {
    private static StudentService studentService = new StudentServiceImpJPA();
    private static CourseService courseService = new CourseServiceImplJPA();
    private static AddressService addressService = new AddressServiceImplJPA();
    private static ExamResultService examResultService = new ExamResultServiceImplJPA();

    @BeforeAll
    public static void resetTablesBeforeAll() {
        JpaImplementation.setPersistanceUnit(EnumJPAConfigFile.JUNIT_TEST);

        examResultService.resetTable();
        courseService.resetTable();
        studentService.resetTable();
        addressService.resetTable();
    }

    @Test
    @Order(101)
    public void testSaveCourse() {
        saveCourseData();
        int expected = 7;
        int actual = courseService.findAll().size();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    @Order(102)
    public void testFindCourseByName() {
        Course course = courseService.findByName(EnumCourse.MATH.getName());
        int expected = 1;
        int actual = course.getId();
        Assertions.assertEquals(expected, actual);

        course = courseService.findByName(EnumCourse.JAVA.getName());
        expected = 4;
        actual = course.getId();
        Assertions.assertEquals(expected, actual);

        course = courseService.findByName("Piano");
        expected = 6;
        actual = course.getId();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    @Order(103)
    public void testDeleteCourseByName() {
        Course course = courseService.findByName(EnumCourse.MATH.getName());
        Assertions.assertNotNull(course);

        courseService.deleteCourseByName(EnumCourse.MATH.getName());
        course = courseService.findByName(EnumCourse.MATH.getName());

        Assertions.assertNull(course);
    }


    @Test
    @Order(104)
    public void testDeleteCourseById() {
        courseService.deleteCourseById(2);
        Course course = courseService.findByName(EnumCourse.MATH.getName());
        Assertions.assertNull(course);
    }


    @Test
    @Order(105)
    public void testUpdateCourse() {
        Course course = courseService.findByName(EnumCourse.JAVA.getName());
        course.setCredits(6.5);
        courseService.updateCourseByName(course);
        course = courseService.findByName(EnumCourse.JAVA.getName());
        double expected = 6.5;
        double actual = course.getCredits();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    @Order(201)
    public void testSaveAndReadAddress() {
        Address address = new Address("1882", "Ankara", "Spain");
        Address address2 = new Address("abc", "abc", "abc");
        addressService.save(address);
        addressService.save(address2);

        Address retrievedAddress = addressService.findById(2);
        Assertions.assertEquals(address2, retrievedAddress);
    }


    @Test
    @Order(202)
    public void testUpdateAddress() {
        Address address = addressService.findById(1);
        address.setCity("Updated City");
        addressService.update(address);

        Address retrievedAddress = addressService.findById(1);
        Assertions.assertEquals(address, retrievedAddress);
    }


    @Test
    @Order(203)
    public void testDeleteAddress() {
        addressService.deleteById(1);
        int expected = 1;
        int actual = addressService.findAll().size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(301)
    public void test_throwException_WhileSavingStudentWithoutAddress() {
        Student student = new Student();
        student.setName("Ahmet");
        student.setGrade(1);
            studentService.save(student);
        student = studentService.findById(1);
        Assertions.assertNull(student);
    }


    @Test
    @Order(302)
    public void testSaveStudentWithAddress() {
        Address address = new Address("Street abc", "Ankara", "Spain");
        addressService.save(address);

        Student student = new Student();
        student.setName("Emin");
        student.setGrade(1);
        student.setAddress(address);
        studentService.save(student);

    }


    @Test
    @Order(303)
    public void test_throwException_SaveStudentWithCourse() {
        Address address = new Address("Street abc", "Ankara", "Spain");

        Student student = new Student();
        student.setName("Saglik");
        student.setGrade(1);
        student.setAddress(address);

        Course courseMath = new MathCourse();

        student.addCourse(courseMath);

        studentService.save(student);
        boolean isErrorOccured = false;
        try {
            student = studentService.findByStudentIdWithCourseName(student.getId(), courseMath.getName());
        } catch (InvalidStudentCourseMatchForExamResult e) {
            isErrorOccured = true;
        }
        Assertions.assertTrue(isErrorOccured,"Student should not be saved Data: "+student);
    }


    @Test
    @Order(304)
    public void test_SaveStudent_SaveCourse_AddCourseToStudent() {
        Address address = new Address("Street def", "Istanbul", "Turkey");
        addressService.save(address);

        Student student = new Student();
        student.setName("Ahmet Emin");
        student.setGrade(1);
        student.setAddress(address);
        studentService.save(student);


        List<Course> courses = new ArrayList<>();
        Course courseJava = courseService.findByName(EnumCourse.JAVA.getName());
        Course coursePianoNew = new OtherCourse("Guitar", 5);

        courses.add(coursePianoNew);
        courses.add(courseJava);

        student.addCourses(courses);
        List<Student> students = studentService.findAll();
        student.addCourse(coursePianoNew);
        studentService.update(student);

    }

    @Test
    @Order(305)
    public void testFindStudentById() {

        Address address = new Address("Street def", "Istanbul", "Turkey");

        Student student = new Student();
        student.setId(2);
        student.setName("Ahmet Emin");
        student.setGrade(1);
        student.setAddress(address);

        Student retrievedStudent = studentService.findById(2);

        Assertions.assertEquals(student, retrievedStudent);

    }

    @Test
    @Order(306)
    public void test_deleteStudent_ThatWithOnlyAddress() {
        Student student = studentService.findById(1);
        Assertions.assertTrue(student != null);

        studentService.deleteById(1);
        student = studentService.findById(1);
        Assertions.assertTrue(student == null);

    }

    @Test
    @Order(401)
    public void testSaveExamResult() {
        Student student = new Student("Omer Koramaz", 6, null);

        Course courseMath = new MathCourse();
        courseService.save(courseMath);
        student.addCourse(courseMath);

        Address address = new Address("1882. street", "Ankara", "Turkey");
        addressService.save(address);

        student.setAddress(address);
        studentService.save(student);

        ExamResult examResult = new ExamResult(76, courseMath, student);
        examResult.setScore(15.15);

        try {
            examResultService.save(examResult);
        } catch (InvalidStudentCourseMatchForExamResult e) {
            throw new RuntimeException(e);
        }


        Student studentAhmetEmin = studentService.findById(2);

        Course courseJava = courseService.findByName(EnumCourse.JAVA.getName());

        Course courseGuitar = courseService.findByName("Guitar");

        ExamResult examResultAhmetEminJava1 = new ExamResult(90.25, courseJava, studentAhmetEmin);
        ExamResult examResultAhmetEminJava2 = new ExamResult(75.25, courseJava, studentAhmetEmin);
        ExamResult examResultAhmetEminGuitar = new ExamResult(5.75, courseGuitar, studentAhmetEmin);

        try {
            examResultService.save(examResultAhmetEminJava1);
            examResultService.save(examResultAhmetEminJava2);
            examResultService.save(examResultAhmetEminGuitar);
        } catch (InvalidStudentCourseMatchForExamResult e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    @Order(402)
    public void testInvalidStudentCourseMatchExamResult() {

        Student student = studentService.findById(2);
        Course courseFlutter = courseService.findByName(EnumCourse.FLUTTER.getName());

        ExamResult examResult = new ExamResult(76, courseFlutter, student);
        examResult.setScore(15.15);
        Assertions.assertThrows(InvalidStudentCourseMatchForExamResult.class, () -> examResultService.save(examResult));
    }

    @Test
    @Order(403)
    public void testFindExamResultByStudentId() {
        List<ExamResult> examResults = examResultService.findAllByStudentId(2);
        int expected = 3;
        int actual = examResults.size();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    @Order(404)
    public void testFindAllExamResult() {
        List<ExamResult> examResults = examResultService.findAll();
        int expected = 4;
        int actual = examResults.size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(404)
    public void testFindExamResultByStudentIdAndCourseName() {
        List<ExamResult> examResults = examResultService.findAllByStudentIdAndCourseName(2, EnumCourse.JAVA.getName());
        int expected = 2;
        int actual = examResults.size();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    @Order(404)
    public void testUpdateExamResultScoreByStudentIdAndCourseName() {
        List<ExamResult> examResults = examResultService.findAllByStudentIdAndCourseName(2, EnumCourse.JAVA.getName());
        List<Double> oldScores = new ArrayList<>();
        int addVal = 15;
        for (int i = 0; i < examResults.size(); i++) {
            ExamResult tmp = examResults.get(i);
            oldScores.add(tmp.getScore());
            tmp.setScore(tmp.getScore() + addVal);
            examResultService.update(tmp);
        }
        examResults = examResultService.findAllByStudentIdAndCourseName(2, EnumCourse.JAVA.getName());
        for (int i = 0; i < examResults.size(); i++) {
            double expected = oldScores.get(i) + addVal;
            double actual = examResults.get(i).getScore();
            Assertions.assertEquals(expected, actual);
        }
    }

    @Test
    @Order(405)
    public void testDeleteExamResultById() {
        List<ExamResult> examResults = examResultService.findAllByStudentId(2);
        int expected = 3;
        int actual = examResults.size();
        Assertions.assertEquals(expected, actual);
        ExamResult examResult= examResults.get(0);
        examResultService.deleteById(examResult.getId());

        examResults = examResultService.findAllByStudentId(2);
        expected = 2;
        actual = examResults.size();
        Assertions.assertEquals(expected, actual);

    }

    private void saveCourseData() {
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
}
