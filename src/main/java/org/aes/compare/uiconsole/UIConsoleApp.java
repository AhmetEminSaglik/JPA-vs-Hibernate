package org.aes.compare.uiconsole;

import org.aes.compare.uiconsole.business.GlobalProcess;

public class UIConsoleApp {

    public void start() {
        GlobalProcess globalProcess = new GlobalProcess();
        globalProcess.startProcess();
    }

}
