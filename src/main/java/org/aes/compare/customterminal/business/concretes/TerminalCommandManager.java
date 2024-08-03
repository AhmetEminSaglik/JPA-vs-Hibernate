package org.aes.compare.customterminal.business.concretes;

import org.aes.compare.customterminal.business.abstracts.RunnableTerminalCommand;
import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.config.concrete.CMDLineSingletonBuilder;
import org.aes.compare.customterminal.model.EnumCRUDCommand;
import org.aes.compare.customterminal.model.EnumModelCommand;
import org.aes.compare.customterminal.model.TerminalCMD;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.business.UIConsoleDBServiceDisplayAddressMenu;
import org.aes.compare.uiconsole.business.UIConsoleDBServiceImplCourse;
import org.aes.compare.uiconsole.business.UIConsoleDBServiceImplExamResult;
import org.aes.compare.uiconsole.business.UIConsoleDBServiceImplStudent;
import org.aes.compare.uiconsole.model.EnumUIConsoleOperation;

public class TerminalCommandManager extends TerminalCommandLayout implements RunnableTerminalCommand {
    private static final char underscore = '_';
    public UIConsoleDBServiceDisplayAddressMenu displayAddressMenu = new UIConsoleDBServiceDisplayAddressMenu(this, this);
    public UIConsoleDBServiceImplStudent displayStudentMenu = new UIConsoleDBServiceImplStudent(this, this);
    public UIConsoleDBServiceImplCourse displayCourseMenu = new UIConsoleDBServiceImplCourse(this, this);
    public UIConsoleDBServiceImplExamResult displayExamResultMenu = new UIConsoleDBServiceImplExamResult();

    public TerminalCommandManager() {
    }

    @Override
    public void runCustomCommand(TerminalCommandLayout layout, TerminalCMD cmd) {
        if (cmd.getStandardCommand() != null) {
            runStandardCommand(layout, cmd);
        } else if (cmd.getCrudCommand() != null && cmd.getModelCommand() != null) {
            runModelCommand(cmd);
        }
    }

    private void runModelCommand(TerminalCMD cmd) {
        String operationName = convertCommandstoOperationName(cmd.getCrudCommand(), cmd.getModelCommand());
        switch (EnumUIConsoleOperation.valueOf(operationName)) {
            case CREATE_ADDRESS:
                displayAddressMenu.save();
                break;
            case CREATE_STUDENT:
                displayStudentMenu.save();
                break;
            case CREATE_COURSE:
                displayCourseMenu.save();
                break;
            case CREATE_EXAM_RESULT:
                displayExamResultMenu.save();
                break;


            case READ_ADDRESS:
                displayAddressMenu.findAll();
                break;
            case READ_STUDENT:
                displayStudentMenu.findAll();
                break;
            case READ_COURSE:
                displayCourseMenu.findAll();
                break;
            case READ_EXAM_RESULT:
                displayExamResultMenu.findAll();
                break;


            case UPDATE_ADDRESS:
                displayAddressMenu.update();
                break;
            case UPDATE_STUDENT:
                displayStudentMenu.update();
                break;
            case UPDATE_COURSE:
                displayCourseMenu.updateCourseByName();
                break;
            case UPDATE_EXAM_RESULT:
                displayExamResultMenu.update();
                break;


            case DELETE_ADDRESS:
                displayAddressMenu.delete();
                break;
            case DELETE_STUDENT:
                displayStudentMenu.delete();
                break;
            case DELETE_COURSE:
                displayCourseMenu.deleteCourseById();
                break;
            case DELETE_EXAM_RESULT:
                displayExamResultMenu.delete();
                break;


        }
        if (isAllowedCurrentProcess) {
            System.out.println(ColorfulTextDesign.getTextForCanceledProcess("Process is canceled."));
        }
//        cmd.getModelCommand();

    }

    private String convertCommandstoOperationName(EnumCRUDCommand crudCommand, EnumModelCommand modelCommand) {
        return crudCommand.getLongName().toUpperCase() + underscore + modelCommand.getName().toUpperCase();
    }


    private void runStandardCommand(TerminalCommandLayout layout, TerminalCMD cmd) {
        switch (cmd.getStandardCommand()) {
            case HELP:
                printHelpInfo();
                break;
            case QUIT_CURRENT_PROCESS:
                layout.quitCurrentProcess();
                break;
            case EXIT_PROGRAM:
                System.exit(0);
                break;
        }
    }

    public void printHelpInfo() {
        CMDLineSingletonBuilder.getCmdLine().printHelpInfoForCustomTerminal();
    }

}
