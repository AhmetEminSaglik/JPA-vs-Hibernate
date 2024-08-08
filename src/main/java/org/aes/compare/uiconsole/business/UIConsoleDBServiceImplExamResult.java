package org.aes.compare.uiconsole.business;

import org.aes.compare.metadata.MetaData;
import org.aes.compare.orm.ORMApp;
import org.aes.compare.orm.config.ORMConfigSingleton;
import org.aes.compare.orm.consoleapplication.utility.FacadeUtility;
import org.aes.compare.orm.model.Address;
import org.aes.compare.orm.model.ExamResult;
import org.aes.compare.orm.utility.ColorfulTextDesign;

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
