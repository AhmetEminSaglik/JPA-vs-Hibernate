package org.aes.compare.customterminal.model;

public class TerminalCMD {
    private EnumModelCommand modelCommand;
    private EnumStandardCommand standardCommand;
    private EnumCRUDCommand crudCommand;
    private String explanation;

    public TerminalCMD() {
    }

    public TerminalCMD(EnumStandardCommand standardCommand) {
        this.standardCommand = standardCommand;
    }

    public TerminalCMD(EnumCRUDCommand crudCommand, EnumModelCommand modelCommand) {
        this.crudCommand = crudCommand;
        this.modelCommand = modelCommand;
    }

    public TerminalCMD(EnumCRUDCommand crudCommand, EnumStandardCommand standardCommand, EnumModelCommand modelCommand) {
        this.crudCommand = crudCommand;
        this.standardCommand = standardCommand;
        this.modelCommand = modelCommand;
    }

    public TerminalCMD(EnumModelCommand modelCommand, EnumStandardCommand standardCommand, EnumCRUDCommand crudCommand, String explanation) {
        this.modelCommand = modelCommand;
        this.standardCommand = standardCommand;
        this.crudCommand = crudCommand;
        this.explanation = explanation;
    }

    public EnumModelCommand getModelCommand() {
        return modelCommand;
    }

    public void setModelCommand(EnumModelCommand modelCommand) {
        this.modelCommand = modelCommand;
    }

    public EnumStandardCommand getStandardCommand() {
        return standardCommand;
    }

    public void setStandardCommand(EnumStandardCommand standardCommand) {
        this.standardCommand = standardCommand;
    }

    public EnumCRUDCommand getCrudCommand() {
        return crudCommand;
    }

    public void setCrudCommand(EnumCRUDCommand crudCommand) {
        this.crudCommand = crudCommand;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Override
    public String toString() {
        return "TerminalCMD{" +
                "modelCommand=" + modelCommand +
                ", standardCommand=" + standardCommand +
                ", crudCommand=" + crudCommand +
                ", explanation='" + explanation + '\'' +
                '}';
    }
}
