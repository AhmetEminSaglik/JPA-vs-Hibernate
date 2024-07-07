package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.utility.ColorfulTextDesign;

import java.util.List;

public class CourseServiceImplJPA extends JpaImplementation<Course> implements CourseService {

    @Override
    public void save(Course c) {
        initializeTransaction();
        getEntityManager().persist(c);
        commit();
    }

    @Override
    public Course findByName(String name) {
        initializeTransaction();
        TypedQuery<Course> query = getEntityManager().createQuery(
                "SELECT c FROM Course c WHERE c.name=:data  ", Course.class);
        query.setParameter("data", name);
        Course course = null;
        try {
            course = query.getSingleResult();
        } catch (NoResultException ex) {
            System.out.println(ColorfulTextDesign.getErrorColorText("Course is not found"));
        } finally {
            commit();
        }
        return course;
    }

    @Override
    public void deleteCourseByName(String name) {
        initializeTransaction();
        TypedQuery<Course> query = getEntityManager().createQuery(
                "SELECT c FROM Course c  WHERE c.name=:data", Course.class
        );
        query.setParameter("data", name);
        Course course = query.getSingleResult();
        getEntityManager().remove(course);
        commit();

    }

    @Override
    public void deleteCourseById(int id) {
        initializeTransaction();
        Course course=getEntityManager().find(Course.class,id);
        getEntityManager().remove(course);
        commit();
    }

    @Override
    public void updateCourseByName(Course course) {
        initializeTransaction();
        getEntityManager().merge(course);
        commit();
    }

    @Override
    public void resetTable() {
        initializeTransaction();

        getEntityManager().createQuery("DELETE FROM Course")
                .executeUpdate();
        commit();

        initializeTransaction();

        //Native query uses database table name
        // Query uses Java Class' name
        getEntityManager().createNativeQuery("ALTER TABLE courses AUTO_INCREMENT = 1")
                .executeUpdate();

        commit();
    }

    @Override
    public List<Course> findAll() {
        initializeTransaction();
        TypedQuery<Course> query = getEntityManager().createQuery("SELECT c FROM Course c ", Course.class);
        List<Course> courses = query.getResultList();
        commit();
        return courses;
    }

}
