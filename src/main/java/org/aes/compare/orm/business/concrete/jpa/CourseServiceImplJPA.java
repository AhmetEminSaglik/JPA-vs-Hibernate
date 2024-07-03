package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.model.courses.abstracts.Course;

public class CourseServiceImplJPA extends JpaImplementation<Course> implements CourseService {

    @Override
    public Course save(Course c) {
//        try {
        initializeTransaction();
        getEntityManager().persist(c);
        commit();
//        } catch (ConstraintViolationException e) {
//            System.out.println("DATA DAHA ONCE EKLENMIS");
//        }
        return c;
    }

    @Override
    public Course findByName(String name) {
        System.out.println("Gelen Name : " + name);
        initializeTransaction();
        TypedQuery<Course> query = getEntityManager().createQuery(
                "SELECT c FROM Course c WHERE c.name=:data  ", Course.class);
        query.setParameter("data", name);
        Course course = query.getSingleResult();
        System.out.println(course);
        return course;
    }

    @Override
    public void deleteCourseByName(String name) {
        System.out.println("silinecek dersin adi : " + name);
        initializeTransaction();
        Course course = findByName(name);
        System.out.println("Retrieved Course : " + course);
        getEntityManager().remove(course);
        commit();
        System.out.println("Course is removed : " + course);


//        TypedQuery

    }

    @Override
    public void deleteCourseById(int id) {

    }

}
