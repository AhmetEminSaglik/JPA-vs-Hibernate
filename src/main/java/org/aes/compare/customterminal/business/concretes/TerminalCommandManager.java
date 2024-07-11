package org.aes.compare.customterminal.business.concretes;

import org.aes.compare.customterminal.business.abstracts.RunnableTerminalCommand;
import org.aes.compare.customterminal.business.abstracts.TerminalCommandLayout;
import org.aes.compare.customterminal.config.concrete.CMDLineSingletonBuilder;
import org.aes.compare.customterminal.model.EnumCRUDCommand;
import org.aes.compare.customterminal.model.EnumModelCommand;
import org.aes.compare.customterminal.model.TerminalCMD;
import org.aes.compare.uiconsole.model.EnumUIConsoleOperation;

public class TerminalCommandManager implements RunnableTerminalCommand {
    private static final char underscore = '_';

    public TerminalCommandManager() {
    }

    @Override
    public void runCustomCommand(TerminalCommandLayout layout, TerminalCMD cmd) {
        if (cmd.getStandardCommand() != null) {
            runStandardCommand(layout, cmd);
        } else {
            runModelCommand(cmd);
        }
    }

    /*public static Map<String, Runnable> getMenuOptions() {
        if (menuOptions == null) {
            menuOptions = new HashMap<>();
            fillMenuOption();
        }
        return menuOptions;
    }*/

    private void runModelCommand(TerminalCMD cmd) {
        String operationName = convertCommandstoOperationName(cmd.getCrudCommand(), cmd.getModelCommand());
        switch (EnumUIConsoleOperation.valueOf(operationName)) {
            case CREATE_ADDRESS:
                System.out.println("Create addrese geldik");
                break;
            case CREATE_STUDENT:
                System.out.println("Create Studenta geldik");
                break;
            case CREATE_COURSE:
                System.out.println("Create course geldik");
                break;
            case CREATE_EXAM_RESULT:
                System.out.println("Create ExamResult'a geldik");
                break;


            case READ_ADDRESS:
                System.out.println("READ addrese geldik");
                break;
            case READ_STUDENT:
                System.out.println("READ Studenta geldik");
                break;
            case READ_COURSE:
                System.out.println("READ course geldik");
                break;
            case READ_EXAM_RESULT:
                System.out.println("READ ExamResult'a geldik");
                break;


            case UPDATE_ADDRESS:
                System.out.println("UPDATE addrese geldik");
                break;
            case UPDATE_STUDENT:
                System.out.println("UPDATE Studenta geldik");
                break;
            case UPDATE_COURSE:
                System.out.println("UPDATE course geldik");
                break;
            case UPDATE_EXAM_RESULT:
                System.out.println("UPDATE ExamResult'a geldik");
                break;


            case DELETE_ADDRESS:
                System.out.println("DELETE addrese geldik");
                break;
            case DELETE_STUDENT:
                System.out.println("DELETE Studenta geldik");
                break;
            case DELETE_COURSE:
                System.out.println("DELETE course geldik");
                break;
            case DELETE_EXAM_RESULT:
                System.out.println("DELETE ExamResult'a geldik");
                break;


        }
        cmd.getModelCommand();

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
