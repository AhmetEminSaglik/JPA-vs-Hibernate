package org.aes.compare.orm.business.concrete.mixed;

import org.aes.compare.orm.business.abstracts.AddressService;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
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
import org.aes.compare.orm.model.enums.configfile.EnumHibernateConfigFile;
import org.aes.compare.orm.model.enums.configfile.EnumJPAConfigFile;
import org.aes.compare.orm.model.enums.course.EnumCourse;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MixedORMTest {

    private static ORMConfigSingleton ormConfig = new ORMConfigSingleton();
    private static StudentService studentService;
    private static CourseService courseService;
    private static AddressService addressService;
    private static ExamResultService examResultService;

    @BeforeAll
    public static void resetTablesBeforeAll() {
        enableJPA();
//        JpaImplementation.setPersistanceUnit(EnumJPAConfigFile.JUNIT_TEST);
        examResultService.resetTable();
        courseService.resetTable();
        studentService.resetTable();
        addressService.resetTable();
    }

    private static void enableJPA() {
        ormConfig.enableJPA(EnumJPAConfigFile.JUNIT_TEST);
        resetORMServices();
    }

    private static void enableHibernate() {
        ormConfig.enableHibernate(EnumHibernateConfigFile.JUNIT_TEST);
        resetORMServices();
    }

    private static void resetORMServices() {
        addressService = ormConfig.getAddressService();
        studentService = ormConfig.getStudentService();
        courseService = ormConfig.getCourseService();
        examResultService = ormConfig.getExamResultService();
    }


    @Test
    @Order(101)
    @DisplayName("[JPA] - Save Course")
    public void testSaveCourse() {
        enableJPA();
        saveCourseData();
        int expected = 7;
        int actual = courseService.findAll().size();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    @Order(102)
    @DisplayName("[Hibernate] - Find Course - By (Name)")
    public void testFindCourseByName() {
        enableHibernate();
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
    @DisplayName("[JPA] - Delete Course By (Name)")
    public void testDeleteCourseByName() {
        enableJPA();
        Course course = courseService.findByName(EnumCourse.MATH.getName());
        Assertions.assertNotNull(course);

        courseService.deleteCourseByName(EnumCourse.MATH.getName());
        course = courseService.findByName(EnumCourse.MATH.getName());

        Assertions.assertNull(course);
    }


    @Test
    @Order(104)
    @DisplayName("[Hibernate] - Delete Course By (Id)")
    public void testDeleteCourseById() {
        enableHibernate();
        courseService.deleteCourseById(2);
        Course course = courseService.findByName(EnumCourse.MATH.getName());
        Assertions.assertNull(course);
    }


    @Test
    @Order(105)
    @DisplayName("[JPA] - Update Course By (Course)")
    public void testUpdateCourse() {
        enableJPA();
        Course course = courseService.findByName(EnumCourse.JAVA.getName());
        course.setcredit(6.5);
        courseService.updateCourseByName(course);
        course = courseService.findByName(EnumCourse.JAVA.getName());
        double expected = 6.5;
        double actual = course.getcredit();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    @Order(201)
    @DisplayName("[Hibernate] - Save & Read Address")
    public void testSaveAndReadAddress() {
        enableHibernate();
        Address address = new Address("1882", "Ankara", "Spain");
        Address address2 = new Address("abc", "abc", "abc");
        addressService.save(address);
        addressService.save(address2);

        Address retrievedAddress = addressService.findById(2);
        Assertions.assertEquals(address2, retrievedAddress);
    }


    @Test
    @Order(202)
    @DisplayName("[JPA] - Update Address")
    public void testUpdateAddress() {
        enableJPA();
        Address address = addressService.findById(1);
        address.setCity("Updated City");
        addressService.update(address);

        Address retrievedAddress = addressService.findById(1);
        Assertions.assertEquals(address, retrievedAddress);
    }


    @Test
    @Order(203)
    @DisplayName("[Hibernate] - Delete Address")
    public void testDeleteAddress() {
        enableHibernate();
        addressService.deleteById(1);
        int expected = 1;
        int actual = addressService.findAll().size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(301)
    @DisplayName("[JPA] - Throw Exception - Save Student Without Address")
    public void test_throwException_WhileSavingStudentWithoutAddress() {
        enableJPA();
        Student student = new Student();
        student.setName("Ahmet");
        student.setGrade(1);
        studentService.save(student);
        student = studentService.findById(1);
        Assertions.assertNull(student);
    }


    @Test
    @Order(302)
    @DisplayName("[Hibernate] - Save Student With Address")
    public void testSaveStudentWithAddress() {
        enableHibernate();
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
    @DisplayName("[JPA] - Throw Exception - Save Student With Course")
    public void test_throwException_SaveStudentWithCourse() {
        enableJPA();
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
        Assertions.assertTrue(isErrorOccured, "Student should not be saved Data: " + student);
    }


    @Test
    @Order(304)
    @DisplayName("[Hibernate] - Save Student Then Add Course To Student")
    public void test_SaveStudent_SaveCourse_AddCourseToStudent() {
        enableHibernate();
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
    @DisplayName("[JPA] - Find Student By (id)")
    public void testFindStudentById() {
        enableJPA();
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
    @DisplayName("[Hibernate] - Delete Student By (id)")
    public void test_deleteStudent_ThatWithOnlyAddress() {
        enableHibernate();
        Student student = studentService.findById(1);
        Assertions.assertTrue(student != null);

        studentService.deleteById(1);
        student = studentService.findById(1);
        Assertions.assertTrue(student == null);

    }

    @Test
    @Order(401)
    @DisplayName("[JPA] - Save ExamResult")
    public void testSaveExamResult() {
        enableJPA();
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
    @DisplayName("[Hibernate] - Throw Exception - Save ExamResult")
    public void test_throwException_InvalidStudentCourseMatch_WhileSavingExamResult() {
        enableHibernate();
        Student student = studentService.findById(2);
        Course courseFlutter = courseService.findByName(EnumCourse.FLUTTER.getName());

        ExamResult examResult = new ExamResult(76, courseFlutter, student);
        examResult.setScore(15.15);
        Assertions.assertThrows(InvalidStudentCourseMatchForExamResult.class, () -> examResultService.save(examResult));
    }

    @Test
    @Order(403)
    @DisplayName("[JPA] - Find ExamResult By (StudentId)")
    public void testFindExamResultByStudentId() {
        enableJPA();
        List<ExamResult> examResults = examResultService.findAllByStudentId(2);
        int expected = 3;
        int actual = examResults.size();
        Assertions.assertEquals(expected, actual);

    }

    @Test
    @Order(404)
    @DisplayName("[Hibernate] - Find All ExamResult")
    public void testFindAllExamResult() {
        enableHibernate();
        List<ExamResult> examResults = examResultService.findAll();
        int expected = 4;
        int actual = examResults.size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @Order(405)
    @DisplayName("[JPA] - Find All ExamResult By (StudentId,  CourseName)")
    public void testFindAllExamResultByStudentIdAndCourseName() {
        enableJPA();
        List<ExamResult> examResults = examResultService.findAllByStudentIdAndCourseName(2, EnumCourse.JAVA.getName());
        int expected = 2;
        int actual = examResults.size();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    @Order(406)
    @DisplayName("[Hibernate] - Update All ExamResult By (StudentId, CourseName)")
    public void testUpdateAllExamResultScoreByStudentIdAndCourseName() {
        enableHibernate();
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
    @Order(407)
    @DisplayName("[JPA] - Delete ExamResult By (id)")
    public void testDeleteExamResultById() {
        enableJPA();
        List<ExamResult> examResults = examResultService.findAllByStudentId(2);
        int expected = 3;
        int actual = examResults.size();
        Assertions.assertEquals(expected, actual);
        ExamResult examResult = examResults.get(0);
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
