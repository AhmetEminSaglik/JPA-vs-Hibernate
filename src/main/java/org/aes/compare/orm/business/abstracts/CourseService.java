package org.ahmeteminsaglik.orm.business.abstracts;

import org.ahmeteminsaglik.orm.exceptions.InvalidCourseDeleteRequestStudentEnrolled;
import org.ahmeteminsaglik.orm.exceptions.InvalidCourseNameSaveRequestException;
import org.ahmeteminsaglik.orm.model.courses.abstracts.Course;

import java.util.List;

public interface CourseService {

    void save(Course c);

    Course findByName(String name);

    List<Course> findAll();

    List<Course> findAllCourseOfStudentId(int studentId);

    List<Course> findAllCourseThatStudentDoesNotHave(int studentId);

    void updateCourseByName(Course course) throws InvalidCourseNameSaveRequestException;

    void deleteCourseByName(String name) throws InvalidCourseDeleteRequestStudentEnrolled;

    void deleteCourseById(int id) throws InvalidCourseDeleteRequestStudentEnrolled;

    void resetTable();


}
