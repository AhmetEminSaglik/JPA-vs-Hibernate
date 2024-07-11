package org.aes.compare.customterminal.business.abstracts;

public abstract class TerminalCommandLayout {
    private boolean isAllowedCurrentProcess = true;

    public void quitCurrentProcess() {
        isAllowedCurrentProcess = false;
    }

    public void enableNextCurrentProcess() {
        isAllowedCurrentProcess = true;
    }
}
