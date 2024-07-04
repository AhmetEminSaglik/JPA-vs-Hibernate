package org.aes.compare.orm.business.abstracts;

import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.List;

public interface CourseService {
    Course save(Course c);

    Course findByName(String name);

    void deleteCourseByName(String name);

    void deleteCourseById(int id);

    void updateCourseByName(Course course);

    void resetTable();

    List<Course> findAll();
}
