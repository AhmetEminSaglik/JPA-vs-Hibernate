package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.RollbackException;
import jakarta.persistence.TypedQuery;
import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.concrete.comparator.CourseComparator;
import org.aes.compare.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.aes.compare.orm.exceptions.InvalidCourseDeleteRequestStudentEnrolled;
import org.aes.compare.orm.model.courses.abstracts.Course;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

public class CourseServiceImplJPA extends JpaImplementation<Course> implements CourseService {
    private final CourseComparator comparator = new CourseComparator();

    @Override
    public void save(Course c) {
//        String errMsg = "Course name must be unique. Probably " + c.getFileName() + " is saved before";
//        String errMsg = ColorfulTextDesign.getErrorColorTextWithPrefix("Course name must be unique. (Probably " + c.getName() + " is saved before)");
        StringBuilder errMsg = new StringBuilder();
        errMsg.append( ColorfulTextDesign.getErrorColorText(MetaData.COURSE_NAME_MUST_BE_UNIQUE))
                .append(ColorfulTextDesign.getInfoColorText(c.getName().toUpperCase()))
                .append(ColorfulTextDesign.getErrorColorText(MetaData.IS_SAVED_BEFORE));
        try {
            initializeTransaction();
            entityManager.persist(c);
            commit();
        } catch (ConstraintViolationException e) {
            System.out.println(errMsg);
        } catch (Exception e) {
            System.out.println(errMsg + "." + e.getMessage());
        }
    }

    @Override
    public Course findByName(String name) {
        initializeTransaction();
        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c WHERE c.name=:data  ", Course.class);
        query.setParameter("data", name);
        Course course = null;
        try {
            course = query.getSingleResult();
            commit();
        } catch (NoResultException ex) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix("[" + getClass().getSimpleName() + "]: Course is not found"));
        } finally {
            close();
        }
        return course;
    }

    @Override
    public List<Course> findAll() {
        initializeTransaction();
        TypedQuery<Course> query = entityManager.createQuery("SELECT c FROM Course c ", Course.class);
        List<Course> courses = query.getResultList();
        commit();
        return courses;
    }

    @Override
    public List<Course> findAllCourseOfStudentId(int studentid) {
        initializeTransaction();
        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c " +
                        "JOIN c.students s " +
                        "WHERE s.id = :studentid", Course.class);
        query.setParameter("studentid", studentid);
        List<Course> courses = query.getResultList();
        commit();
        courses.sort(comparator);
        return courses;

    }

    @Override
    public List<Course> findAllCourseThatStudentDoesNotHave(int studentId) {
        initializeTransaction();
        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c WHERE c NOT IN (SELECT c FROM Course c JOIN c.students s WHERE s.id = :studentId)", Course.class);
        query.setParameter("studentId", studentId);
        List<Course> courses = query.getResultList();
        commit();
        courses.sort(comparator);
        return courses;
    }

    @Override
    public void updateCourseByName(Course c) {
        String errMsg = ColorfulTextDesign.getErrorColorTextWithPrefix( ColorfulTextDesign.getErrorColorText(MetaData.COURSE_NAME_MUST_BE_UNIQUE) + c.getName() + MetaData.IS_SAVED_BEFORE);
        try {
            initializeTransaction();
            entityManager.merge(c);
            commit();
        } catch (ConstraintViolationException e) {
            System.out.println(errMsg);
        } catch (Exception e) {
            System.out.println(errMsg);
        }
    }

    @Override
    public void deleteCourseByName(String name) throws InvalidCourseDeleteRequestStudentEnrolled {
        initializeTransaction();
        TypedQuery<Course> query = entityManager.createQuery(
                "SELECT c FROM Course c  WHERE c.name=:data", Course.class
        );
        query.setParameter("data", name);
        Course course = query.getSingleResult();
        try {
            entityManager.remove(course);
            commit();
        } catch (RollbackException e) {
            throw new InvalidCourseDeleteRequestStudentEnrolled(course.getName());
        } finally {
            close();
        }
    }

    @Override
    public void deleteCourseById(int id) throws InvalidCourseDeleteRequestStudentEnrolled {
        initializeTransaction();
        Course course = entityManager.find(Course.class, id);
        try {
            entityManager.remove(course);
            commit();
        } catch (RollbackException e) {
            throw new InvalidCourseDeleteRequestStudentEnrolled(course.getName());
        } finally {
            close();
        }
    }


    @Override
    public void resetTable() {
        initializeTransaction();

        entityManager.createQuery("DELETE FROM Course")
                .executeUpdate();
        commit();

        initializeTransaction();

        entityManager.createNativeQuery("ALTER TABLE courses AUTO_INCREMENT = 1")
                .executeUpdate();

        commit();
    }

}
