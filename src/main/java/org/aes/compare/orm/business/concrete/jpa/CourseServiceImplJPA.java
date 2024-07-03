package org.aes.compare.orm.business.concrete.jpa;

import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.model.courses.abstracts.Course;

public class CourseServiceImplJPA extends JpaImplementation<Course> implements CourseService {

    @Override
    public Course save(Course c) {
        initializeTransaction();
        getEntityManager().persist(c);
        commit();
        return c;
    }

    @Override
    public Course findByName(String DTYPE) {
        return null;
    }

}
