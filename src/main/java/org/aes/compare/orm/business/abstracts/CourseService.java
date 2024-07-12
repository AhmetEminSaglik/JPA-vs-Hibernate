package org.aes.compare.orm.business.abstracts;

import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.List;

public interface CourseService {

    void save(Course c);

    Course findByName(String name);

    List<Course> findAll();

    List<Course> findAllCourseOfStudentId(int studentid);

    List<Course> findAllCourseThatStudentDoesNotHave(int studentid);

    void updateCourseByName(Course course);

    void deleteCourseByName(String name);

    void deleteCourseById(int id);

    void resetTable();


}
