package org.aes.compare.uiconsole;

import org.aes.compare.uiconsole.business.GlobalProcess;

public class UIConsoleApp {
//    private SafeScannerInput safeScannerInput = new SafeScannerInput();

    public void start() {
        GlobalProcess globalProcess = new GlobalProcess();
        globalProcess.startProcess();
        /*while (true) {
            System.out.println("UIConsoleApp > 1.While");
            globalProcess.startProcess();
        }*/
    }

}
