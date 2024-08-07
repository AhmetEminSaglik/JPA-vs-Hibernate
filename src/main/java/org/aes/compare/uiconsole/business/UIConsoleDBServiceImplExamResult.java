package org.aes.compare.uiconsole.business;

import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.utility.ColorfulTextDesign;

public class UIConsoleDBServiceImplExamResult {
    private final ORMConfigSingleton ormConfig = new ORMConfigSingleton();


    public void save() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE save  ExamResult"));

        //ormConfig.getExamResultService().save(examResult);
    }


    public void findAll() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE findAll  ExamResult"));
        // return ormConfig.getExamResultService().findAll();
    }


    public void findAllByStudentId() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE findAllByStudentId  ExamResult"));
        //return ormConfig.getExamResultService().findAllByStudentId(studentId);
    }


    public void findAllByStudentIdAndCourseName() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE findAllByStudentIdAndCourseName  ExamResult"));
        // return ormConfig.getExamResultService().findAllByStudentIdAndCourseName(studentId, courseName);
    }


    public void findAllByCourseName() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE findAllByCourseName  ExamResult"));
        //return ormConfig.getExamResultService().findAllByCourseName(courseName);
    }


    public void findById() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE findById  ExamResult"));
        //  return ormConfig.getExamResultService().findById(id);
    }


    public void update() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE update  ExamResult"));
        //  ormConfig.getExamResultService().update(examResult);
    }


    public void delete() {
        System.out.println(ColorfulTextDesign.getWarningColorTextWithPrefix("UI CONSOLE delete  ExamResult"));
        // ormConfig.getExamResultService().deleteById(id);
    }


    public void resetTable() {
        //  ormConfig.getExamResultService().resetTable();
    }
}
