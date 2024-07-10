package org.aes.compare.uiconsole.business;

import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.List;

public class UIConsoleDBServiceImplCourse implements CourseService {
    private ORMConfigSingleton ormConfig = new ORMConfigSingleton();

    @Override
    public void save(Course c) {
        ormConfig.getCourseService().save(c);
    }

    @Override
    public Course findByName(String name) {
        return ormConfig.getCourseService().findByName(name);
    }

    @Override
    public List<Course> findAll() {
        return ormConfig.getCourseService().findAll();
    }

    @Override
    public void updateCourseByName(Course course) {
        ormConfig.getCourseService().updateCourseByName(course);
    }

    @Override
    public void deleteCourseByName(String name) {
        ormConfig.getCourseService().deleteCourseByName(name);
    }

    @Override
    public void deleteCourseById(int id) {
        ormConfig.getCourseService().deleteCourseById(id);
    }

    @Override
    public void resetTable() {
        ormConfig.getCourseService().resetTable();
    }
}
