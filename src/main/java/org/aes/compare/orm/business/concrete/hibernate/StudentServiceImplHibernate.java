package org.aes.compare.orm.business.concrete.hibernate;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.hibernate.abstracts.HibernateImplementation;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class StudentServiceImplHibernate extends HibernateImplementation<Student> implements StudentService {
/*    @Override
    protected void createFactory() {
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();
    }*/

    @Override
    public void save(Student s) {

        try {
            initializeTransaction();
            session.persist(s);
        } catch (Exception e) {
            System.out.println(ColorfulTextDesign.getErrorColorText(e.getMessage()));
        } finally {
            commit();
        }
    }

    @Override
    public Student findById(int id) {
        initializeTransaction();
        Student student = session.find(Student.class, id);
        commit();
        return student;
    }

    @Override
    public List<Student> findAll() {
        initializeTransaction();
        TypedQuery<Student> query = session.createQuery("SELECT s FROM Student s ", Student.class);
        List<Student> students = query.getResultList();
        commit();
        return students;
    }

    @Override
    public Student findByStudentIdWithCourseName(int studentId, String courseName) throws InvalidStudentCourseMatchForExamResult {
        initializeTransaction();
        try {
            Student student = session.createQuery(
                            "SELECT s FROM Student s " +
                                    "JOIN s.courses c " +
                                    "WHERE s.id = :studentId AND c.name = :courseName", Student.class)
                    .setParameter("studentId", studentId)
                    .setParameter("courseName", courseName)
                    .getSingleResult();
            return student;
        } catch (NoResultException e) {
            throw new InvalidStudentCourseMatchForExamResult(studentId, courseName);
        } finally {
            commit();
        }

    }

    @Override
    public void update(Student s) {
        initializeTransaction();
        session.merge(s);
        commit();
    }

    @Override
    public void deleteById(int id) {
        initializeTransaction();
        Student student = session.find(Student.class, id);
//        student.setAddress(null);
        session.remove(student);
        commit();
    }

    @Override
    public void resetTable() {

        initializeTransaction();

        session.createNativeMutationQuery("DELETE FROM students")
                .executeUpdate();
        commit();

        initializeTransaction();

        session.createNativeMutationQuery("ALTER TABLE students AUTO_INCREMENT = 1")
                .executeUpdate();
        commit();
    }


}
