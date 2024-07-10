package org.aes.compare.uiconsole;

import org.aes.compare.uiconsole.business.GlobalProcess;
import org.aes.compare.uiconsole.utility.SafeScannerInput;

public class UIConsoleApp {
//    private SafeScannerInput safeScannerInput = new SafeScannerInput();

    public void start() {
        GlobalProcess globalProcess = new GlobalProcess();
        while (true) {
            System.out.println("UIConsoleApp > 1.While");
            globalProcess.startProcess();
        }
    }

}
