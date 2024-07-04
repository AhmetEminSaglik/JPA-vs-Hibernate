package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.NoResultException;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.DatabaseCoreService;
import org.aes.compare.orm.model.EnumCourse;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.model.courses.concretes.LiteratureCourse;
import org.aes.compare.orm.model.courses.concretes.MathCourse;
import org.aes.compare.orm.model.courses.concretes.OtherCourse;
import org.aes.compare.orm.model.courses.concretes.ScienceCourse;
import org.aes.compare.orm.model.courses.concretes.programming.FlutterCourse;
import org.aes.compare.orm.model.courses.concretes.programming.JavaCourse;
import org.junit.jupiter.api.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CourseTestJPA {

    private CourseService courseService = new CourseServiceImplJPA();

    @Test
    @Order(1)
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
    @Order(2)
    public void testFindCourseByName() {
//        saveTestData();
        Course course = courseService.findByName(EnumCourse.MATH.getName());
        System.out.println("Retrieved Course By Name " + EnumCourse.MATH.getName() + " is : " + course);
/*
        Course course2 = courseService.findByName(EnumCourse.SCIENCE.getName());
        System.out.println("Retrieved Course By Name " + EnumCourse.SCIENCE.getName() + " is : " + course2);

        Course course3 = courseService.findByName(EnumCourse.FLUTTER.getName());
        System.out.println("Retrieved Course By Name " + EnumCourse.SCIENCE.getName() + " is : " + course3);*/
    }

    @Test
    @Order(3)
    public  void testDeleteCourseByName(){
        courseService.deleteCourseByName(EnumCourse.MATH.getName());
        Assertions.assertThrows(NoResultException.class,()->{
            System.out.println("Retrieved course after deleted : "+courseService.findByName(EnumCourse.MATH.getName()));
        });
    }

    @Test
    @Order(4)
    public void testDeleteCourseById() {
        courseService.deleteCourseById(2);
        Assertions.assertThrows(NoResultException.class, () -> {
            System.out.println("Retrieved course after deleted : " + courseService.findByName(EnumCourse.MATH.getName()));
        });
    }

    private void saveTestData() {
        Course courseMath = new MathCourse();
        Course courseScience = new ScienceCourse();
/*
        Course courseJava = new JavaCourse();
        Course courseUnity = new OtherCourse("Unity");
*/

        courseService.save(courseMath);
        System.out.println("new Course is saved : " + courseMath);

        courseService.save(courseScience);
        System.out.println("new Course is saved : " + courseScience);
/*



        courseService.save(courseJava);
        System.out.println("new Course is saved : " + courseJava);

        courseService.save(courseUnity);
        System.out.println("new Course is saved : " + courseUnity);
*/

    }
}
