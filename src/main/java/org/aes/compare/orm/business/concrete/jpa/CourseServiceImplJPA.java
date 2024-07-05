package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.TypedQuery;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.aes.compare.orm.model.courses.abstracts.Course;

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

        /*
        * 2. Merge the entity before deleting
        you can try merging the detached entity back into the Hibernate session before deleting it.
* */
        initializeTransaction();
        System.out.println("Deleting course with name: " + name);
        Course course = getEntityManager().createQuery("SELECT c FROM Course c WHERE c.name = :name", Course.class)
                .setParameter("name", name)
                .getSingleResult();
        course = getEntityManager().merge(course);
        getEntityManager().remove(course);
        commit();
        System.out.println("Course deleted: " + course);
        /*

        initializeTransaction();
        TypedQuery<Course> query = getEntityManager().createQuery(
                "SELECT c FROM Course c  WHERE c.name=:data", Course.class
        );
        query.setParameter("data", name);

        Course course = query.getSingleResult();


        System.out.println(course.getId());
        System.out.println(course.getName());
        System.out.println(course.getCredits());
        getEntityManager().remove(course);
        commit();

*/
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
