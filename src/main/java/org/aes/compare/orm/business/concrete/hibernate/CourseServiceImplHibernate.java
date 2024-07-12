package org.aes.compare.orm.business.concrete.hibernate;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.concrete.hibernate.abstracts.HibernateImplementation;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.utility.ColorfulTextDesign;

import java.util.List;

public class CourseServiceImplHibernate extends HibernateImplementation<Course> implements CourseService {

    @Override
    public void save(Course c) {
        initializeTransaction();
        session.persist(c);
        commit();
    }

    @Override
    public Course findByName(String name) {
        initializeTransaction();
        TypedQuery<Course> query = session.createQuery(
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
    public List<Course> findAll() {
        initializeTransaction();
        TypedQuery<Course> query = session.createQuery("SELECT c FROM Course c ", Course.class);
        List<Course> courses = query.getResultList();
        commit();
        return courses;
    }

    @Override
    public List<Course> findAllCourseOfStudentId(int studentid) {
        initializeTransaction();
        TypedQuery<Course> query = session.createQuery(
                "SELECT c FROM Course c " +
                        "JOIN c.students s " +
                        "WHERE s.id = :studentid", Course.class);
        query.setParameter("studentid", studentid);
        List<Course> courses = query.getResultList();
        commit();
        return courses;
    }

    @Override
    public List<Course> findAllCourseThatStudentDoesNotHave(int studentid) {
        initializeTransaction();
        TypedQuery<Course> query = session.createQuery(
                "SELECT c FROM Course c WHERE c NOT IN (SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentid)", Course.class);
        query.setParameter("studentid", studentid);
        List<Course> courses = query.getResultList();
        commit();
        return courses;
    }

    @Override
    public void updateCourseByName(Course course) {
        initializeTransaction();
        session.merge(course);
        commit();
    }

    @Override
    public void deleteCourseByName(String name) {
        initializeTransaction();
        TypedQuery<Course> query = session.createQuery(
                "SELECT c FROM Course c  WHERE c.name=:data", Course.class
        );
        query.setParameter("data", name);
        Course course = query.getSingleResult();
        session.remove(course);
        commit();
    }

    @Override
    public void deleteCourseById(int id) {
        initializeTransaction();
        Course course = session.find(Course.class, id);
        session.remove(course);
        commit();
    }

    @Override
    public void resetTable() {
        initializeTransaction();
        session.createNativeMutationQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
        session.createNativeMutationQuery("TRUNCATE TABLE courses").executeUpdate();
        session.createNativeMutationQuery("TRUNCATE TABLE student_courses").executeUpdate();
        session.createNativeMutationQuery("SET FOREIGN_KEY_CHECKS=1").executeUpdate();
        session.createNativeMutationQuery("ALTER TABLE courses AUTO_INCREMENT = 1")
                .executeUpdate();
        commit();
    }

}
