package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.TypedQuery;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.List;

public class ExamResultServiceImplJPA extends JpaImplementation<ExamResult> implements ExamResultService {
    private StudentService studentService = new StudentServiceImpJPA();
    private CourseService courseService = new CourseServiceImplJPA();

    @Override
    public void save(ExamResult examResult) throws InvalidStudentCourseMatchForExamResult {
        Student student = studentService.findByStudentIdWithCourseName(examResult.getStudent().getId(), examResult.getCourse().getName());
        if (student == null) {
            throw new InvalidStudentCourseMatchForExamResult(examResult);
        }
        initializeTransaction();
        getEntityManager().persist(examResult);
        commit();
    }

    @Override
    public List<ExamResult> findAll() {
        initializeTransaction();
        TypedQuery<ExamResult> query = getEntityManager().createQuery("SELECT e FROM ExamResult e", ExamResult.class);
        List<ExamResult> examResults = query.getResultList();
        commit();
        return examResults;
    }

    @Override
    public List<ExamResult> findAllByStudentId(int studentId) {
        initializeTransaction();
        TypedQuery<ExamResult> query = getEntityManager().createQuery(
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
        TypedQuery<ExamResult> query = getEntityManager().
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
        TypedQuery<ExamResult> query = getEntityManager().createQuery("SELECT e FROM ExamResult e where " +
                "course_id=:courseId", ExamResult.class);
        query.setParameter("courseId", course.getId());
        List<ExamResult> examResults = query.getResultList();
        commit();
        return examResults;
    }

    @Override
    public ExamResult findById(int id) {
        initializeTransaction();
        ExamResult examResult = getEntityManager().find(ExamResult.class, id);
        commit();
        return examResult;
    }

    @Override
    public void update(ExamResult examResult) {
        initializeTransaction();
        getEntityManager().merge(examResult);
        commit();
    }

    @Override
    public void deleteById(int id) {
        initializeTransaction();
        ExamResult examResult = getEntityManager().find(ExamResult.class, id);
        getEntityManager().remove(examResult);
        commit();
    }

    @Override
    public void resetTable() {
        initializeTransaction();
        getEntityManager().createQuery("DELETE FROM ExamResult")
                .executeUpdate();
        commit();
        //todo burda init comit init comit yerine, init  2 islem ve comit test edicem
        initializeTransaction();

        getEntityManager().createNativeQuery(
                        "ALTER TABLE exam_result AUTO_INCREMENT = 1")
                .executeUpdate();
        commit();
    }
}
