package org.aes.compare.uiconsole.business;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.ORMApp;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.model.Student;

import java.util.List;

public class UIConsoleDBServiceImplStudent {
    private final ORMApp ormApp = new ORMApp();

    private void addLoggerData() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_INNER_TERMINAL_PROCESS);
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_STUDENT);
    }

    private void destroyTerminalProcessSuccessfully() {
        FacadeUtility.destroyProcessSuccessfully(3);
    }

    public Student create() {
        addLoggerData();
        Student student = ormApp.getStudentFacade().save();
        destroyTerminalProcessSuccessfully();
        return student;
    }


    public List<Student> read() {
        addLoggerData();
        List<Student> students = ormApp.getStudentFacade().findAll();
        destroyTerminalProcessSuccessfully();
        return students;
    }


    public Student update() {
        addLoggerData();
        Student student = ormApp.getStudentFacade().update();
        destroyTerminalProcessSuccessfully();
        return student;
    }


    public void delete() {
        addLoggerData();
        ormApp.getStudentFacade().delete();
        destroyTerminalProcessSuccessfully();
    }
}
