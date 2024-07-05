package org.aes.compare.orm.business.concrete.jpa;

import jakarta.persistence.TypedQuery;
import org.aes.compare.orm.business.abstracts.ExamResultService;
import org.aes.compare.orm.business.abstracts.StudentService;
import org.aes.compare.orm.business.concrete.jpa.abstracts.JpaImplementation;
import org.aes.compare.orm.exceptions.InvalidStudentCourseMatchForExamResult;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.model.Student;

import java.util.List;

public class ExamResultServiceImplJPA extends JpaImplementation<ExamResult> implements ExamResultService {
    StudentService studentService = new StudentServiceImpJPA();

    @Override
    public void save(ExamResult examResult) throws InvalidStudentCourseMatchForExamResult {
        /*boolean studentCourseMatchSuccessfull = studentService.isStudentEnrolledInCourse(examResult.getStudent().getId(), examResult.getCourse().getName());

        if (!studentCourseMatchSuccessfull){
            System.out.println("INVALID Student-Course Match");
            return;
        }*/
        Student student = studentService.findByStudentIdWithCourseName(examResult.getStudent().getId(), examResult.getCourse().getName());
        if (student == null) {
            throw new InvalidStudentCourseMatchForExamResult(examResult);
        }
        initializeTransaction();
        getEntityManager().persist(examResult);
        commit();
        System.out.println("Exam Result is saved : " + examResult);

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
