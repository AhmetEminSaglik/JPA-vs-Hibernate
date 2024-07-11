package org.aes.compare.customterminal.business.concretes;

import org.aes.compare.customterminal.business.abstracts.ProcessCommandService;
import org.aes.compare.customterminal.config.concrete.CMDLineSingletonBuilder;
import org.aes.compare.customterminal.model.EnumCRUDCommand;
import org.aes.compare.customterminal.model.EnumModelCommand;
import org.aes.compare.customterminal.model.EnumStandardCommand;
import org.aes.compare.customterminal.model.TerminalCMD;
import org.aes.compare.orm.utility.ColorfulTextDesign;
import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;
import org.ahmeteminsaglik.utility.ConsoleColors;


public class ProcessCommandServiceImpl implements ProcessCommandService {
    private TerminalCMD terminalCMD = new TerminalCMD();
    //    private EnumCRUDCommand reqCrudOperation;
//    private EnumModelCommand reqModel;
//    private EnumStandardCommand reqTerminalCmd;
    private final String helpMsg = "Type '" + EnumStandardCommand.HELP.getShortName() + "' or '" + EnumStandardCommand.HELP.getLongName() + "'to get help.";

    @Override
    public EnumCMDLineParserResult processCommand(String text) {
        terminalCMD = new TerminalCMD();
        return parseCommand(text);
    }

    @Override
    public EnumCMDLineParserResult parseCommand(String text) {
        text = clearCommand(text);
        String cmdArr[] = text.split(" ");
//        System.out.println("array length : " + cmdArr.length);

        try {
            for (int i = 0; i < cmdArr.length; i++) {
//                System.out.println("cmdArr[" + i + "] : " + cmdArr[i]);
            }
            figureOutRequestedTerminalOperation(cmdArr);
            figureOutRequestedCRUDOperation(cmdArr);
            figureOutRequestedObjectToProcess(cmdArr);
            decideProcess(cmdArr);


            return EnumCMDLineParserResult.RUN_FOR_CMDLINE;
        } catch (Exception e) {
            System.out.println(ColorfulTextDesign.getText(ConsoleColors.RED_BRIGHT, "Error : " + e.getMessage()));
            return EnumCMDLineParserResult.RUN_FOR_INVALID_COMMAND;
        } /*finally {
            reqCrudOperation = null;
            reqModel = null;
            reqTerminalCmd = null;
        }*/

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
                    if (cmdArr[i].equals(tmp.getShortName())) {
                        if (terminalCMD.getModelCommand() == null) {
                            terminalCMD.setModelCommand(tmp);
                        } else {
                            throw new Exception("Invalid Requested CRUD Operation." + helpMsg);
                        }
                        cmdArr[i] = "";
                    } else if (cmdArr[i].equals(tmp.getName())) {
                    }
                }
            }
        }
    }

    private EnumCMDLineParserResult decideProcess(String[] cmdArr) {
//        String msg = "Decided Process : ";
        String errMsg = "Invalid command.";

        if (terminalCMD.getStandardCommand() != null) {
            if (cmdArr.length == 1) {
//                msg += terminalCMD.getStandardCommand().getLongName();
//                System.out.println(ColorfulTextDesign.getText(ConsoleColors.GREEN_BRIGHT, msg));
                return EnumCMDLineParserResult.RUN_FOR_CMDLINE;
            } else {
                System.out.println(ColorfulTextDesign.getText(ConsoleColors.RED_BRIGHT, errMsg + helpMsg));
            }
        } else if (terminalCMD.getCrudCommand() != null && terminalCMD.getModelCommand() != null) {
//            msg += terminalCMD.getCrudCommand().getLongName() + " -> " + terminalCMD.getModelCommand().getName();
//            System.out.println(ColorfulTextDesign.getText(ConsoleColors.GREEN_BRIGHT, msg));
            return EnumCMDLineParserResult.RUN_FOR_CMDLINE;
        } else {
            System.out.println(ColorfulTextDesign.getText(ConsoleColors.RED_BRIGHT, errMsg + helpMsg));
//            return EnumCMDLineParserResult.RUN_FOR_INVALID_COMMAND;
        }
        return EnumCMDLineParserResult.RUN_FOR_INVALID_COMMAND;

    }




}
