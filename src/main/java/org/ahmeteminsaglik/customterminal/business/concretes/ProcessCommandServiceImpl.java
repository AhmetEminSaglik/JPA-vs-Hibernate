package org.ahmeteminsaglik.customterminal.business.concretes;

import org.ahmeteminsaglik.customterminal.business.abstracts.ProcessCommandService;
import org.ahmeteminsaglik.customterminal.config.concrete.CMDLineSingletonBuilder;
import org.ahmeteminsaglik.customterminal.model.EnumCRUDCommand;
import org.ahmeteminsaglik.customterminal.model.EnumModelCommand;
import org.ahmeteminsaglik.customterminal.model.EnumStandardCommand;
import org.ahmeteminsaglik.customterminal.model.TerminalCMD;
import org.ahmeteminsaglik.orm.utility.ColorfulTextDesign;
import org.ahmeteminsaglik.uiconsole.model.EnumCMDLineParserResult;
import org.ahmeteminsaglik.utility.ConsoleColors;


public class ProcessCommandServiceImpl implements ProcessCommandService {
    //    private EnumCRUDCommand reqCrudOperation;
//    private EnumModelCommand reqModel;
//    private EnumStandardCommand reqTerminalCmd;
    private final String helpMsg = "Type '" + EnumStandardCommand.HELP.getShortName() + "' or '" + EnumStandardCommand.HELP.getLongName() + "'to get help.";
    private TerminalCMD terminalCMD = new TerminalCMD();

    @Override
    public EnumCMDLineParserResult processCommand(String text) {
        terminalCMD = new TerminalCMD();
        return parseCommand(text);
    }

    @Override
    public EnumCMDLineParserResult parseCommand(String text) {
        text = clearCommand(text);
        String[] cmdArr = text.split(" ");
        try {
            for (int i = 0; i < cmdArr.length; i++) {
            }
            figureOutRequestedTerminalOperation(cmdArr);
            figureOutRequestedCRUDOperation(cmdArr);
            figureOutRequestedObjectToProcess(cmdArr);
            return decideProcess(cmdArr);
        } catch (Exception e) {
            System.out.println(ColorfulTextDesign.getText(ConsoleColors.RED_BRIGHT, "Error : " + e.getMessage()));
            return EnumCMDLineParserResult.RUN_FOR_INVALID_COMMAND;
        }
    }

    @Override
    public String clearCommand(String text) {
        text = text.replaceFirst(CMDLineSingletonBuilder.getCmdLine().getPrefix(), "").trim();
        text = text.replaceAll("\\s+", " ");
        return text;
    }

    @Override
    public TerminalCMD getDecidedTerminalCMD() {
        return terminalCMD;
    }

    private void figureOutRequestedTerminalOperation(String[] cmdArr) throws Exception {
        for (int i = 0; i < cmdArr.length; i++) {
            if (!cmdArr[i].isEmpty() || cmdArr[i] != CMDLineSingletonBuilder.getCmdLine().getPrefix()) {
                for (EnumStandardCommand tmp : EnumStandardCommand.values()) {
                    if (cmdArr[i].equals(tmp.getShortName()) || cmdArr[i].equals(tmp.getLongName())) {
                        if (terminalCMD.getStandardCommand() == null) {
                            terminalCMD.setStandardCommand(tmp);
                        } else {
                            throw new Exception("Invalid Requested terminal Operation." + helpMsg);
                        }
                        cmdArr[i] = "";
                    }
                }
            }
        }
    }

    private void figureOutRequestedCRUDOperation(String[] cmdArr) throws Exception {
        for (int i = 0; i < cmdArr.length; i++) {
            if (!cmdArr[i].isEmpty() || cmdArr[i] != CMDLineSingletonBuilder.getCmdLine().getPrefix()) {
                for (EnumCRUDCommand tmp : EnumCRUDCommand.values()) {
                    if (cmdArr[i].equals(tmp.getShortName()) || cmdArr[i].equals(tmp.getLongName())) {
                        if (terminalCMD.getCrudCommand() == null) {
                            terminalCMD.setCrudCommand(tmp);
                        } else {
                            throw new Exception("Invalid Requested CRUD Operation." + helpMsg);
                        }
                        cmdArr[i] = "";
                    }
                }
            }
        }
    }

    private void figureOutRequestedObjectToProcess(String[] cmdArr) throws Exception {
        for (int i = 0; i < cmdArr.length; i++) {
            if (!cmdArr[i].isEmpty() || cmdArr[i] != CMDLineSingletonBuilder.getCmdLine().getPrefix()) {
                for (EnumModelCommand tmp : EnumModelCommand.values()) {
                    if (cmdArr[i].equals(tmp.getShortName())||cmdArr[i].equals(tmp.getName())) {
                        if (terminalCMD.getModelCommand() == null) {
                            terminalCMD.setModelCommand(tmp);
                        } else {
                            throw new Exception("Invalid Requested CRUD Operation." + helpMsg);
                        }
                        cmdArr[i] = "";
                    }
                }
            }
        }
    }

    private EnumCMDLineParserResult decideProcess(String[] cmdArr) {
        String errMsg = "Invalid command.";
        if (terminalCMD.getStandardCommand() != null) {
            if (cmdArr.length == 1) {
                if (terminalCMD.getStandardCommand().getLongName().equalsIgnoreCase(EnumStandardCommand.QUIT_CURRENT_PROCESS.getLongName())) {
                    return EnumCMDLineParserResult.CMD_CANCEL_PROCESS;
                }
                return EnumCMDLineParserResult.RUN_FOR_CMDLINE;
            } else {
                System.out.println(ColorfulTextDesign.getText(ConsoleColors.RED_BRIGHT, errMsg + helpMsg));
                terminalCMD.setStandardCommand(null);
            }
        } else if (terminalCMD.getCrudCommand() != null && terminalCMD.getModelCommand() != null) {
            return EnumCMDLineParserResult.RUN_FOR_CMDLINE;
        } else {
            System.out.println(ColorfulTextDesign.getText(ConsoleColors.RED_BRIGHT, errMsg + helpMsg));
            return EnumCMDLineParserResult.RUN_FOR_INVALID_COMMAND;
        }
        return EnumCMDLineParserResult.RUN_FOR_CMDLINE;
    }
}
