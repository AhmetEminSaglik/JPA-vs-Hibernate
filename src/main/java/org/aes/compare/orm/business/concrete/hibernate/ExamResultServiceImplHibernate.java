package org.aes.compare.orm.business.concrete.hibernate;

import jakarta.persistence.TypedQuery;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.hibernate.abstracts.HibernateImplementation;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.List;

public class ExamResultServiceImplHibernate extends HibernateImplementation<ExamResult> implements ExamResultService {
    private StudentService studentService = new StudentServiceImplHibernate();
    private CourseService courseService = new CourseServiceImplHibernate();

    @Override
    public void save(ExamResult examResult) throws InvalidStudentCourseMatchForExamResult {
        Student student = studentService.findByStudentIdWithCourseName(examResult.getStudent().getId(), examResult.getCourse().getName());
        if (student == null) {
            throw new InvalidStudentCourseMatchForExamResult(examResult);
        }
        initializeTransaction();
        session.persist(examResult);
        commit();
    }

    @Override
    public List<ExamResult> findAll() {
        initializeTransaction();
        TypedQuery<ExamResult> query = session.createQuery("SELECT e FROM ExamResult e", ExamResult.class);
        List<ExamResult> examResults = query.getResultList();
        commit();
        return examResults;
    }

    @Override
    public List<ExamResult> findAllByStudentId(int studentId) {
        initializeTransaction();
        TypedQuery<ExamResult> query = session.createQuery(
                "SELECT e FROM ExamResult e where " +
                        "e.student.id=:studentId ", ExamResult.class);
        query.setParameter("studentId", studentId);
        List<ExamResult> examResults = query.getResultList();
        commit();
        return examResults;

    }

    @Override
    public List<ExamResult> findAllByStudentIdAndCourseName(int studentId, String courseName) {
        Course course = courseService.findByName(courseName);

        initializeTransaction();
        TypedQuery<ExamResult> query = session.
                createQuery("SELECT e FROM ExamResult e where " +
                        "e.student.id=:studentId " +
                        "and e.course.id=:courseId", ExamResult.class);
        query.setParameter("studentId", studentId);
        query.setParameter("courseId", course.getId());
        List<ExamResult> examResults = query.getResultList();
        commit();
        return examResults;
    }

    @Override
    public List<ExamResult> findAllByCourseName(String courseName) {
        Course course = courseService.findByName(courseName);
        initializeTransaction();
        TypedQuery<ExamResult> query = session.createQuery("SELECT e FROM ExamResult e where " +
                "course_id=:courseId", ExamResult.class);
        query.setParameter("courseId", course.getId());
        List<ExamResult> examResults = query.getResultList();
        commit();
        return examResults;
    }

    @Override
    public ExamResult findById(int id) {
        initializeTransaction();
        ExamResult examResult = session.find(ExamResult.class, id);
        commit();
        return examResult;
    }

    @Override
    public void update(ExamResult examResult) {
        initializeTransaction();
        session.merge(examResult);
        commit();
    }

    @Override
    public void deleteById(int id) {
        initializeTransaction();
        ExamResult examResult = session.find(ExamResult.class, id);
        session.remove(examResult);
        commit();
    }

    @Override
    public void resetTable() {
        initializeTransaction();
        session.createNativeMutationQuery("DELETE FROM exam_result")
                .executeUpdate();

        session.createNativeMutationQuery(
                        "ALTER TABLE exam_result AUTO_INCREMENT = 1")
                .executeUpdate();
        commit();
    }

}
