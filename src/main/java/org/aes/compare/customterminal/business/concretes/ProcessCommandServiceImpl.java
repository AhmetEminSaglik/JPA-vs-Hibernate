package org.aes.compare.customterminal.business.concretes;

import org.aes.compare.customterminal.business.abstracts.ProcessCommandService;
import org.aes.compare.customterminal.config.concrete.CMDLineSingletonBuilder;
import org.aes.compare.customterminal.model.EnumCRUDCommands;
import org.aes.compare.customterminal.model.EnumModelCommands;
import org.aes.compare.customterminal.model.EnumTerminalCommands;
import org.aes.compare.uiconsole.model.EnumCMDLineParserResult;

public class ProcessCommandServiceImpl implements ProcessCommandService {
    private EnumCRUDCommands reqCrudOperation;
    private EnumModelCommands reqModel;
    private EnumTerminalCommands reqTerminalCmd;

    @Override
    public EnumCMDLineParserResult processCommand(String text) {

        return parseCommand(text);

    }

    @Override
    public EnumCMDLineParserResult parseCommand(String text) {
        text = text.replaceAll("\\s+", " ");
        String cmdArr[] = text.split(" ");
        System.out.println("array lenght : " + cmdArr.length);

        try {
            figureOutRequestedTerminalOperation(cmdArr);
            figureOutRequestedCRUDOperation(cmdArr);
            figureOutRequestedObjectToProcess(cmdArr);
            System.out.println("Decided process :  " + reqCrudOperation.name() + " -> " + reqModel.name());
            return EnumCMDLineParserResult.RUN_FOR_CMDLINE;
        } catch (Exception e) {
            return EnumCMDLineParserResult.RUN_FOR_INDEX_VALUE;
        } finally {
            reqCrudOperation = null;
            reqModel = null;
            reqTerminalCmd = null;
        }

        /*for (int i = 0; i < cmdArr.length; i++) {
            if (!cmdArr[i].isEmpty() || cmdArr[i] != CMDLineSingletonBuilder.getCmdLine().getPrefix()) {
                try {
                    for (EnumTerminalCommands tmp : EnumTerminalCommands.values()) {
                        *//*if(cmdArr[i].equals(tmp.getShortName()) || cmdArr[i].equals(tmp.getLongName())){
                        }*//*
                        if (cmdArr[i].equals(tmp.getShortName())) {
                            System.out.println("Short name Commands is found : " + tmp.getShortName());
                        }
                        if (cmdArr[i].equals(tmp.getLongName())) {
                            System.out.println("Long name Commands is found : " + tmp.getLongName());

                        }
                    }
                    *//*System.out.println( EnumTerminalCommands.valueOf(EnumTerminalCommands.class,"create"));
                    System.out.println(cmdArr[i]+" => : " + EnumTerminalCommands.valueOf(cmdArr[i]));*//*
                } catch (Exception e) {
                    System.out.println("hata  : " + e.getMessage());
                }
            }
        }*/

    }

    private void figureOutRequestedTerminalOperation(String[] cmdArr) throws Exception {
        for (int i = 0; i < cmdArr.length; i++) {
            if (!cmdArr[i].isEmpty() || cmdArr[i] != CMDLineSingletonBuilder.getCmdLine().getPrefix()) {
                for (EnumTerminalCommands tmp : EnumTerminalCommands.values()) {
                    if (cmdArr[i].equals(tmp.getShortName()) || cmdArr[i].equals(tmp.getLongName())) {
                        if (reqTerminalCmd == null) {
                            reqTerminalCmd = tmp;
                        } else {
                            throw new Exception("Invalid Requested terminal Operation. Please type only one of them");
                        }
                        cmdArr[i] = "";
                    }
                }
                    /*System.out.println( EnumTerminalCommands.valueOf(EnumTerminalCommands.class,"create"));
                    System.out.println(cmdArr[i]+" => : " + EnumTerminalCommands.valueOf(cmdArr[i]));*/

            }
        }
    }

    private void figureOutRequestedCRUDOperation(String[] cmdArr) throws Exception {
        for (int i = 0; i < cmdArr.length; i++) {
            if (!cmdArr[i].isEmpty() || cmdArr[i] != CMDLineSingletonBuilder.getCmdLine().getPrefix()) {
                for (EnumCRUDCommands tmp : EnumCRUDCommands.values()) {
                    if (cmdArr[i].equals(tmp.getShortName()) || cmdArr[i].equals(tmp.getLongName())) {
                        if (reqCrudOperation == null) {
                            reqCrudOperation = tmp;
                        } else {
                            throw new Exception("Invalid Requested CRUD Operation. Please type only one of them");
                        }
                        cmdArr[i] = "";
                    }
                }
                    /*System.out.println( EnumTerminalCommands.valueOf(EnumTerminalCommands.class,"create"));
                    System.out.println(cmdArr[i]+" => : " + EnumTerminalCommands.valueOf(cmdArr[i]));*/

            }
        }
    }

    private void figureOutRequestedObjectToProcess(String[] cmdArr) throws Exception {
        for (int i = 0; i < cmdArr.length; i++) {
            if (!cmdArr[i].isEmpty() || cmdArr[i] != CMDLineSingletonBuilder.getCmdLine().getPrefix()) {
                for (EnumModelCommands tmp : EnumModelCommands.values()) {
                    if (cmdArr[i].equals(tmp.getShortName())) {
                        if (reqModel == null) {
                            reqModel = tmp;
                        } else {
                            throw new Exception("Invalid Requested CRUD Operation. Please type only one of them");
                        }
                        cmdArr[i] = "";
                    } else if (cmdArr[i].equals(tmp.getName())) {
                    }
                }
            }
        }
    }

}
