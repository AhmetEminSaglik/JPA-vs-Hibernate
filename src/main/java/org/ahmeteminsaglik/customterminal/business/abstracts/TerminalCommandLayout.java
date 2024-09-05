package org.ahmeteminsaglik.customterminal.business.abstracts;

public abstract class TerminalCommandLayout {
    protected boolean isAllowedCurrentProcess = true;

    public void quitCurrentProcess() {
        isAllowedCurrentProcess = false;
    }

    public void enableNextProcess() {
        isAllowedCurrentProcess = true;
    }

    public boolean isAllowedCurrentProcess() {
        return isAllowedCurrentProcess;
    }
}
