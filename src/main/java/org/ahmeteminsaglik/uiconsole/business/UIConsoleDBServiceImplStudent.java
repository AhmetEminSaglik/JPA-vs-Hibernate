package org.ahmeteminsaglik.uiconsole.business;

import org.ahmeteminsaglik.metadata.MetaData;
import org.ahmeteminsaglik.orm.ORMApp;
import org.ahmeteminsaglik.orm.consoleapplication.utility.FacadeUtility;
import org.ahmeteminsaglik.orm.model.Student;

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

    public void create() {
        addLoggerData();
        Student student = ormApp.getStudentFacade().save();
        destroyTerminalProcessSuccessfully();
//        return student;
    }


    public void read() {
        addLoggerData();
        List<Student> students = ormApp.getStudentFacade().findAll();
        destroyTerminalProcessSuccessfully();
//        return students;
    }


    public void update() {
        addLoggerData();
        Student student = ormApp.getStudentFacade().update();
        destroyTerminalProcessSuccessfully();
//        return student;
    }


    public void delete() {
        addLoggerData();
        ormApp.getStudentFacade().delete();
        destroyTerminalProcessSuccessfully();
    }
}
