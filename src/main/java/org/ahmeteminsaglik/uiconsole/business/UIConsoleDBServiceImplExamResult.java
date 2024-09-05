package org.ahmeteminsaglik.uiconsole.business;

import org.ahmeteminsaglik.metadata.MetaData;
import org.ahmeteminsaglik.orm.ORMApp;
import org.ahmeteminsaglik.orm.config.ORMConfigSingleton;
import org.ahmeteminsaglik.orm.consoleapplication.utility.FacadeUtility;
import org.ahmeteminsaglik.orm.model.Address;
import org.ahmeteminsaglik.orm.model.ExamResult;
import org.ahmeteminsaglik.orm.utility.ColorfulTextDesign;

import java.util.List;

public class UIConsoleDBServiceImplExamResult {
    private final ORMApp ormApp = new ORMApp();

    private void addLoggerData() {
        LoggerProcessStack.addWithInnerPrefix(MetaData.PREFIX_INNER_TERMINAL_PROCESS);
        LoggerProcessStack.addWithInnerPrefix(MetaData.PROCESS_PREFIX_EXAM_RESULT);
    }
    private  void destroyTerminalProcessSuccessfully(){
        FacadeUtility.destroyProcessSuccessfully(3);
    }

    public void create() {
        addLoggerData();
//        ExamResult examResult =
                ormApp.getExamResultFacade().save();
       destroyTerminalProcessSuccessfully();
//        return examResult;
    }


    public void read() {
        addLoggerData();
        ormApp.getExamResultFacade().findAll();
       destroyTerminalProcessSuccessfully();
//        return examResult;
    }

    public void update() {
        addLoggerData();
        ormApp.getExamResultFacade().update();
        destroyTerminalProcessSuccessfully();
    }


    public void delete() {
        addLoggerData();
        ormApp.getExamResultFacade().delete();
        destroyTerminalProcessSuccessfully();
    }


}
