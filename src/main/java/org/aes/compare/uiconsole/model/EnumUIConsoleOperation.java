package org.aes.compare.uiconsole.model;

import org.aes.compare.customterminal.model.EnumCRUDCommand;
import org.aes.compare.customterminal.model.EnumModelCommand;

public enum EnumUIConsoleOperation {
    //    CREATE_STUDENT(EnumCRUDCommand.CREATE.getLongName() + "-" + EnumModelCommand.STUDENT.getFileName()),
    CREATE_STUDENT("create-Course"),
    CREATE_ADDRESS(EnumCRUDCommand.CREATE.getLongName() + "-" + EnumModelCommand.ADDRESS.getName()),
    CREATE_COURSE(EnumCRUDCommand.CREATE.getLongName() + "-" + EnumModelCommand.COURSE.getName()),
    CREATE_EXAM_RESULT(EnumCRUDCommand.CREATE.getLongName() + "-" + EnumModelCommand.EXAM_RESULT.getName()),


    READ_STUDENT(EnumCRUDCommand.READ.getLongName() + "-" + EnumModelCommand.STUDENT.getName()),
    READ_ADDRESS(EnumCRUDCommand.READ.getLongName() + "-" + EnumModelCommand.ADDRESS.getName()),
    READ_COURSE(EnumCRUDCommand.READ.getLongName() + "-" + EnumModelCommand.COURSE.getName()),
    READ_EXAM_RESULT(EnumCRUDCommand.READ.getLongName() + "-" + EnumModelCommand.EXAM_RESULT.getName()),


    UPDATE_STUDENT(EnumCRUDCommand.UPDATE.getLongName() + "-" + EnumModelCommand.STUDENT.getName()),
    UPDATE_ADDRESS(EnumCRUDCommand.UPDATE.getLongName() + "-" + EnumModelCommand.ADDRESS.getName()),
    UPDATE_COURSE(EnumCRUDCommand.UPDATE.getLongName() + "-" + EnumModelCommand.COURSE.getName()),
    UPDATE_EXAM_RESULT(EnumCRUDCommand.UPDATE.getLongName() + "-" + EnumModelCommand.EXAM_RESULT.getName()),


    DELETE_STUDENT(EnumCRUDCommand.DELETE.getLongName() + "-" + EnumModelCommand.STUDENT.getName()),
    DELETE_ADDRESS(EnumCRUDCommand.DELETE.getLongName() + "-" + EnumModelCommand.ADDRESS.getName()),
    DELETE_COURSE(EnumCRUDCommand.DELETE.getLongName() + "-" + EnumModelCommand.COURSE.getName()),
    DELETE_EXAM_RESULT(EnumCRUDCommand.DELETE.getLongName() + "-" + EnumModelCommand.EXAM_RESULT.getName());

    private final String getOperationName;

    EnumUIConsoleOperation(String getOperationName) {
        this.getOperationName = getOperationName;
    }

    public String getOperationName() {
        return getOperationName;
    }
}
