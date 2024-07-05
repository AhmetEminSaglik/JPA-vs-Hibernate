package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.List;

public class StudentServiceImpJPA extends JpaImplementation<Student> implements StudentService {

    @Override
    public void save(Student s) {
        initializeTransaction();
        getEntityManager().persist(s);
        commit();
    }

    @Override
    public void update(Student s) {
        initializeTransaction();
        getEntityManager().merge(s);
        commit();
    }

    @Override
    public void resetTable() {
        initializeTransaction();

        getEntityManager().createQuery("DELETE FROM Student")
                .executeUpdate();
        commit();

        initializeTransaction();

        getEntityManager().createNativeQuery("ALTER TABLE students AUTO_INCREMENT = 1")
                .executeUpdate();
        commit();

    }

    @Override
    public Student findById(int id) {
        initializeTransaction();
        Student student =getEntityManager().find(Student.class,id);
        commit();
        return  student;
    }

    @Override
    public List<Student> findAll() {
        initializeTransaction();
        Query query = getEntityManager().createQuery("SELECT s Student ");
        List<Student> students = query.getResultList();
        commit();
        return students;
    }

    @Override
    public boolean isStudentEnrolledInCourse(int studentId, String courseName) {
        initializeTransaction();
        Student student = getEntityManager().find(Student.class, studentId);
        commit();
        if (student != null) {
            List<Course> courses = student.getCourses();
            for (Course course : courses) {
                if (course.getName().equals(courseName)) {
                    return true;
                }
            }
        }
        return false;
//        Student student= getEntityManager().createQuery(
//                "SELECT s FROM s LEFT JOIN Course where s.id=:studentId AND s.course_"
//        );
//        return null;
    }

    @Override
    public Student findByStudentIdWithCourseName(int studentId, String courseName) throws InvalidStudentCourseMatchForExamResult {
        initializeTransaction();
        try {
            Student student = getEntityManager().createQuery(
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
    public void deleteById(int id) {
        initializeTransaction();
        Student student = getEntityManager().find(Student.class, id);
//        student.setAddress(null);
        getEntityManager().remove(student);
        commit();
    }

}
