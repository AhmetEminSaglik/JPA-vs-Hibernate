package org.aes.compare.orm.business.abstracts;

import org.aes.compare.orm.exceptions.InvalidCourseDeleteRequestStudentEnrolled;
import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.List;

public interface CourseService {

    void save(Course c);

    Course findByName(String name);

    List<Course> findAll();

    List<Course> findAllCourseOfStudentId(int studentId);

    List<Course> findAllCourseThatStudentDoesNotHave(int studentId);

    void updateCourseByName(Course course);

    void deleteCourseByName(String name) throws InvalidCourseDeleteRequestStudentEnrolled;

    void deleteCourseById(int id) throws InvalidCourseDeleteRequestStudentEnrolled;

    void resetTable();


}
