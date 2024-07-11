package org.aes.compare.uiconsole.business;

import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.List;

public class UIConsoleDBServiceImplCourse {
    private ORMConfigSingleton ormConfig = new ORMConfigSingleton();

    public void save() {
//        ormConfig.getCourseService().save(c);
    }

    public void findByName() {
//        return ormConfig.getCourseService().findByName(name);
    }


    public void findAll() {
        // return ormConfig.getCourseService().findAll();
    }


    public void updateCourseByName() {
        //   ormConfig.getCourseService().updateCourseByName(course);
    }


    public void deleteCourseByName() {
        //  ormConfig.getCourseService().deleteCourseByName(name);
    }


    public void deleteCourseById() {
        //  ormConfig.getCourseService().deleteCourseById(id);
    }


    public void resetTable() {
        //  ormConfig.getCourseService().resetTable();
    }
}
