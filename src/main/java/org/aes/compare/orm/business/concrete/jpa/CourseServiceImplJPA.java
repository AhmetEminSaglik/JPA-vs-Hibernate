package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.TypedQuery;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.List;

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
        commit();
        return course;
    }

    @Override
    public void deleteCourseByName(String name) {
        initializeTransaction();
        System.out.println("silinecek dersin adi : " + name);
        TypedQuery<Course> query = getEntityManager().createQuery(
//                "SELECT c FROM Course c LEFT JOIN c.student WHERE c.name=:data", Course.class
                "SELECT c FROM Course c  WHERE c.name=:data", Course.class
        );
        query.setParameter("data", name);

        Course course = query.getSingleResult();
        System.out.println("Retrieved Course : " + course);
        System.out.println("course.getStudents() : " + course.getStudents());

        getEntityManager().remove(course);
        commit();
        System.out.println("Course is removed : " + course);


//        TypedQuery

    }

    @Override
    public void deleteCourseById(int id) {
        System.out.println("Removing course's id : "+id);
        initializeTransaction();
        Course course=getEntityManager().find(Course.class,id);
        getEntityManager().remove(course);
        commit();
        System.out.println("Course is removed by id");
    }

    @Override
    public void updateCourseByName(Course course) {
        initializeTransaction();
        System.out.println("Gelen Course :" + course);
        Course tmpCourse = getEntityManager().find(Course.class, course.getId());
        System.out.println("tmp Course : ");
        course= getEntityManager().merge(course);
        commit();
    }

    @Override
    public void resetTable() {

        System.out.println("Reset Table ");
        initializeTransaction();
        getEntityManager().createQuery("DELETE FROM Course")
                .executeUpdate();
        System.out.println("DELETE FROM Course executeUpdate  ");
        commit();
        System.out.println("commit");
        initializeTransaction();
        System.out.println("initializeTransaction");
        //Native query uses database table name
        // Query uses Java Class' name
        getEntityManager().createNativeQuery("ALTER TABLE courses AUTO_INCREMENT = 1")
                .executeUpdate();
        System.out.println("ALTER TABLE courses AUTO_INCREMENT");
        commit();
        System.out.println("All Courses are deleted");
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
