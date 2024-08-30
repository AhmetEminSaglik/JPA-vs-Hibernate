package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.TypedQuery;
import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.business.abstracts.CourseService;
import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.comparator.ExamResultComparator;
import org.aes.compare.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.model.Student;
import org.aes.compare.orm.model.courses.abstracts.Course;

import java.util.List;

public class ExamResultServiceImplJPA extends JpaImplementation<ExamResult> implements ExamResultService {

    private final ExamResultComparator comparator = new ExamResultComparator();
    private final StudentService studentService = new StudentServiceImpJPA();
    private final CourseService courseService = new CourseServiceImplJPA();

    @Override
    public void save(ExamResult examResult) throws InvalidStudentCourseMatchForExamResult {
        Student student = studentService.findByStudentIdWithCourseName(examResult.getStudent().getId(), examResult.getCourse().getName());
        if (student == null) {
            throw new InvalidStudentCourseMatchForExamResult(examResult);
        }
        initializeTransaction();
        entityManager.persist(examResult);
        commit();
    }

    @Override
    public List<ExamResult> findAll() {
        initializeTransaction();
        TypedQuery<ExamResult> query = entityManager.createQuery("SELECT e FROM ExamResult e", ExamResult.class);
        List<ExamResult> examResults = query.getResultList();
        commit();
        examResults.sort(comparator);
        return examResults;
    }

    @Override
    public List<ExamResult> findAllByStudentId(int studentId) {
        initializeTransaction();
        TypedQuery<ExamResult> query = entityManager.createQuery(
                "SELECT e FROM ExamResult e where " +
                        "e.student.id=:studentId ", ExamResult.class);
        query.setParameter("studentId", studentId);
        List<ExamResult> examResults = query.getResultList();
        commit();
        examResults.sort(comparator);
        return examResults;
    }

    @Override
    public List<ExamResult> findAllByStudentIdAndCourseName(int studentId, String courseName) {
        Course course = courseService.findByName(courseName);

        if (course == null) {
            System.out.println(MetaData.getCourseNameIsNotFound(courseName));
            return null;
        }
        initializeTransaction();
        TypedQuery<ExamResult> query = entityManager.
                createQuery("SELECT e FROM ExamResult e where " +
                        "e.student.id=:studentId " +
                        "and e.course.id=:courseId", ExamResult.class);
        query.setParameter("studentId", studentId);
        query.setParameter("courseId", course.getId());
        List<ExamResult> examResults = query.getResultList();
        commit();
        examResults.sort(comparator);
        return examResults;
    }

    @Override
    public List<ExamResult> findAllByCourseName(String courseName) {
        Course course = courseService.findByName(courseName);

        if (course == null) {
            System.out.println(MetaData.getCourseNameIsNotFound(courseName));
            return null;
        }

        initializeTransaction();
        TypedQuery<ExamResult> query = entityManager.createQuery("SELECT e FROM ExamResult e where " +
                "e.course.id=:courseId", ExamResult.class);
        query.setParameter("courseId", course.getId());
        List<ExamResult> examResults = query.getResultList();
        commit();
        examResults.sort(comparator);
        return examResults;
    }

    @Override
    public ExamResult findById(int id) {
        initializeTransaction();
        ExamResult examResult = entityManager.find(ExamResult.class, id);
        commit();
        return examResult;
    }

    @Override
    public void update(ExamResult examResult) {
        initializeTransaction();
        entityManager.merge(examResult);
        commit();
    }

    @Override
    public void deleteById(int id) {
        initializeTransaction();
        ExamResult examResult = entityManager.find(ExamResult.class, id);
        entityManager.remove(examResult);
        commit();
    }

    @Override
    public void resetTable() {
        initializeTransaction();
        entityManager.createQuery("DELETE FROM ExamResult")
                .executeUpdate();
        entityManager.createNativeQuery(
                        "ALTER TABLE exam_results AUTO_INCREMENT = 1")
                .executeUpdate();
        commit();
    }

}
