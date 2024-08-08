package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.comparator.StudentComparator;
import org.aes.compare.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.exceptions.InvalidStudentDeleteRequestHavingExamResult;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.utility.ColorfulTextDesign;

import java.util.List;

public class StudentServiceImpJPA extends JpaImplementation<Student> implements StudentService {
    private final StudentComparator comparator = new StudentComparator();

    @Override
    public void save(Student s) {
        initializeTransaction();
        try {
            entityManager.persist(s);
        } catch (Exception e) {
            System.out.println(ColorfulTextDesign.getErrorColorTextWithPrefix(e.getMessage()));
        } finally {
            commit();
        }
    }

    @Override
    public Student findById(int id) {
        initializeTransaction();
        Student student = entityManager.find(Student.class, id);
        commit();
        return student;
    }

    @Override
    public List<Student> findAll() {
        initializeTransaction();
        TypedQuery<Student> query = entityManager.createQuery("SELECT s FROM Student s ", Student.class);
        List<Student> students = query.getResultList();
        commit();
        students.sort(comparator);
        return students;
    }


    @Override
    public Student findByStudentIdWithCourseName(int studentId, String courseName) throws InvalidStudentCourseMatchForExamResult {
        initializeTransaction();
        try {
            Student student = entityManager.createQuery(
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
        entityManager.merge(s);
        commit();
    }

    @Override
    public void deleteById(int id) throws InvalidStudentDeleteRequestHavingExamResult {
        initializeTransaction();
        Student student = entityManager.find(Student.class, id);
        try {
            entityManager.remove(student);
            commit();
        } catch (Exception e) {
            throw new InvalidStudentDeleteRequestHavingExamResult(student);
        }
    }

    @Override
    public void resetTable() {
        initializeTransaction();

        entityManager.createQuery("DELETE FROM Student")
                .executeUpdate();
        commit();

        initializeTransaction();

        entityManager.createNativeQuery("ALTER TABLE students AUTO_INCREMENT = 1")
                .executeUpdate();
        commit();

    }

}
